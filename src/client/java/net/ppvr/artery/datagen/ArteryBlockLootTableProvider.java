package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.items.ArteryItems;

import java.util.concurrent.CompletableFuture;

public class ArteryBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected ArteryBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ArteryBlocks.ATRIUM);
        addDrop(ArteryBlocks.VENTRICLE, nameableContainerDrops(ArteryBlocks.VENTRICLE));
        addDrop(ArteryBlocks.FIBROBLASTER, nameableContainerDrops(ArteryBlocks.FIBROBLASTER));
        addDrop(ArteryBlocks.PRESSOR, nameableContainerDrops(ArteryBlocks.PRESSOR));
        addDrop(ArteryBlocks.ERYTHRITE_ORE, this::erythriteOreDrops);
        addDrop(ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE, this::erythriteOreDrops);
        addDrop(ArteryBlocks.ERYTHRITE_BLOCK);
        addDrop(ArteryBlocks.LEUKIUM_ORE, block -> oreDrops(block, ArteryItems.RAW_LEUKIUM));
        addDrop(ArteryBlocks.DEEPSLATE_LEUKIUM_ORE, block -> oreDrops(block, ArteryItems.RAW_LEUKIUM));
        addDrop(ArteryBlocks.RAW_LEUKIUM_BLOCK);
        addDrop(ArteryBlocks.LEUKIUM_BLOCK);
        addDrop(ArteryBlocks.THROMBIUM_ORE, block -> oreDrops(block, ArteryItems.RAW_THROMBIUM));
        addDrop(ArteryBlocks.DEEPSLATE_THROMBIUM_ORE, block -> oreDrops(block, ArteryItems.RAW_THROMBIUM));
        addDrop(ArteryBlocks.RAW_THROMBIUM_BLOCK);
        addDrop(ArteryBlocks.THROMBIUM_BLOCK);
    }

    private LootTable.Builder erythriteOreDrops(Block drop) {
        RegistryWrapper.Impl<Enchantment> enchantments = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        return dropsWithSilkTouch(
                drop,
                applyExplosionDecay(drop,
                        ItemEntry.builder(ArteryItems.ERYTHRITE)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 3.0f)))
                                .apply(ApplyBonusLootFunction.oreDrops(enchantments.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }
}
