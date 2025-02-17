package net.ppvr.artery.blocks.entity;

import net.fabricmc.fabric.api.screenhandler.v1.FabricScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.ppvr.artery.screen.AtriumScreenHandler;
import org.jetbrains.annotations.Nullable;

public class AtriumBlockEntity extends OrganBlockEntity implements NamedScreenHandlerFactory, FabricScreenHandlerFactory {
    public AtriumBlockEntity(BlockPos pos, BlockState state) {
        super(ArteryBlockEntities.ATRIUM_BLOCK_ENTITY, pos, state);
    }

    @Override
    public int getCapacity() {
        return 800;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.artery.atrium");
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AtriumBlockEntity.this.getGroup().getSanguinity();
                case 1 -> AtriumBlockEntity.this.getGroup().getCapacity();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                AtriumBlockEntity.this.getGroup().setSanguinity(value);
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AtriumScreenHandler(syncId, player, this.propertyDelegate, ScreenHandlerContext.create(world, pos));
    }
}
