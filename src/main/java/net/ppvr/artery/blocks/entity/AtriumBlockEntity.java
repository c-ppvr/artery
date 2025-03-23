package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.ppvr.artery.screen.AtriumScreenHandler;

public class AtriumBlockEntity extends OrganBlockEntity implements NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.of();
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

    public AtriumBlockEntity(BlockPos pos, BlockState state) {
        super(ArteryBlockEntities.ATRIUM_BLOCK_ENTITY, pos, state);
    }

    @Override
    public int getCapacity() {
        return 320;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.artery.atrium");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new AtriumScreenHandler(syncId, playerInventory.player, propertyDelegate, ScreenHandlerContext.create(world, pos));
    }

    @Override
    public int size() {
        return 0;
    }
}
