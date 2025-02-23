package net.ppvr.artery;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.ppvr.artery.datagen.feature.ArteryOrePlacedFeatures;
import net.ppvr.artery.gui.screen.ArteryScreens;

public class ArteryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArteryScreens.initialize();
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ArteryOrePlacedFeatures.ORE_ERYTHIRTE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ArteryOrePlacedFeatures.ORE_LEUKIUM);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ArteryOrePlacedFeatures.ORE_THROMBIUM);
    }
}