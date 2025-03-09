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
import net.ppvr.artery.items.ArteryItemTags;
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
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.ERYTHRITE, RecipeCategory.BUILDING_BLOCKS,  ArteryBlocks.ERYTHRITE_BLOCK);

                offerSmelting(LEUKIUM_ORES, RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, 0.7F, 200, "leukium_ingot");
                offerBlasting(LEUKIUM_ORES, RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, 0.7F, 100, "leukium_ingot");
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.RAW_LEUKIUM, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.RAW_LEUKIUM_BLOCK);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.LEUKIUM_BLOCK);

                offerSmelting(THROMBIUM_ORES, RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, 0.7F, 200, "thrombium_ingot");
                offerBlasting(THROMBIUM_ORES, RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, 0.7F, 100, "thrombium_ingot");
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.RAW_THROMBIUM, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.RAW_THROMBIUM_BLOCK);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.THROMBIUM_BLOCK);

                createShaped(RecipeCategory.TOOLS, ArteryItems.LEUKIUM_AXE)
                        .input('#', Items.STICK)
                        .input('X', ArteryItemTags.LEUKIUM_TOOL_MATERIALS)
                        .pattern("XX")
                        .pattern("X#")
                        .pattern(" #")
                        .criterion("has_leukium_ingot", conditionsFromTag(ArteryItemTags.LEUKIUM_TOOL_MATERIALS))
                        .offerTo(exporter);
                createShaped(RecipeCategory.COMBAT, ArteryItems.LEUKIUM_SWORD)
                        .input('#', Items.STICK)
                        .input('X', ArteryItemTags.LEUKIUM_TOOL_MATERIALS)
                        .pattern("X")
                        .pattern("X")
                        .pattern("#")
                        .criterion("has_leukium_ingot", conditionsFromTag(ArteryItemTags.LEUKIUM_TOOL_MATERIALS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.COMBAT, ArteryItems.THROMBIUM_HELMET)
                        .input('X', ArteryItems.THROMBIUM_INGOT)
                        .pattern("XXX")
                        .pattern("X X")
                        .criterion("has_thrombium_ingot", conditionsFromItem(ArteryItems.THROMBIUM_INGOT))
                        .offerTo(exporter);
                createShaped(RecipeCategory.COMBAT, ArteryItems.THROMBIUM_CHESTPLATE)
                        .input('X', ArteryItems.THROMBIUM_INGOT)
                        .pattern("X X")
                        .pattern("XXX")
                        .pattern("XXX")
                        .criterion("has_thrombium_ingot", conditionsFromItem(ArteryItems.THROMBIUM_INGOT))
                        .offerTo(exporter);
                createShaped(RecipeCategory.COMBAT, ArteryItems.THROMBIUM_LEGGINGS)
                        .input('X', ArteryItems.THROMBIUM_INGOT)
                        .pattern("XXX")
                        .pattern("X X")
                        .pattern("X X")
                        .criterion("has_thrombium_ingot", conditionsFromItem(ArteryItems.THROMBIUM_INGOT))
                        .offerTo(exporter);
                createShaped(RecipeCategory.COMBAT, ArteryItems.THROMBIUM_BOOTS)
                        .input('X', ArteryItems.THROMBIUM_INGOT)
                        .pattern("X X")
                        .pattern("X X")
                        .criterion("has_thrombium_ingot", conditionsFromItem(ArteryItems.THROMBIUM_INGOT))
                        .offerTo(exporter);
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
