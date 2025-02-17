package net.ppvr.artery.gui.widget;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.GhostRecipe;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextParameterMap;
import net.ppvr.artery.blocks.entity.VentricleBlockEntity;
import net.ppvr.artery.recipe.InfusionRecipe;
import net.ppvr.artery.recipe.InfusionRecipeDisplay;
import net.ppvr.artery.screen.VentricleScreenHandler;

import java.util.List;

public class InfusionRecipeBookWidget extends RecipeBookWidget<VentricleScreenHandler> {
    private static final ButtonTextures TEXTURES = new ButtonTextures(
            Identifier.ofVanilla("recipe_book/filter_enabled"),
            Identifier.ofVanilla("recipe_book/filter_disabled"),
            Identifier.ofVanilla("recipe_book/filter_enabled_highlighted"),
            Identifier.ofVanilla("recipe_book/filter_disabled_highlighted")
    );
    public static final Text TOGGLE_INFUSIBLE_TEXT = Text.translatable("gui.artery.recipebook.toggleRecipes.infusible");
    private static final List<RecipeBookWidget.Tab> TABS = List.of(
            new RecipeBookWidget.Tab(Items.COMPASS, InfusionRecipe.BOOK_CATEGORY)
            );

    public InfusionRecipeBookWidget(VentricleScreenHandler handler) {
        super(handler, TABS);
    }

    @Override
    protected void setBookButtonTexture() {
        this.toggleCraftableButton.setTextures(TEXTURES);
    }

    @Override
    protected boolean isValid(Slot slot) {
        return slot.id == 0 || slot.id == 2;
    }

    @Override
    protected void populateRecipes(RecipeResultCollection recipeResultCollection, RecipeFinder recipeFinder) {
        recipeResultCollection.populateRecipes(recipeFinder, display -> display instanceof InfusionRecipeDisplay);
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_INFUSIBLE_TEXT;
    }

    @Override
    protected void showGhostRecipe(GhostRecipe ghostRecipe, RecipeDisplay display, ContextParameterMap context) {
        if (display instanceof InfusionRecipeDisplay infusionRecipeDisplay) {
            ghostRecipe.addInputs(craftingScreenHandler.getSlot(VentricleBlockEntity.INPUT_SLOT_INDEX), context, infusionRecipeDisplay.ingredient());
            Slot outputSlot = craftingScreenHandler.getSlot(VentricleBlockEntity.OUTPUT_SLOT_INDEX);
            if (outputSlot.getStack().isEmpty()) {
                ghostRecipe.addResults(outputSlot, context, display.result());
            }
        }
    }
}
