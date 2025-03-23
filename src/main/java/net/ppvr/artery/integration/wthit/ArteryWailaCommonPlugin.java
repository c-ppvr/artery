package net.ppvr.artery.integration.wthit;

import mcp.mobius.waila.api.ICommonRegistrar;
import mcp.mobius.waila.api.IWailaCommonPlugin;
import net.ppvr.artery.blocks.entity.AtriumBlockEntity;
import net.ppvr.artery.blocks.entity.VentricleBlockEntity;
import net.ppvr.artery.integration.wthit.provider.AtriumDataProvider;
import net.ppvr.artery.integration.wthit.provider.VentricleDataProvider;

public class ArteryWailaCommonPlugin implements IWailaCommonPlugin {
    @Override
    public void register(ICommonRegistrar registrar) {
        registrar.blockData(AtriumDataProvider.INSTANCE, AtriumBlockEntity.class);

        registrar.blockData(VentricleDataProvider.INSTANCE, VentricleBlockEntity.class);
    }
}
