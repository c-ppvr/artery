package net.ppvr.artery.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArteryRecipeProvider extends FabricRecipeProvider {
    public ArteryRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registry, RecipeExporter exporter) {
        return new RecipeGenerator(registry, exporter) {
            @Override
            public void generate() {
                offerInfusionRecipe(Items.ROTTEN_FLESH, Items.BONE, 25);
            }

            public void offerInfusionRecipe(ItemConvertible output, ItemConvertible input, int infusedAmount) {
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
