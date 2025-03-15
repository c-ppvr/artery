package net.ppvr.artery.datagen.recipe;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.items.ArteryItemTags;
import net.ppvr.artery.items.ArteryItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.ppvr.artery.Artery.MOD_ID;

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
                offerInfusion(Items.ROTTEN_FLESH, Items.BONE, 10);
                offerInfusion(ArteryItems.FLESH, Items.ROTTEN_FLESH, 25);

                offerModSmelting(ERYTHRITE_ORES, RecipeCategory.MISC, ArteryItems.ERYTHRITE, 0.7F, 200, "erythrite");
                offerModBlasting(ERYTHRITE_ORES, RecipeCategory.MISC, ArteryItems.ERYTHRITE, 0.7F, 100, "erythrite");
                offerModReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.ERYTHRITE, RecipeCategory.BUILDING_BLOCKS,  ArteryBlocks.ERYTHRITE_BLOCK);

                offerModSmelting(LEUKIUM_ORES, RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, 0.7F, 200, "leukium_ingot");
                offerModBlasting(LEUKIUM_ORES, RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, 0.7F, 100, "leukium_ingot");
                offerModReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.RAW_LEUKIUM, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.RAW_LEUKIUM_BLOCK);
                offerModReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.LEUKIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.LEUKIUM_BLOCK);

                offerModSmelting(THROMBIUM_ORES, RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, 0.7F, 200, "thrombium_ingot");
                offerModBlasting(THROMBIUM_ORES, RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, 0.7F, 100, "thrombium_ingot");
                offerModReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.RAW_THROMBIUM, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.RAW_THROMBIUM_BLOCK);
                offerModReversibleCompactingRecipes(RecipeCategory.MISC, ArteryItems.THROMBIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.THROMBIUM_BLOCK);

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

                createShaped(RecipeCategory.MISC, ArteryItems.HEMOGLOBIUM_INGOT)
                        .input('E', ArteryItems.ERYTHRITE)
                        .input('I', Items.IRON_INGOT)
                        .pattern("EI")
                        .pattern("IE")
                        .criterion(hasItem(ArteryItems.ERYTHRITE), conditionsFromItem(ArteryItems.ERYTHRITE))
                        .offerTo(exporter);
                offerModReversibleCompactingRecipesWithReverseRecipeGroup(
                        RecipeCategory.MISC, ArteryItems.HEMOGLOBIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, ArteryBlocks.HEMOGLOBIUM_BLOCK, "hemoglobium_ingot_from_hemoglobium_block", "hemoglobium_ingot"
                );
                createShaped(RecipeCategory.DECORATIONS, ArteryBlocks.ATRIUM)
                        .input('#', ArteryItems.HEMOGLOBIUM_INGOT)
                        .input('C', Items.CHEST)
                        .pattern("###")
                        .pattern("#C#")
                        .pattern("###")
                        .criterion(hasItem(ArteryItems.HEMOGLOBIUM_INGOT), conditionsFromItem(ArteryItems.HEMOGLOBIUM_INGOT))
                        .offerTo(exporter);
                createShaped(RecipeCategory.DECORATIONS, ArteryBlocks.VENTRICLE)
                        .input('#', ArteryItems.HEMOGLOBIUM_INGOT)
                        .input('D', Items.DISPENSER)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .criterion(hasItem(ArteryItems.HEMOGLOBIUM_INGOT), conditionsFromItem(ArteryItems.HEMOGLOBIUM_INGOT))
                        .offerTo(exporter);
                createShaped(RecipeCategory.DECORATIONS, ArteryBlocks.FIBROBLASTER)
                        .input('#', ArteryItems.FLESH)
                        .input('D', Items.DIAMOND)
                        .input('T', ArteryItems.THROMBIUM_INGOT)
                        .pattern("#T#")
                        .pattern("#D#")
                        .pattern("#T#")
                        .criterion(hasItem(ArteryItems.FLESH), conditionsFromItem(ArteryItems.FLESH))
                        .offerTo(exporter);
                createShaped(RecipeCategory.DECORATIONS, ArteryBlocks.PRESSOR)
                        .input('#', ArteryItems.HEMOGLOBIUM_INGOT)
                        .input('D', Items.DIAMOND)
                        .input('F', Items.FURNACE)
                        .pattern("#D#")
                        .pattern("#F#")
                        .pattern("###")
                        .criterion(hasItem(ArteryItems.FLESH), conditionsFromItem(ArteryItems.FLESH))
                        .offerTo(exporter);
                createShaped(RecipeCategory.COMBAT, ArteryItems.TOTEM_OF_REVIVAL)
                        .input('#', Items.TOTEM_OF_UNDYING)
                        .input('N', Items.NETHERITE_INGOT)
                        .input('H', ArteryItems.HEMOGLOBIUM_INGOT)
                        .input('F', ArteryItems.FLESH)
                        .pattern("FNF")
                        .pattern("H#H")
                        .pattern(" N ")
                        .criterion(hasItem(ArteryItems.FLESH), conditionsFromItem(ArteryItems.FLESH))
                        .offerTo(exporter);
            }

            public void offerInfusion(ItemConvertible output, ItemConvertible input, int infusedAmount) {
                InfusionRecipeJsonBuilder.create(Ingredient.ofItem(input), output, infusedAmount)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter);
            }

            public void offerModSmelting(List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
                offerModMultipleOptions(RecipeSerializer.SMELTING, SmeltingRecipe::new, inputs, category, output, experience, cookingTime, group, "_from_smelting");
            }

            public void offerModBlasting(List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
                offerModMultipleOptions(RecipeSerializer.BLASTING, BlastingRecipe::new, inputs, category, output, experience, cookingTime, group, "_from_blasting");
            }

            public <T extends AbstractCookingRecipe> void offerModMultipleOptions(
                    RecipeSerializer<T> serializer,
                    AbstractCookingRecipe.RecipeFactory<T> recipeFactory,
                    List<ItemConvertible> inputs,
                    RecipeCategory category,
                    ItemConvertible output,
                    float experience,
                    int cookingTime,
                    String group,
                    String suffix
            ) {
                for (ItemConvertible input : inputs) {
                    CookingRecipeJsonBuilder.create(Ingredient.ofItem(input), category, output, experience, cookingTime, serializer, recipeFactory)
                            .group(group)
                            .criterion(hasItem(input), conditionsFromItem(input))
                            .offerTo(exporter, MOD_ID + ":"  + getItemPath(output) + suffix + "_" + getItemPath(input));
                }
            }

            public void offerModReversibleCompactingRecipes(
                    RecipeCategory reverseCategory, ItemConvertible baseItem, RecipeCategory compactingCategory, ItemConvertible compactItem
            ) {
                offerModReversibleCompactingRecipes(
                        reverseCategory, baseItem, compactingCategory, compactItem, getRecipeName(compactItem), null, getRecipeName(baseItem), null
                );
            }

            public void offerModReversibleCompactingRecipesWithReverseRecipeGroup(
                    RecipeCategory reverseCategory,
                    ItemConvertible baseItem,
                    RecipeCategory compactingCategory,
                    ItemConvertible compactItem,
                    String reverseId,
                    String reverseGroup
            ) {
                offerModReversibleCompactingRecipes(reverseCategory, baseItem, compactingCategory, compactItem, getRecipeName(compactItem), null, reverseId, reverseGroup);
            }

            public void offerModReversibleCompactingRecipes(
                    RecipeCategory reverseCategory,
                    ItemConvertible baseItem,
                    RecipeCategory compactingCategory,
                    ItemConvertible compactItem,
                    String compactingId,
                    @Nullable String compactingGroup,
                    String reverseId,
                    @Nullable String reverseGroup
            ) {
                this.createShapeless(reverseCategory, baseItem, 9)
                        .input(compactItem)
                        .group(reverseGroup)
                        .criterion(hasItem(compactItem), this.conditionsFromItem(compactItem))
                        .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(MOD_ID, reverseId)));
                this.createShaped(compactingCategory, compactItem)
                        .input('#', baseItem)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .group(compactingGroup)
                        .criterion(hasItem(baseItem), this.conditionsFromItem(baseItem))
                        .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(MOD_ID, compactingId)));
            }
        };
    }

    @Override
    public String getName() {
        return "Artery Recipes";
    }
}
