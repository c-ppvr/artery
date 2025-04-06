package net.ppvr.artery.integration.wthit;

import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.IWailaClientPlugin;
import net.ppvr.artery.blocks.AtriumBlock;
import net.ppvr.artery.integration.wthit.provider.AtriumProvider;

public class ArteryWailaClientPlugin implements IWailaClientPlugin {
    @Override
    public void register(IClientRegistrar registrar) {
        registrar.body(AtriumProvider.INSTANCE, AtriumBlock.class);
    }
}
