package net.ppvr.artery.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.entity.ArteryBlockEntities;
import net.ppvr.artery.blocks.entity.OrganBlockEntity;
import net.ppvr.artery.blocks.entity.PressorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PressorBlock extends OrganBlock {
    public static final BooleanProperty LIT = Properties.LIT;

    public PressorBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LIT, false));
    }

    @Override
    public OrganBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PressorBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ArteryBlockEntities.PRESSOR_BLOCK_ENTITY, PressorBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT);
    }
}
