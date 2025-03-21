package net.ppvr.artery.items;

import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public interface ArteryEquipmentAssetKeys {
    RegistryKey<EquipmentAsset> THROMBIUM = register("thrombium");

    static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MOD_ID, name));
    }
}
