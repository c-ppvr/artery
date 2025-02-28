package net.ppvr.artery.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.entity.ArteryBlockEntities;
import net.ppvr.artery.blocks.entity.FibroblasterBlockEntity;
import net.ppvr.artery.blocks.entity.OrganBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FibroblasterBlock extends OrganBlock{
    public FibroblasterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public OrganBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FibroblasterBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && world.getBlockEntity(pos) instanceof FibroblasterBlockEntity blockEntity) {
            player.openHandledScreen(blockEntity);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ArteryBlockEntities.FIBROBLASTER_BLOCK_ENTITY, FibroblasterBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FibroblasterBlock::new);
    }
}
