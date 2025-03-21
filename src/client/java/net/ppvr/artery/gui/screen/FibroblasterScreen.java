package net.ppvr.artery.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ppvr.artery.screen.FibroblasterScreenHandler;

import static net.ppvr.artery.Artery.MOD_ID;

public class FibroblasterScreen extends HandledScreen<FibroblasterScreenHandler> {
    public static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/gui/fibroblaster.png");

    public FibroblasterScreen(FibroblasterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        Text sanguinityText = Text.of("%d/%d".formatted(handler.getSanguinity(), handler.getCapacity()));
        context.drawText(textRenderer, sanguinityText, backgroundWidth - 12 - textRenderer.getWidth(sanguinityText), playerInventoryTitleY - 12, 0xFF000000, false);
    }
}
