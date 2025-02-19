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
            private final ImmutableList<ItemConvertible> ERYTHRITE_ORES = ImmutableList.of(ArteryBlocks.ERYTHRITE_ORE.asItem(), ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE.asItem());
            @Override
            public void generate() {
                offerInfusion(Items.ROTTEN_FLESH, Items.BONE, 25);
                offerSmelting(ERYTHRITE_ORES, RecipeCategory.MISC, ArteryItems.ERYTHRITE, 0.7F, 200, "erythrite");
                offerBlasting(ERYTHRITE_ORES, RecipeCategory.MISC, ArteryItems.ERYTHRITE, 0.7F, 100, "erythrite");
                offerCompactingRecipe(RecipeCategory.MISC, ArteryBlocks.ERYTHRITE_BLOCK.asItem(), ArteryItems.ERYTHRITE);
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
