package net.ppvr.artery.integration.wthit.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import net.ppvr.artery.blocks.VentricleBlock;
import net.ppvr.artery.blocks.entity.VentricleBlockEntity;

public enum VentricleDataProvider implements IDataProvider<VentricleBlockEntity> {
    INSTANCE;

    @Override
    public void appendData(IDataWriter data, IServerAccessor<VentricleBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.TYPE, res -> {
            VentricleBlockEntity blockEntity = accessor.getTarget();
            if (blockEntity.getCachedState().get(VentricleBlock.LIT)) {
                res.add(ProgressData
                        .ratio((float) blockEntity.infuseAmountDone / blockEntity.infuseTotalAmount)
                        .itemGetter(blockEntity::getStack)
                        .input(0)
                        .output(1)
                );
            }
        });
    }
}
