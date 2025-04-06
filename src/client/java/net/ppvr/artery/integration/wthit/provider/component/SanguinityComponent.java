package net.ppvr.artery.integration.wthit.provider.component;

import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class SanguinityComponent implements ITooltipComponent {
    private static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/gui/singular_bars.png");
    private final int sanguinity;
    private final int capacity;
    private final Text text;

    public SanguinityComponent(int sanguinity, int capacity) {
        this.sanguinity = sanguinity;
        this.capacity = capacity;
        this.text = Text.of(sanguinity+"/"+capacity);
    }

    @Override
    public int getWidth() {
        return getTextRenderer().getWidth(text) + 14 + 1;
    }

    @Override
    public int getHeight() {
        return 11;
    }

    @Override
    public void render(DrawContext ctx, int x, int y, RenderTickCounter delta) {
        float v = sanguinity == 0 ? 0 : sanguinity == capacity ? 10 : 5;

        ctx.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y+2, 0, v, 11, 5, 16, 16);
        ctx.drawTextWithShadow(getTextRenderer(), text,x + 14, y + 1, 0xFFE96779);
    }

    private static TextRenderer getTextRenderer() {
        return MinecraftClient.getInstance().textRenderer;
    }
}
