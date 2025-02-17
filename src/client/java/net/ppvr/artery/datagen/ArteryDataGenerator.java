package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.ppvr.artery.datagen.recipe.ArteryRecipeProvider;

public class ArteryDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ArteryRecipeProvider::new);
        pack.addProvider(ArteryBlockLootTableProvider::new);
        pack.addProvider(ArteryBlockTagProvider::new);
        pack.addProvider(ArteryModelProvider::new);
    }
}
