package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.ppvr.artery.blocks.ArteryBlocks;

import java.util.concurrent.CompletableFuture;

public class ArteryBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected ArteryBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ArteryBlocks.ATRIUM);
        addDrop(ArteryBlocks.VENTRICLE, nameableContainerDrops(ArteryBlocks.VENTRICLE));
    }
}
