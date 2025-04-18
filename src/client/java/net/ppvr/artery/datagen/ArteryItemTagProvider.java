package net.ppvr.artery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
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
        getOrCreateTagBuilder(ArteryItemTags.REPAIRS_THROMBIUM_ARMOR).add(ArteryItems.THROMBIUM_INGOT);

        getOrCreateTagBuilder(ItemTags.SWORDS).add(ArteryItems.LEUKIUM_SWORD);
        getOrCreateTagBuilder(ItemTags.AXES).add(ArteryItems.LEUKIUM_AXE);

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).add(ArteryItems.THROMBIUM_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR).add(ArteryItems.THROMBIUM_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR).add(ArteryItems.THROMBIUM_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR).add(ArteryItems.THROMBIUM_BOOTS);
    }
}
