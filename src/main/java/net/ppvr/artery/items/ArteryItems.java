package net.ppvr.artery.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.EntityType;
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
import net.ppvr.artery.items.components.ArteryConsumableComponents;
import net.ppvr.artery.items.components.ArteryFoodComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryItems {
    public static final Identifier BASE_TRANSFUSION_RATE_MODIFIER_ID = Identifier.of(MOD_ID, "base_transfusion_rate");

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Item> WANDS = new ArrayList<>();
    
    public static final Item ERYTHRITE = register("erythrite");
    public static final Item RAW_LEUKIUM = register("raw_leukium");
    public static final Item RAW_THROMBIUM = register("raw_thrombium");
    public static final Item LEUKIUM_INGOT = register("leukium_ingot");
    public static final Item THROMBIUM_INGOT = register("thrombium_ingot");
    public static final Item HEMOGLOBIUM_INGOT = register("hemoglobium_ingot");
    public static final Item FLESH = register("flesh", new Item.Settings().food(ArteryFoodComponents.FLESH, ArteryConsumableComponents.FLESH));

    public static final Item LEUKIUM_SWORD = register("leukium_sword", new Item.Settings().sword(ArteryToolMaterial.LEUKIUM, 4.0f, -2.4f));
    public static final Item LEUKIUM_AXE = register("leukium_axe", settings -> new AxeItem(ArteryToolMaterial.LEUKIUM, 6.0f, -2.8f, settings));
    public static final Item THROMBIUM_HELMET = register("thrombium_helmet", new Item.Settings().armor(ArteryArmorMaterial.THROMBIUM, EquipmentType.HELMET));
    public static final Item THROMBIUM_CHESTPLATE = register("thrombium_chestplate", new Item.Settings().armor(ArteryArmorMaterial.THROMBIUM, EquipmentType.CHESTPLATE));
    public static final Item THROMBIUM_LEGGINGS = register("thrombium_leggings", new Item.Settings().armor(ArteryArmorMaterial.THROMBIUM, EquipmentType.LEGGINGS));
    public static final Item THROMBIUM_BOOTS = register("thrombium_boots", new Item.Settings().armor(ArteryArmorMaterial.THROMBIUM, EquipmentType.BOOTS));

    public static final Item TOTEM_OF_REVIVAL = register("totem_of_revival", new Item.Settings().rarity(Rarity.UNCOMMON).maxDamage(32).artery$maxPressure(1440).component(DataComponentTypes.DEATH_PROTECTION, DeathProtectionComponent.TOTEM_OF_UNDYING));

    public static final Item BLAZE_WAND = registerWand("blaze_wand", settings -> new MobWandItem(EntityType.BLAZE, 60, settings.maxDamage(24).artery$maxPressure(60 * 6)));
    public static final Item BREEZE_WAND = registerWand("breeze_wand", settings -> new MobWandItem(EntityType.BREEZE, 90, settings.maxDamage(24).artery$maxPressure(90 * 6)));
    public static final Item CHICKEN_WAND = registerWand("chicken_wand", settings -> new MobWandItem(EntityType.CHICKEN, 16, settings.maxDamage(24).artery$maxPressure(16 * 6)));
    public static final Item COD_WAND = registerWand("cod_wand", settings -> new MobWandItem(EntityType.COD, 12, settings.maxDamage(24).artery$maxPressure(12 * 6)));
    public static final Item COW_WAND = registerWand("cow_wand", settings -> new MobWandItem(EntityType.COW, 40, settings.maxDamage(24).artery$maxPressure(40 * 6)));
    public static final Item CREEPER_WAND = registerWand("creeper_wand", settings -> new MobWandItem(EntityType.CREEPER, 60, settings.maxDamage(24).artery$maxPressure(60 * 6)));
    public static final Item ENDERMAN_WAND = registerWand("enderman_wand", settings -> new MobWandItem(EntityType.ENDERMAN, 120, settings.maxDamage(24).artery$maxPressure(120 * 6)));
    public static final Item GHAST_WAND = registerWand("ghast_wand", settings -> new MobWandItem(EntityType.GHAST, 30, settings.maxDamage(24).artery$maxPressure(30 * 6)));
    public static final Item GLOW_SQUID_WAND = registerWand("glow_squid_wand", settings -> new MobWandItem(EntityType.GLOW_SQUID, 40, settings.maxDamage(24).artery$maxPressure(40 * 6)));
    public static final Item GUARDIAN_WAND = registerWand("guardian_wand", settings -> new MobWandItem(EntityType.GUARDIAN, 90, settings.maxDamage(24).artery$maxPressure(90 * 6)));
    public static final Item MAGMA_CUBE_WAND = registerWand("magma_cube_wand", settings -> new MobWandItem(EntityType.MAGMA_CUBE, 21, settings.maxDamage(24).artery$maxPressure(21 * 6)));
    public static final Item PHANTOM_WAND = registerWand("phantom_wand", settings -> new MobWandItem(EntityType.PHANTOM, 60, settings.maxDamage(24).artery$maxPressure(60 * 6)));
    public static final Item PIG_WAND = registerWand("pig_wand", settings -> new MobWandItem(EntityType.PIG, 40, settings.maxDamage(24).artery$maxPressure(40 * 6)));
    public static final Item PUFFERFISH_WAND = registerWand("pufferfish_wand", settings -> new MobWandItem(EntityType.PUFFERFISH, 12, settings.maxDamage(24).artery$maxPressure(12 * 6)));
    public static final Item RABBIT_WAND = registerWand("rabbit_wand", settings -> new MobWandItem(EntityType.RABBIT, 12, settings.maxDamage(24).artery$maxPressure(12 * 6)));
    public static final Item SALMON_WAND = registerWand("salmon_wand", settings -> new MobWandItem(EntityType.SALMON, 12, settings.maxDamage(24).artery$maxPressure(12 * 6)));
    public static final Item SHEEP_WAND = registerWand("sheep_wand", settings -> new MobWandItem(EntityType.SHEEP, 32, settings.maxDamage(24).artery$maxPressure(32 * 6)));
    public static final Item SHULKER_WAND = registerWand("shulker_wand", settings -> new MobWandItem(EntityType.SHULKER, 90, settings.maxDamage(24).artery$maxPressure(90 * 6)));
    public static final Item SKELETON_WAND = registerWand("skeleton_wand", settings -> new MobWandItem(EntityType.SKELETON, 60, settings.maxDamage(24).artery$maxPressure(60 * 6)));
    public static final Item SLIME_WAND = registerWand("slime_wand", settings -> new MobWandItem(EntityType.SLIME, 21, settings.maxDamage(24).artery$maxPressure(21 * 6)));
    public static final Item SPIDER_WAND = registerWand("spider_wand", settings -> new MobWandItem(EntityType.SPIDER, 48, settings.maxDamage(24).artery$maxPressure(48 * 6)));
    public static final Item SQUID_WAND = registerWand("squid_wand", settings -> new MobWandItem(EntityType.SQUID, 40, settings.maxDamage(24).artery$maxPressure(40 * 6)));
    public static final Item TROPICAL_FISH_WAND = registerWand("tropical_fish_wand", settings -> new MobWandItem(EntityType.TROPICAL_FISH, 12, settings.maxDamage(24).artery$maxPressure(12 * 6)));
    public static final Item ZOMBIE_WAND = registerWand("zombie_wand", settings -> new MobWandItem(EntityType.ZOMBIE, 60, settings.maxDamage(24).artery$maxPressure(60 * 6)));

    public static final RegistryKey<ItemGroup> ARTERY_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
    public static final RegistryKey<ItemGroup> ARTERY_WAND_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "wand_group"));

    public static final ItemGroup ARTERY_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ArteryBlocks.ATRIUM.asItem()))
            .displayName(Text.translatable("itemGroup.artery.artery"))
            .build();

    public static final ItemGroup ARTERY_WAND_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ZOMBIE_WAND))
            .displayName(Text.translatable("itemGroup.artery.wands"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ARTERY_ITEM_GROUP_KEY, ARTERY_ITEM_GROUP);
        Registry.register(Registries.ITEM_GROUP, ARTERY_WAND_GROUP_KEY, ARTERY_WAND_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ARTERY_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ArteryBlocks.BLOCK_ITEMS) {
                itemGroup.add(item);
            }
            for (Item item : ITEMS) {
                itemGroup.add(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ARTERY_WAND_GROUP_KEY).register(itemGroup -> {
            for (Item item : WANDS) {
                itemGroup.add(item);
            }
        });
    }

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

    private static Item registerWand(String id, Function<Item.Settings, Item> factory) {
        Item item = factory.apply(new Item.Settings().registryKey(keyOf(id)));
        WANDS.add(item);
        return Registry.register(Registries.ITEM, keyOf(id), item);
    }
}
