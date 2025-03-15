package net.ppvr.artery.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.ppvr.artery.util.OrganGroup;
import net.ppvr.artery.util.OrganGroupState;

public abstract class OrganBlockEntity extends LockableContainerBlockEntity {
    private OrganGroup group;

    public OrganBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract int getCapacity();

    public void setGroup(OrganGroup group) {
        this.group = group;
    }

    public OrganGroup getGroup() {
        if (this.group == null) {
            this.group = OrganGroupState.get((ServerWorld) this.getWorld()).get(pos);
        }
        return group;
    }
}
