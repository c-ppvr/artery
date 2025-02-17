package net.ppvr.artery.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ppvr.artery.gui.widget.InfusionRecipeBookWidget;
import net.ppvr.artery.screen.VentricleScreenHandler;

import static net.ppvr.artery.Artery.MOD_ID;

public class VentricleScreen extends RecipeBookScreen<VentricleScreenHandler> {
    public static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/gui/ventricle.png");
    public VentricleScreen(VentricleScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new InfusionRecipeBookWidget(handler), inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0 ,0, backgroundWidth, backgroundHeight, 256, 256);
        int infusedTotalAmount = handler.getInfusedTotalAmount();
        if (infusedTotalAmount > 0) {
            int infusionProgress = 20 * handler.getInfusedAmountDone() / infusedTotalAmount;
            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 84, y + 36, 177, 1, 7, infusionProgress + 1, 256, 256);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        context.drawText(textRenderer, Text.of("%d".formatted(handler.getInfusedTotalAmount())), 95, 41, 0xFF000000, false);
        Text sanguinityText = Text.of("%d/%d".formatted(handler.getSanguinity(), handler.getCapacity()));
        context.drawText(textRenderer, sanguinityText, backgroundWidth - 12 - textRenderer.getWidth(sanguinityText), playerInventoryTitleY - 12, 0xFF000000, false);
    }

    @Override
    protected ScreenPos getRecipeBookButtonPos() {
        return new ScreenPos(x + 20, height/2 - 49);
    }
}
