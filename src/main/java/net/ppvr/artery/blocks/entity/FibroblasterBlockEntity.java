package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.ppvr.artery.screen.FibroblasterScreenHandler;
import org.jetbrains.annotations.Nullable;

public class FibroblasterBlockEntity extends OrganBlockEntity implements NamedScreenHandlerFactory, SidedInventory {
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
        if (blockEntity.getGroup().getSanguinity() > 0 && itemStack.isDamaged() && ++blockEntity.repairTimer >= blockEntity.repairTime) {
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
            markDirty(world, pos, state);
        } else if (blockEntity.getGroup().getSanguinity() == 0 || itemStack.isEmpty()){
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
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.artery.fibroblaster");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FibroblasterScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public void clear() {
        this.inventoryStack = ItemStack.EMPTY;
    }
}
