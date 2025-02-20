package net.ppvr.artery.datagen.recipe;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.items.ArteryItems;

import java.util.concurrent.CompletableFuture;

public class ArteryRecipeProvider extends FabricRecipeProvider {
    public ArteryRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registry, RecipeExporter exporter) {
        return new RecipeGenerator(registry, exporter) {
            private final ImmutableList<ItemConvertible> ERYTHRITE_ORES = ImmutableList.of(ArteryBlocks.ERYTHRITE_ORE, ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE);
            private final ImmutableList<ItemConvertible> LEUKIUM_ORES = ImmutableList.of(ArteryBlocks.LEUKIUM_ORE, ArteryBlocks.DEEPSLATE_LEUKIUM_ORE, ArteryItems.RAW_LEUKIUM);
            private final ImmutableList<ItemConvertible> THROMBIUM_ORES = ImmutableList.of(ArteryBlocks.THROMBIUM_ORE, ArteryBlocks.DEEPSLATE_THROMBIUM_ORE, ArteryItems.RAW_THROMBIUM);

            @Override
            public void generate() {
                offerInfusion(Items.ROTTEN_FLESH, Items.BONE, 25);

                offerSmelting(ERYTHRITE_ORES, RecipeCategory.MISC, ArteryItems.ERYTHRITE, 0.7F, 200, "erythrite");
                offerBlasting(ERYTHRITE_ORES, RecipeCategory.MISC, ArteryItems.ERYTHRITE, 0.7F, 100, "erythrite");
                offerCompactingRecipe(RecipeCategory.MISC, ArteryBlocks.ERYTHRITE_BLOCK, ArteryItems.ERYTHRITE);

                offerSmelting(LEUKIUM_ORES, RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, 0.7F, 200, "leukium_ingot");
                offerBlasting(LEUKIUM_ORES, RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, 0.7F, 100, "leukium_ingot");
                offerCompactingRecipe(RecipeCategory.MISC, ArteryBlocks.RAW_LEUKIUM_BLOCK, ArteryItems.RAW_LEUKIUM);
                offerCompactingRecipe(RecipeCategory.MISC, ArteryBlocks.LEUKIUM_BLOCK, ArteryItems.LEUKIUM_INGOT);

                offerSmelting(THROMBIUM_ORES, RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, 0.7F, 200, "thrombium_ingot");
                offerBlasting(THROMBIUM_ORES, RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, 0.7F, 100, "thrombium_ingot");
                offerCompactingRecipe(RecipeCategory.MISC, ArteryBlocks.RAW_THROMBIUM_BLOCK, ArteryItems.RAW_THROMBIUM);
                offerCompactingRecipe(RecipeCategory.MISC, ArteryBlocks.THROMBIUM_BLOCK, ArteryItems.THROMBIUM_INGOT);
            }

            public void offerInfusion(ItemConvertible output, ItemConvertible input, int infusedAmount) {
                InfusionRecipeJsonBuilder.create(Ingredient.ofItem(input), output, infusedAmount)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Artery Recipes";
    }
}
