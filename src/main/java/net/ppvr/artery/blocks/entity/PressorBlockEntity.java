package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.PressorBlock;
import net.ppvr.artery.screen.PressorScreenHandler;
import org.jetbrains.annotations.Nullable;

public class PressorBlockEntity extends OrganBlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    protected static final int INPUT_SLOT_INDEX = 0;
    protected static final int FUEL_SLOT_INDEX = 1;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    int litTimeRemaining;
    int litTotalTime;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getGroup().getSanguinity();
                case 1 -> getGroup().getCapacity();
                case 2 -> litTimeRemaining;
                case 3 -> litTotalTime;
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
                    litTimeRemaining = value;
                    break;
                case 3:
                    litTotalTime = value;
                    break;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public PressorBlockEntity(BlockPos pos, BlockState state) {
        super(ArteryBlockEntities.PRESSOR_BLOCK_ENTITY, pos, state);
    }

    private boolean isBurning() {
        return this.litTimeRemaining > 0;
    }

    private static boolean isPressurizable(ItemStack item) {
        return item.artery$getMaxPressure() > 0 && item.artery$getPressure() < item.artery$getMaxPressure();
    }
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory, registries);
        litTimeRemaining = nbt.getShort("lit_time_remaining");
        litTotalTime = nbt.getShort("lit_total_time");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putShort("lit_time_remaining", (short) litTimeRemaining);
        nbt.putShort("lit_total_time", (short) litTotalTime);
        Inventories.writeNbt(nbt, inventory, registries);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PressorBlockEntity blockEntity) {
        boolean wasBurning = blockEntity.isBurning();
        boolean dirty = false;

        if (blockEntity.isBurning()) {
            --blockEntity.litTimeRemaining;
        }

        ItemStack inputStack = blockEntity.getStack(INPUT_SLOT_INDEX);
        ItemStack fuelStack = blockEntity.getStack(FUEL_SLOT_INDEX);
        if (blockEntity.isBurning() || !inputStack.isEmpty() && !fuelStack.isEmpty()) {
            if (!blockEntity.isBurning() && isPressurizable(inputStack) && blockEntity.getGroup().getSanguinity() > 0) {
                blockEntity.litTimeRemaining = blockEntity.getFuelTime(fuelStack);
                blockEntity.litTotalTime = blockEntity.litTimeRemaining;
                if (blockEntity.isBurning()) {
                    dirty = true;
                    if (!fuelStack.isEmpty()) {
                        Item item = fuelStack.getItem();
                        fuelStack.decrement(1);
                        if (fuelStack.isEmpty()) {
                            blockEntity.setStack(FUEL_SLOT_INDEX, item.getRecipeRemainder());
                        }
                    }
                }
            }


            if (blockEntity.isBurning() && isPressurizable(inputStack) && blockEntity.getGroup().getSanguinity() > 0) {
                blockEntity.getGroup().addSanguinity(-1);
                inputStack.artery$addPressure(1);
                dirty = true;
            }
        }

        if (wasBurning != blockEntity.isBurning()) {
            dirty = true;
            world.setBlockState(pos, state.with(PressorBlock.LIT, blockEntity.isBurning()));
        }

        if (dirty) {
            markDirty(world, pos, state);
        }
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.artery.pressor");
    }

    private int getFuelTime(ItemStack stack) {
        return world.getFuelRegistry().getFuelTicks(stack)/10;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.UP) {
            return new int[]{INPUT_SLOT_INDEX};
        } else if (side == Direction.DOWN) {
            return new int[]{INPUT_SLOT_INDEX, FUEL_SLOT_INDEX};
        }
        else {
            return new int[]{FUEL_SLOT_INDEX};
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == INPUT_SLOT_INDEX) {
            return true;
        } else {
            return world.getFuelRegistry().isFuel(stack);
        }
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir != Direction.DOWN || stack.isOf(Items.BUCKET);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(inventory, slot, amount);
        if (!itemStack.isEmpty()) {
            markDirty();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }


    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PressorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
