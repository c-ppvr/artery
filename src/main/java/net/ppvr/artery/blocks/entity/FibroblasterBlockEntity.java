package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.FibroblasterBlock;
import net.ppvr.artery.screen.FibroblasterScreenHandler;
import org.jetbrains.annotations.Nullable;

public class FibroblasterBlockEntity extends OrganBlockEntity implements SidedInventory {
    private ItemStack inventoryStack = ItemStack.EMPTY;
    int repairTime;
    int repairTimer;
    int accelerationTimer;
    int progress;
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getGroup().getSanguinity();
                case 1 -> getGroup().getCapacity();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                getGroup().setSanguinity(value);
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public FibroblasterBlockEntity(BlockPos pos, BlockState state) {
        super(ArteryBlockEntities.FIBROBLASTER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventoryStack = ItemStack.fromNbtOrEmpty(registries, nbt.getCompound("item"));
        this.repairTime = nbt.getShort("repair_time");
        this.repairTimer = nbt.getShort("repair_timer");
        this.accelerationTimer = nbt.getShort("acceleration_timer");
        this.progress = nbt.getShort("progress");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putShort("repair_time", (short) repairTime);
        nbt.putShort("repair_timer", (short) repairTimer);
        nbt.putShort("acceleration_timer", (short) accelerationTimer);
        nbt.putShort("progress", (short) progress);
        nbt.put("item", inventoryStack.toNbtAllowEmpty(registries));
    }

    public static void tick(World world, BlockPos pos, BlockState state, FibroblasterBlockEntity blockEntity) {
        ItemStack itemStack = blockEntity.getStack(0);
        if (blockEntity.getGroup().getSanguinity() > 0 && itemStack.isDamaged()) {
            world.setBlockState(pos, state.with(FibroblasterBlock.LIT, true));
            if (++blockEntity.repairTimer >= blockEntity.repairTime) {
                blockEntity.getGroup().addSanguinity(-1);
                if (++blockEntity.progress >= 2) {
                    itemStack.setDamage(itemStack.getDamage() - 1);
                    blockEntity.progress = 0;
                }
                blockEntity.repairTimer = 0;

                if (blockEntity.repairTime > 1 && ++blockEntity.accelerationTimer >= 12) {
                    --blockEntity.repairTime;
                    blockEntity.accelerationTimer = 0;
                }
            }
            markDirty(world, pos, state);
        } else {
            world.setBlockState(pos, state.with(FibroblasterBlock.LIT, false));
            blockEntity.repairTime = 8;
            blockEntity.repairTimer = 0;
            blockEntity.accelerationTimer = 0;
            blockEntity.progress = 0;
        }
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return inventoryStack.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot == 0 ? inventoryStack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        ItemStack itemStack = inventoryStack.split(amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return slot == 0 ? inventoryStack.copyAndEmpty() : ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0) {
            this.inventoryStack = stack;
        }
    }

    @Override
    public Text getContainerName() {
        return Text.translatable("container.artery.fibroblaster");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return DefaultedList.ofSize(1, inventoryStack);
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventoryStack = inventory.getFirst();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FibroblasterScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public void clear() {
        this.inventoryStack = ItemStack.EMPTY;
    }
}
