package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.ppvr.artery.blocks.VentricleBlock;
import net.ppvr.artery.recipe.InfusionRecipe;
import net.ppvr.artery.screen.VentricleScreenHandler;
import org.jetbrains.annotations.Nullable;

public class VentricleBlockEntity extends OrganBlockEntity implements NamedScreenHandlerFactory, SidedInventory, RecipeInputProvider {
    public static final int INPUT_SLOT_INDEX = 0;
    public static final int OUTPUT_SLOT_INDEX = 1;
    private static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1};
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, InfusionRecipe> matchGetter;
    int infuseTimer;
    public int infuseAmountDone;
    public int infuseTotalAmount;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getGroup().getSanguinity();
                case 1 -> getGroup().getCapacity();
                case 2 -> infuseAmountDone;
                case 3 -> infuseTotalAmount;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    getGroup().setSanguinity(value);
                    break;
                case 2:
                    infuseAmountDone = value;
                    break;
                case 3:
                    infuseTotalAmount = value;
                    break;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };


    public VentricleBlockEntity(BlockPos pos, BlockState state) {
        super(ArteryBlockEntities.VENTRICLE_BLOCK_ENTITY, pos, state);
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(InfusionRecipe.TYPE);
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, VentricleBlockEntity blockEntity) {
        ItemStack inputItem = blockEntity.getStack(INPUT_SLOT_INDEX);
        ItemStack outputItem = blockEntity.getStack(OUTPUT_SLOT_INDEX);
        if (blockEntity.getGroup().getSanguinity() > 0) {
            SingleStackRecipeInput recipeInput = new SingleStackRecipeInput(inputItem);
            RecipeEntry<InfusionRecipe> recipe = inputItem.isEmpty() ? null : blockEntity.matchGetter.getFirstMatch(recipeInput, world).orElse(null);
            int maxCount = blockEntity.getMaxCount(outputItem);
            if (canAcceptRecipeOutput(world.getRegistryManager(), recipe, recipeInput, blockEntity.inventory, maxCount)) {
                if (++blockEntity.infuseTimer >= 5) {
                    blockEntity.getGroup().addSanguinity(-1);
                    blockEntity.infuseTimer = 0;
                    if (++blockEntity.infuseAmountDone == blockEntity.infuseTotalAmount) {
                        blockEntity.infuseAmountDone = 0;
                        blockEntity.infuseTotalAmount = getInfuseTime(world, blockEntity);
                        craftRecipe(world.getRegistryManager(), recipe, recipeInput, blockEntity.inventory, maxCount);
                    }
                }
                world.setBlockState(pos, state.with(VentricleBlock.LIT, true));
            } else {
                world.setBlockState(pos, state.with(VentricleBlock.LIT, false));
            }
        } else {
            world.setBlockState(pos, state.with(VentricleBlock.LIT, false).with(VentricleBlock.ACTIVE, false));
        }
    }

    private static boolean canAcceptRecipeOutput(
            DynamicRegistryManager manager,
            @Nullable RecipeEntry<InfusionRecipe> recipe,
            SingleStackRecipeInput input,
            DefaultedList<ItemStack> inventory,
            int maxCount
    ) {
        if (!inventory.get(INPUT_SLOT_INDEX).isEmpty() && recipe != null) {
            ItemStack resultStack = recipe.value().craft(input, manager);
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outputStack = inventory.get(OUTPUT_SLOT_INDEX);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsAndComponentsEqual(outputStack, resultStack)) {
                    return false;
                } else {
                    return outputStack.isEmpty()
                            || outputStack.getCount() < maxCount
                            || outputStack.getCount() < resultStack.getMaxCount();
                }
            }
        }
        return false;
    }

    private static boolean craftRecipe(
            DynamicRegistryManager manager,
            @Nullable RecipeEntry<InfusionRecipe> recipe,
            SingleStackRecipeInput input,
            DefaultedList<ItemStack> inventory,
            int maxCount
    ) {
        if (recipe != null && canAcceptRecipeOutput(manager, recipe, input, inventory, maxCount)) {
            ItemStack inputStack = inventory.get(INPUT_SLOT_INDEX);
            ItemStack resultStack = recipe.value().craft(input, manager);
            ItemStack outputStack = inventory.get(OUTPUT_SLOT_INDEX);
            if (outputStack.isEmpty()) {
                inventory.set(OUTPUT_SLOT_INDEX, resultStack.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(outputStack, resultStack)) {
                outputStack.increment(1);
            }
            inputStack.decrement(1);
            return true;
        }
        return false;
    }

    public static int getInfuseTime(ServerWorld world, VentricleBlockEntity blockEntity) {
        SingleStackRecipeInput input = new SingleStackRecipeInput(blockEntity.getStack(INPUT_SLOT_INDEX));
        return blockEntity.matchGetter
                .getFirstMatch(input, world)
                .map(recipe -> recipe.value().getInfusedAmount())
                .orElse(InfusionRecipe.DEFAULT_INFUSED_AMOUNT);
    }

    @Override
    public int getCapacity() {
        return 80;
    }

    @Override
    public Text getContainerName() {
        return Text.translatable("container.artery.ventricle");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new VentricleScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putShort("infuse_timer", (short) infuseTimer);
        nbt.putShort("infuse_amount_done", (short) infuseAmountDone);
        nbt.putShort("infuse_total_amount", (short) infuseTotalAmount);
        Inventories.writeNbt(nbt, inventory, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.infuseTimer = nbt.getShort("infuse_timer", (short) 0);
        this.infuseAmountDone = nbt.getShort("infuse_amount_done", (short) 0);
        this.infuseTotalAmount = nbt.getShort("infuse_total_amount", (short) 0);
        this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory, registries);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return side == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return slot == INPUT_SLOT_INDEX;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = inventory.get(slot);
        boolean stacked = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        inventory.set(slot, stack);
        stack.capCount(getMaxCount(stack));
        if (slot == INPUT_SLOT_INDEX && !stacked && world instanceof ServerWorld serverWorld) {
            this.infuseTimer = 0;
            this.infuseAmountDone = 0;
            this.infuseTotalAmount = getInfuseTime(serverWorld, this);
            markDirty();
        }
    }

    @Override
    public void provideRecipeInputs(RecipeFinder finder) {
        for (ItemStack itemStack : inventory) {
            finder.addInput(itemStack);
        }
    }
}
