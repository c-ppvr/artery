package net.ppvr.artery.integration.wthit.provider;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;
import net.ppvr.artery.integration.wthit.config.Options;
import net.ppvr.artery.integration.wthit.provider.component.SanguinityComponent;

public enum AtriumProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        NbtCompound storageNbt = accessor.getData().raw();
        if (!storageNbt.contains("sanguinity") || !storageNbt.contains("capacity")) {
            return;
        }
        int sanguinity = accessor.getData().raw().getInt("sanguinity",0);
        int capacity = accessor.getData().raw().getInt("capacity",0);
        tooltip.setLine(Options.ATRIUM, new SanguinityComponent(sanguinity, capacity));
    }
}
