package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
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

    public static void tick(World world, BlockPos pos, BlockState state, PressorBlockEntity blockEntity) {
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.artery.pressor");
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PressorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public void clear() {

    }
}
