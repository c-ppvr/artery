package net.ppvr.artery.items;

import net.minecraft.item.equipment.trim.ArmorTrimAssets;

import java.util.Map;

public class ArteryArmorTrimAssets {
    public static final ArmorTrimAssets THROMBIUM = of("thrombium");

    public static ArmorTrimAssets of(String suffix) {
        return new ArmorTrimAssets(new ArmorTrimAssets.AssetId(suffix), Map.of());
    }
}
