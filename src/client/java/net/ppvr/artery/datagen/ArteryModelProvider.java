package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.state.property.Properties;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.items.ArteryItems;

public class ArteryModelProvider extends FabricModelProvider {
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
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ArteryItems.ERYTHRITE, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.RAW_LEUKIUM, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.LEUKIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.RAW_THROMBIUM, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.THROMBIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ArteryItems.LEUKIUM_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ArteryItems.LEUKIUM_AXE, Models.HANDHELD);
    }
}
