package net.ppvr.artery.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.ppvr.artery.blocks.ArteryBlocks;

import java.util.ArrayList;
import java.util.List;

public class InfusionCategory implements DisplayCategory<InfusionDisplay> {
    @Override
    public List<Widget> setupDisplay(InfusionDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        return widgets;
    }

    @Override
    public CategoryIdentifier<InfusionDisplay> getCategoryIdentifier() {
        return ArteryReiPluginClient.INFUSION;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.artery.infusion");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ArteryBlocks.VENTRICLE);
    }
}
