package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.ppvr.artery.datagen.feature.ArteryOreConfiguredFeatures;
import net.ppvr.artery.datagen.feature.ArteryOrePlacedFeatures;
import net.ppvr.artery.datagen.recipe.ArteryRecipeProvider;

public class ArteryDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ArteryRecipeProvider::new);
        pack.addProvider(ArteryBlockLootTableProvider::new);
        pack.addProvider(ArteryBlockTagProvider::new);
        pack.addProvider(ArteryModelProvider::new);
        pack.addProvider(ArteryDynamicRegistriesProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder
                .addRegistry(RegistryKeys.CONFIGURED_FEATURE, ArteryOreConfiguredFeatures::bootstrap)
                .addRegistry(RegistryKeys.PLACED_FEATURE, ArteryOrePlacedFeatures::bootstrap);
    }
}
