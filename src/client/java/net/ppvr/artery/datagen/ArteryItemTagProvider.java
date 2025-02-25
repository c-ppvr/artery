package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.ppvr.artery.items.ArteryItemTags;
import net.ppvr.artery.items.ArteryItems;

import java.util.concurrent.CompletableFuture;

public class ArteryItemTagProvider extends FabricTagProvider<Item> {
    ArteryItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ArteryItemTags.LEUKIUM_TOOL_MATERIALS).add(ArteryItems.LEUKIUM_INGOT);
    }
}
