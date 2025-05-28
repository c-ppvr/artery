package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.state.property.Properties;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.items.ArteryArmorTrimMaterials;
import net.ppvr.artery.items.ArteryEquipmentAssetKeys;
import net.ppvr.artery.items.ArteryItems;

import java.util.List;
import java.util.Map;


public class ArteryModelProvider extends FabricModelProvider {
    public static final List<ItemModelGenerator.TrimMaterial> EXTRA_TRIM_MATERIALS = List.of(
            new ItemModelGenerator.TrimMaterial("thrombium", ArteryArmorTrimMaterials.THROMBIUM, Map.of())
    );

    public ArteryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ArteryBlocks.ATRIUM)
                        .coordinate(
                                BlockStateVariantMap.create(Properties.ACTIVE)
                                        .register(true, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_ALL.upload(ArteryBlocks.ATRIUM, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_ALL
                                                                .get(ArteryBlocks.ATRIUM)
                                                                .textures(textureMap -> textureMap.put(TextureKey.ALL, TextureMap.getSubId(ArteryBlocks.ATRIUM, "_empty")))
                                                                .upload(ArteryBlocks.ATRIUM, "_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                        )
        );
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ArteryBlocks.VENTRICLE)
                        .coordinate(
                                BlockStateVariantMap.create(Properties.LIT, Properties.ACTIVE)
                                        .register(false, true,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_BOTTOM_TOP.upload(ArteryBlocks.VENTRICLE, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, true,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_BOTTOM_TOP
                                                                .get(ArteryBlocks.VENTRICLE)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.VENTRICLE, "_side_on"))
                                                                        .put(TextureKey.TOP, TextureMap.getSubId(ArteryBlocks.VENTRICLE, "_top_on"))
                                                                )
                                                                .upload(ArteryBlocks.VENTRICLE, "_on", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, false,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_BOTTOM_TOP
                                                                .get(ArteryBlocks.VENTRICLE)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.VENTRICLE, "_side_empty"))
                                                                        .put(TextureKey.TOP, TextureMap.getSubId(ArteryBlocks.VENTRICLE, "_top_empty"))
                                                                )
                                                                .upload(ArteryBlocks.VENTRICLE, "_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, false,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_BOTTOM_TOP
                                                                .get(ArteryBlocks.VENTRICLE)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.VENTRICLE, "_side_on_empty"))
                                                                        .put(TextureKey.TOP, TextureMap.getSubId(ArteryBlocks.VENTRICLE, "_top_on_empty"))
                                                                )
                                                                .upload(ArteryBlocks.VENTRICLE, "_on_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                        )
        );
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ArteryBlocks.FIBROBLASTER)
                        .coordinate(
                                BlockStateVariantMap.create(Properties.LIT, Properties.ACTIVE)
                                        .register(false, true,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_COLUMN.upload(ArteryBlocks.FIBROBLASTER, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, true,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_COLUMN
                                                                .get(ArteryBlocks.FIBROBLASTER)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.FIBROBLASTER, "_side_on"))
                                                                )
                                                                .upload(ArteryBlocks.FIBROBLASTER, "_on", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, false,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_COLUMN
                                                                .get(ArteryBlocks.FIBROBLASTER)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.FIBROBLASTER, "_side_empty"))
                                                                )
                                                                .upload(ArteryBlocks.FIBROBLASTER, "_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, false,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.CUBE_COLUMN
                                                                .get(ArteryBlocks.FIBROBLASTER)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.FIBROBLASTER, "_side_on_empty"))
                                                                )
                                                                .upload(ArteryBlocks.FIBROBLASTER, "_on_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                        )
        );
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ArteryBlocks.PRESSOR)
                        .coordinate(
                                BlockStateVariantMap.create(Properties.LIT, Properties.ACTIVE)
                                        .register(false, true,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.ORIENTABLE.upload(ArteryBlocks.PRESSOR, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, true,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.ORIENTABLE
                                                                .get(ArteryBlocks.PRESSOR)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.FRONT, TextureMap.getSubId(ArteryBlocks.PRESSOR, "_front_on"))
                                                                )
                                                                .upload(ArteryBlocks.PRESSOR, "_on", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, false,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.ORIENTABLE
                                                                .get(ArteryBlocks.PRESSOR)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.PRESSOR, "_side_empty"))
                                                                        .put(TextureKey.FRONT, TextureMap.getSubId(ArteryBlocks.PRESSOR, "_front_empty"))
                                                                )
                                                                .upload(ArteryBlocks.PRESSOR, "_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, false,
                                                BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        TexturedModel.ORIENTABLE
                                                                .get(ArteryBlocks.PRESSOR)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.PRESSOR, "_side_empty"))
                                                                        .put(TextureKey.FRONT, TextureMap.getSubId(ArteryBlocks.PRESSOR, "_front_on_empty"))
                                                                )
                                                                .upload(ArteryBlocks.PRESSOR, "_on_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                        )
                        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
        );
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.ERYTHRITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.ERYTHRITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.LEUKIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.DEEPSLATE_LEUKIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.RAW_LEUKIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.LEUKIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.THROMBIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.DEEPSLATE_THROMBIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.RAW_THROMBIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.THROMBIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArteryBlocks.HEMOGLOBIUM_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ArteryItems.ERYTHRITE, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.RAW_LEUKIUM, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.LEUKIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.RAW_THROMBIUM, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.THROMBIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.HEMOGLOBIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.FLESH, Models.GENERATED);

        itemModelGenerator.register(ArteryItems.LEUKIUM_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ArteryItems.LEUKIUM_AXE, Models.HANDHELD);

        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_HELMET, ArteryEquipmentAssetKeys.THROMBIUM, "helmet", false);
        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_CHESTPLATE, ArteryEquipmentAssetKeys.THROMBIUM, "chestplate", false);
        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_LEGGINGS, ArteryEquipmentAssetKeys.THROMBIUM, "leggings", false);
        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_BOOTS, ArteryEquipmentAssetKeys.THROMBIUM, "boots", false);

        itemModelGenerator.register(ArteryItems.TOTEM_OF_REVIVAL, Models.GENERATED);

        // have to do this to register trims added by the mod
        itemModelGenerator.registerArmor(Items.TURTLE_HELMET, EquipmentAssetKeys.TURTLE_SCUTE, "helmet", false);
        itemModelGenerator.registerArmor(Items.LEATHER_HELMET, EquipmentAssetKeys.LEATHER, "helmet", true);
        itemModelGenerator.registerArmor(Items.LEATHER_CHESTPLATE, EquipmentAssetKeys.LEATHER, "chestplate", true);
        itemModelGenerator.registerArmor(Items.LEATHER_LEGGINGS, EquipmentAssetKeys.LEATHER, "leggings", true);
        itemModelGenerator.registerArmor(Items.LEATHER_BOOTS, EquipmentAssetKeys.LEATHER, "boots", true);
        itemModelGenerator.registerArmor(Items.CHAINMAIL_HELMET, EquipmentAssetKeys.CHAINMAIL, "helmet", false);
        itemModelGenerator.registerArmor(Items.CHAINMAIL_CHESTPLATE, EquipmentAssetKeys.CHAINMAIL, "chestplate", false);
        itemModelGenerator.registerArmor(Items.CHAINMAIL_LEGGINGS, EquipmentAssetKeys.CHAINMAIL, "leggings", false);
        itemModelGenerator.registerArmor(Items.CHAINMAIL_BOOTS, EquipmentAssetKeys.CHAINMAIL, "boots", false);
        itemModelGenerator.registerArmor(Items.IRON_HELMET, EquipmentAssetKeys.IRON, "helmet", false);
        itemModelGenerator.registerArmor(Items.IRON_CHESTPLATE, EquipmentAssetKeys.IRON, "chestplate", false);
        itemModelGenerator.registerArmor(Items.IRON_LEGGINGS, EquipmentAssetKeys.IRON, "leggings", false);
        itemModelGenerator.registerArmor(Items.IRON_BOOTS, EquipmentAssetKeys.IRON, "boots", false);
        itemModelGenerator.registerArmor(Items.DIAMOND_HELMET, EquipmentAssetKeys.DIAMOND, "helmet", false);
        itemModelGenerator.registerArmor(Items.DIAMOND_CHESTPLATE, EquipmentAssetKeys.DIAMOND, "chestplate", false);
        itemModelGenerator.registerArmor(Items.DIAMOND_LEGGINGS, EquipmentAssetKeys.DIAMOND, "leggings", false);
        itemModelGenerator.registerArmor(Items.DIAMOND_BOOTS, EquipmentAssetKeys.DIAMOND, "boots", false);
        itemModelGenerator.registerArmor(Items.GOLDEN_HELMET, EquipmentAssetKeys.GOLD, "helmet", false);
        itemModelGenerator.registerArmor(Items.GOLDEN_CHESTPLATE, EquipmentAssetKeys.GOLD, "chestplate", false);
        itemModelGenerator.registerArmor(Items.GOLDEN_LEGGINGS, EquipmentAssetKeys.GOLD, "leggings", false);
        itemModelGenerator.registerArmor(Items.GOLDEN_BOOTS, EquipmentAssetKeys.GOLD, "boots", false);
        itemModelGenerator.registerArmor(Items.NETHERITE_HELMET, EquipmentAssetKeys.NETHERITE, "helmet", false);
        itemModelGenerator.registerArmor(Items.NETHERITE_CHESTPLATE, EquipmentAssetKeys.NETHERITE, "chestplate", false);
        itemModelGenerator.registerArmor(Items.NETHERITE_LEGGINGS, EquipmentAssetKeys.NETHERITE, "leggings", false);
        itemModelGenerator.registerArmor(Items.NETHERITE_BOOTS, EquipmentAssetKeys.NETHERITE, "boots", false);

        for (Item item : ArteryItems.WANDS) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }
}
