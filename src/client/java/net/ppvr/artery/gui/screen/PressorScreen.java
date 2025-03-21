package net.ppvr.artery.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.ppvr.artery.screen.PressorScreenHandler;

import static net.ppvr.artery.Artery.MOD_ID;

public class PressorScreen extends HandledScreen<PressorScreenHandler> {
    public static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/gui/pressor.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");

    public PressorScreen(PressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0.0f, 0.0f, backgroundWidth, backgroundHeight, 256, 256);
        if (handler.isBurning()) {
            int progress = MathHelper.ceil(handler.getFuelProgress() * 13.0f) + 1;
            context.drawGuiTexture(RenderLayer::getGuiTextured, LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - progress, x + 80, y + 50 - progress, 14, progress);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        ItemStack itemStack = handler.getSlot(0).getStack();
        if (!itemStack.isEmpty()) {
            Text pressureText = Text.of("%d/%d".formatted(itemStack.artery$getPressure(), itemStack.artery$getMaxPressure()));
            context.drawText(textRenderer, pressureText, backgroundWidth - 12 - textRenderer.getWidth(pressureText), handler.getSlot(0).y + 9, 0xFF000000, false);
        }
        Text sanguinityText = Text.of("%d/%d".formatted(handler.getSanguinity(), handler.getCapacity()));
        context.drawText(textRenderer, sanguinityText, backgroundWidth - 12 - textRenderer.getWidth(sanguinityText), playerInventoryTitleY - 12, 0xFF000000, false);
    }
}
