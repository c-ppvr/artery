package net.ppvr.artery.mixin.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow
    public abstract void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int z, int color);

    @Inject(method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemBar(Lnet/minecraft/item/ItemStack;II)V"))
    public void drawStackOverlay(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci){
        drawPressureBar(stack, x, y);
    }

    @Unique
    private void drawPressureBar(ItemStack stack, int x, int y) {
        if (stack.artery$getPressure() > 0) {
            int i = x + 2;
            int j = y + 12;
            fill(RenderLayer.getGui(), i, j, i + 13, j + 1, 200, Colors.BLACK);
            fill(
                    RenderLayer.getGui(),
                    i, j,
                    i + MathHelper.clamp(Math.round(stack.artery$getPressure() * 13.0f / stack.artery$getMaxPressure()), 0, 13), j + 1,
                    200, ColorHelper.fullAlpha(MathHelper.hsvToRgb(0.0f, 1.0f - 0.8f * stack.artery$getPressure() / stack.artery$getMaxPressure(), 1.0f))
            );
        }
    }
}
