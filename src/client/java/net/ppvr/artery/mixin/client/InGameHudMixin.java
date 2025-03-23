package net.ppvr.artery.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profilers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.ppvr.artery.Artery.MOD_ID;
import static net.ppvr.artery.ArteryEntityAttributes.MAX_SANGUINITY;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier SANGUINITY_BARS = Identifier.of(MOD_ID, "textures/gui/sanguinity_bars.png");

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
    public void renderSanguinity(DrawContext context, CallbackInfo ci, @Local PlayerEntity playerEntity) {
        Profilers.get().swap("sanguinity");
        int sanguinity = (int) playerEntity.artery$getSanguinity();
        int maxSanguinity = (int) playerEntity.getAttributeValue(MAX_SANGUINITY);
        int totalRows = maxSanguinity / 80;
        int rowPart = maxSanguinity % 80;
        int filledRows = sanguinity / 80;
        int progress = sanguinity % 80;

        int left = context.getScaledWindowWidth() / 2 + 10;
        int top = context.getScaledWindowHeight() - 45;
        for (int row = 0; row < totalRows; ++ row) {
            if (row < filledRows) {
                drawBar(context, left, top - 6 * row, 10, 82);
                continue;
            }
            drawBar(context, left, top - 6 * row, 0, 82);
            if (row == filledRows) {
                drawBar(context, left, top - 6 * row, 5, 1 + progress);
            }
        }

        if (rowPart > 0) {
            drawShortBar(context, left, top - 6 * totalRows, filledRows == totalRows ? progress : 0, rowPart);
        }

        String text = sanguinity + "/" + maxSanguinity;
        int x = left + 84;
        int y = top - 4;
        context.drawText(getTextRenderer(), text, x + 1, y, 0, false);
        context.drawText(getTextRenderer(), text, x - 1, y, 0, false);
        context.drawText(getTextRenderer(), text, x, y + 1, 0, false);
        context.drawText(getTextRenderer(), text, x, y - 1, 0, false);
        context.drawText(getTextRenderer(), text, x, y, 15296377, false);
    }

    @ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderAirBubbles(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;III)V"), index = 3)
    public int shiftBubblesUp(int y, @Local PlayerEntity playerEntity) {
        return y - (int) (playerEntity.getAttributeValue(MAX_SANGUINITY) / 80) * 6;
    }

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Unique
    private void drawBar(DrawContext context, int x, int y, int v, int width) {
        context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, x, y, 0, v, width, 5, 256, 256);
    }

    @Unique
    private void drawShortBar(DrawContext context, int x, int y, int progress, int part) {
        context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, x, y, 0,  0, 1 + part, 5, 256, 256);
        context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, x + 81, y, 81, progress == part ? 10 : 0, 1, 5, 256, 256);
        context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, x, y, 0, progress == part ? 10 : 5, 1 + progress, 5, 256, 256);
    }
}