package net.ppvr.artery.datagen.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ppvr.artery.recipe.InfusionRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static net.ppvr.artery.Artery.MOD_ID;

public class InfusionRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final ArteryRecipeCategory category;
    private final Item output;
    private final Ingredient input;
    private final int infusedAmount;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    private final InfusionRecipe.RecipeFactory<?> recipeFactory;
    @Nullable
    private String group;

    private InfusionRecipeJsonBuilder(
            ItemConvertible output,
            Ingredient input,
            int infusedAmount,
            InfusionRecipe.RecipeFactory<?> recipeFactory
    ) {
        this.category = ArteryRecipeCategory.INFUSION;
        this.output = output.asItem();
        this.input = input;
        this.infusedAmount = infusedAmount;
        this.recipeFactory = recipeFactory;
    }

    public static InfusionRecipeJsonBuilder create(Ingredient input, ItemConvertible output, int infusedAmount) {
        return new InfusionRecipeJsonBuilder(output, input, infusedAmount, InfusionRecipe::new);
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return output;
    }

    @Override
    public void offerTo(RecipeExporter exporter) {
        Identifier id = Identifier.of(MOD_ID, RecipeGenerator.getItemPath(getOutputItem()));
        this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, id));
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        validate(recipeKey);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        criteria.forEach(builder::criterion);
        InfusionRecipe recipe = recipeFactory
                .create(Objects.requireNonNullElse(group, ""), input, new ItemStack(output), infusedAmount);
        exporter.accept(recipeKey, recipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + category.getName() + "/")));
    }

    private void validate(RegistryKey<Recipe<?>> recipeKey) {
        if (criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeKey.getValue());
        }
    }
}
