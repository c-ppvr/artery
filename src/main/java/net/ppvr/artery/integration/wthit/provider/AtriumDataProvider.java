package net.ppvr.artery.integration.wthit.provider;

import mcp.mobius.waila.api.*;
import net.ppvr.artery.blocks.entity.AtriumBlockEntity;

public enum AtriumDataProvider implements IDataProvider<AtriumBlockEntity> {
    INSTANCE;

    @Override
    public void appendData(IDataWriter data, IServerAccessor<AtriumBlockEntity> accessor, IPluginConfig config) {
        AtriumBlockEntity blockEntity = accessor.getTarget();
        if (blockEntity.getCapacity() > 0) {
            data.raw().putInt("sanguinity", blockEntity.getGroup().getSanguinity());
            data.raw().putInt("capacity", blockEntity.getGroup().getCapacity());
        }
    }

}
