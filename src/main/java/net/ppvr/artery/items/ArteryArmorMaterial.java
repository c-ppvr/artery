package net.ppvr.artery.items;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ArteryArmorMaterial {
    public static final ArmorMaterial THROMBIUM = new ArmorMaterial(18, Util.make(new EnumMap<>(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 2);
        map.put(EquipmentType.LEGGINGS, 5);
        map.put(EquipmentType.CHESTPLATE, 6);
        map.put(EquipmentType.HELMET, 3);
        map.put(EquipmentType.BODY, 5);
    }), 16, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, ArteryItemTags.REPAIRS_THROMBIUM_ARMOR, ArteryEquipmentAssetKeys.THROMBIUM);

    public static final Map<Object, Double> COAGULATION_RATE_MAP = new HashMap<>();
    public static final Map<Object, Double> MAX_SANGUINITY_MAP = new HashMap<>();

    static {
        COAGULATION_RATE_MAP.put(THROMBIUM, -0.5);
        MAX_SANGUINITY_MAP.put(THROMBIUM, 80.0);
    }
}
