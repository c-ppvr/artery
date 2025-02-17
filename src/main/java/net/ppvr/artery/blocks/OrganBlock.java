package net.ppvr.artery.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.entity.OrganBlockEntity;
import net.ppvr.artery.util.OrganGroup;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public abstract class OrganBlock extends BlockWithEntity {
    public static final BooleanProperty ACTIVE = Properties.ACTIVE;

    public OrganBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        OrganGroup.merge((ServerWorld) world,
                Arrays.stream(DIRECTIONS)
                        .map(d -> world.getBlockEntity(pos.offset(d)) instanceof OrganBlockEntity blockEntity ? blockEntity.getGroup() : null)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())
        ).add(pos);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof OrganBlockEntity blockEntity && !state.isOf(newState.getBlock())) {
            blockEntity.getGroup().remove(pos);
            blockEntity.getGroup().redistribute();
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public abstract OrganBlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public static ToIntFunction<BlockState> getLuminanceSupplier(int luminance) {
        return state -> state.get(Properties.ACTIVE) ? luminance : 0;
    }
}
