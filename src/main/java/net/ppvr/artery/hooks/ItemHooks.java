package net.ppvr.artery.hooks;

import net.minecraft.item.Item;

public interface ItemHooks {
    interface Settings {
        default Item.Settings artery$maxPressure(int maxPressure) { throw new RuntimeException(); }
    }
}
