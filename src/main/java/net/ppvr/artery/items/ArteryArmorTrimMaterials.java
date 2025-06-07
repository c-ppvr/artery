package net.ppvr.artery.items;

import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryArmorTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> THROMBIUM = of("thrombium");

    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MOD_ID, id));
    }
}
