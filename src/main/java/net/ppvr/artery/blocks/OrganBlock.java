package net.ppvr.artery.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.entity.OrganBlockEntity;
import net.ppvr.artery.util.OrganGroup;
import net.ppvr.artery.util.OrganGroupState;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public abstract class OrganBlock extends BlockWithEntity {
    public static final BooleanProperty ACTIVE = ArteryProperties.ACTIVE;

    public OrganBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            OrganGroup.merge((ServerWorld) world,
                    Arrays.stream(DIRECTIONS)
                            .map(d -> world.getBlockEntity(pos.offset(d)) instanceof OrganBlockEntity blockEntity ? blockEntity.getGroup() : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet())
            ).add(pos);
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        BlockState newState = world.getBlockState(pos);
        if (!state.isOf(newState.getBlock())) {
            OrganGroup group = OrganGroupState.get(world).get(pos);
            if (group != null) {
                group.remove(pos);
                group.redistribute();
            }
        }
        super.onStateReplaced(state, world, pos, moved);
    }

    @Override
    public abstract OrganBlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public static ToIntFunction<BlockState> getLuminanceSupplier(int luminance) {
        return state -> state.get(ArteryProperties.ACTIVE) ? luminance : 0;
    }
}
