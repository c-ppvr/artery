package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.blocks.ArteryProperties;
import net.ppvr.artery.items.ArteryEquipmentAssetKeys;
import net.ppvr.artery.items.ArteryItems;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class ArteryModelProvider extends FabricModelProvider {
    public ArteryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(ArteryBlocks.ATRIUM)
                        .with(
                                BlockStateVariantMap.models(ArteryProperties.ACTIVE)
                                        .register(true, BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.CUBE_ALL.upload(ArteryBlocks.ATRIUM, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.CUBE_ALL
                                                                .get(ArteryBlocks.ATRIUM)
                                                                .textures(textureMap -> textureMap.put(TextureKey.ALL, TextureMap.getSubId(ArteryBlocks.ATRIUM, "_empty")))
                                                                .upload(ArteryBlocks.ATRIUM, "_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                        )
        );
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(ArteryBlocks.VENTRICLE)
                        .with(
                                BlockStateVariantMap.models(Properties.LIT, ArteryProperties.ACTIVE)
                                        .register(false, true,
                                                BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.CUBE_BOTTOM_TOP.upload(ArteryBlocks.VENTRICLE, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, true,
                                                BlockStateModelGenerator.createWeightedVariant(
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
                                                BlockStateModelGenerator.createWeightedVariant(
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
                                                BlockStateModelGenerator.createWeightedVariant(
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
                VariantsBlockModelDefinitionCreator.of(ArteryBlocks.FIBROBLASTER)
                        .with(
                                BlockStateVariantMap.models(Properties.LIT, ArteryProperties.ACTIVE)
                                        .register(false, true,
                                                BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.CUBE_COLUMN.upload(ArteryBlocks.FIBROBLASTER, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, true,
                                                BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.CUBE_COLUMN
                                                                .get(ArteryBlocks.FIBROBLASTER)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.FIBROBLASTER, "_side_on"))
                                                                )
                                                                .upload(ArteryBlocks.FIBROBLASTER, "_on", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, false,
                                                BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.CUBE_COLUMN
                                                                .get(ArteryBlocks.FIBROBLASTER)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.SIDE, TextureMap.getSubId(ArteryBlocks.FIBROBLASTER, "_side_empty"))
                                                                )
                                                                .upload(ArteryBlocks.FIBROBLASTER, "_empty", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, false,
                                                BlockStateModelGenerator.createWeightedVariant(
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
                VariantsBlockModelDefinitionCreator.of(ArteryBlocks.PRESSOR)
                        .with(
                                BlockStateVariantMap.models(Properties.LIT, ArteryProperties.ACTIVE)
                                        .register(false, true,
                                                BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.ORIENTABLE.upload(ArteryBlocks.PRESSOR, blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(true, true,
                                                BlockStateModelGenerator.createWeightedVariant(
                                                        TexturedModel.ORIENTABLE
                                                                .get(ArteryBlocks.PRESSOR)
                                                                .textures(textureMap -> textureMap
                                                                        .put(TextureKey.FRONT, TextureMap.getSubId(ArteryBlocks.PRESSOR, "_front_on"))
                                                                )
                                                                .upload(ArteryBlocks.PRESSOR, "_on", blockStateModelGenerator.modelCollector)
                                                )
                                        )
                                        .register(false, false,
                                                BlockStateModelGenerator.createWeightedVariant(
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
                                                BlockStateModelGenerator.createWeightedVariant(
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
                        .coordinate(BlockStateVariantMap.operations(Properties.HORIZONTAL_FACING)
                                        .register(Direction.EAST, ROTATE_Y_90)
                                        .register(Direction.SOUTH, ROTATE_Y_180)
                                        .register(Direction.WEST, ROTATE_Y_270)
                                        .register(Direction.NORTH, NO_OP)
                        )
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

        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_HELMET, ArteryEquipmentAssetKeys.THROMBIUM, ItemModelGenerator.HELMET_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_CHESTPLATE, ArteryEquipmentAssetKeys.THROMBIUM, ItemModelGenerator.CHESTPLATE_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_LEGGINGS, ArteryEquipmentAssetKeys.THROMBIUM, ItemModelGenerator.LEGGINGS_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ArteryItems.THROMBIUM_BOOTS, ArteryEquipmentAssetKeys.THROMBIUM, ItemModelGenerator.BOOTS_TRIM_ID_PREFIX, false);

        itemModelGenerator.register(ArteryItems.TOTEM_OF_REVIVAL, Models.GENERATED);
    }
}
