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
        int rows = maxSanguinity / 80;
        int fullRows = sanguinity / 80;
        int progress = sanguinity % 80;

        int left = context.getScaledWindowWidth() / 2 + 10;
        int top = context.getScaledWindowHeight() - 45;
        for (int i = 0; i < rows; ++i) {
            context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, left, top - 6 * i, 0, 0, 82, 5, 256, 256);
            if (i < fullRows) {
                context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, left, top - 6 * i, 0, 10, 82, 5, 256, 256);
            } else if (i == fullRows) {
                context.drawTexture(RenderLayer::getGuiTextured, SANGUINITY_BARS, left, top - 6 * i, 0, 5, 1 + progress, 5, 256, 256);
            }
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
}