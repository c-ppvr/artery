package net.ppvr.artery.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.recipe.InfusionRecipe;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryReiPluginClient implements REIClientPlugin {
    public static final CategoryIdentifier<InfusionDisplay> INFUSION = CategoryIdentifier.of(MOD_ID, "infusion");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new InfusionCategory());

        registry.addWorkstations(INFUSION, EntryStacks.of(ArteryBlocks.VENTRICLE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.beginFiller(InfusionRecipe.class)
                .fill(InfusionDisplay::new);
    }
}
