package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.ppvr.artery.blocks.ArteryBlocks;

import java.util.concurrent.CompletableFuture;

public class ArteryBlockTagProvider extends FabricTagProvider<Block> {
    public ArteryBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
                .add(
                        ArteryBlocks.VENTRICLE,
                        ArteryBlocks.ATRIUM,
                        ArteryBlocks.FIBROBLASTER,
                        ArteryBlocks.ERYTHRITE_BLOCK,
                        ArteryBlocks.LEUKIUM_BLOCK,
                        ArteryBlocks.THROMBIUM_BLOCK,
                        ArteryBlocks.HEMOGLOBIUM_BLOCK
                );
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(
                        ArteryBlocks.ERYTHRITE_ORE,
                        ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE,
                        ArteryBlocks.ERYTHRITE_BLOCK,
                        ArteryBlocks.LEUKIUM_ORE,
                        ArteryBlocks.DEEPSLATE_LEUKIUM_ORE,
                        ArteryBlocks.RAW_LEUKIUM_BLOCK,
                        ArteryBlocks.LEUKIUM_BLOCK,
                        ArteryBlocks.THROMBIUM_ORE,
                        ArteryBlocks.DEEPSLATE_THROMBIUM_ORE,
                        ArteryBlocks.RAW_THROMBIUM_BLOCK,
                        ArteryBlocks.THROMBIUM_BLOCK,
                        ArteryBlocks.HEMOGLOBIUM_BLOCK
                );
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(
                        ArteryBlocks.LEUKIUM_BLOCK,
                        ArteryBlocks.THROMBIUM_BLOCK,
                        ArteryBlocks.HEMOGLOBIUM_BLOCK
                );
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(
                        ArteryBlocks.ERYTHRITE_ORE,
                        ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE,
                        ArteryBlocks.ERYTHRITE_BLOCK
                );
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        ArteryBlocks.LEUKIUM_ORE,
                        ArteryBlocks.DEEPSLATE_LEUKIUM_ORE,
                        ArteryBlocks.RAW_LEUKIUM_BLOCK,
                        ArteryBlocks.LEUKIUM_BLOCK,
                        ArteryBlocks.THROMBIUM_ORE,
                        ArteryBlocks.DEEPSLATE_THROMBIUM_ORE,
                        ArteryBlocks.RAW_THROMBIUM_BLOCK,
                        ArteryBlocks.THROMBIUM_BLOCK,
                        ArteryBlocks.HEMOGLOBIUM_BLOCK
                );
    }
}