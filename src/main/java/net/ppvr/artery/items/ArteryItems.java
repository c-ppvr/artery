package net.ppvr.artery.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.ppvr.artery.blocks.ArteryBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryItems  {
    public static final Identifier BASE_TRANSFUSION_RATE_MODIFIER_ID = Identifier.of(MOD_ID, "base_transfusion_rate");
    public static final Identifier BASE_COAGULATION_RATE_MODIFIER_ID = Identifier.of(MOD_ID, "base_coagulation_rate");

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Item ERYTHRITE = register("erythrite");
    public static final Item RAW_LEUKIUM = register("raw_leukium");
    public static final Item LEUKIUM_INGOT = register("leukium_ingot");
    public static final Item RAW_THROMBIUM = register("raw_thrombium");
    public static final Item THROMBIUM_INGOT = register("thrombium_ingot");

    public static final Item LEUKIUM_SWORD = register("leukium_sword", settings -> new SwordItem(ArteryToolMaterial.LEUKIUM, 4.0f, -2.4f, settings));
    public static final Item LEUKIUM_AXE = register("leukium_axe", settings -> new AxeItem(ArteryToolMaterial.LEUKIUM, 6.0f, -2.8f, settings));
    public static final Item THROMBIUM_HELMET = register("thrombium_helmet", settings -> new ArmorItem(ArteryArmorMaterial.THROMBIUM, EquipmentType.HELMET, settings));
    public static final Item THROMBIUM_CHESTPLATE = register("thrombium_chestplate", settings -> new ArmorItem(ArteryArmorMaterial.THROMBIUM, EquipmentType.CHESTPLATE, settings));
    public static final Item THROMBIUM_LEGGINGS = register("thrombium_leggings", settings -> new ArmorItem(ArteryArmorMaterial.THROMBIUM, EquipmentType.LEGGINGS, settings));
    public static final Item THROMBIUM_BOOTS = register("thrombium_boots", settings -> new ArmorItem(ArteryArmorMaterial.THROMBIUM, EquipmentType.BOOTS, settings));

    public static final Item TOTEM_OF_REVIVAL = register("totem_of_revival", new Item.Settings().rarity(Rarity.UNCOMMON).maxDamage(32).component(DataComponentTypes.DEATH_PROTECTION, DeathProtectionComponent.TOTEM_OF_UNDYING));

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ARTERY_ITEM_GROUP_KEY, ARTERY_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ARTERY_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ArteryBlocks.BLOCK_ITEMS) {
                itemGroup.add(item);
            }
            for (Item item : ITEMS) {
                itemGroup.add(item);
            }
        });
    }

    public static final RegistryKey<ItemGroup> ARTERY_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
    public static final ItemGroup ARTERY_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ArteryBlocks.ATRIUM.asItem()))
            .displayName(Text.translatable("itemGroup.artery.artery"))
            .build();

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, id));
    }

    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }

    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return register(keyOf(id), factory, new Item.Settings());
    }

    private static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, key, item);
    }
}
