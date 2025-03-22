package net.ppvr.artery.mixin.client.appleskin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.ppvr.artery.hooks.TooltipOverlayHandlerHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.appleskin.client.TooltipOverlayHandler;

import static net.ppvr.artery.Artery.MOD_ID;

@Mixin(TooltipOverlayHandler.class)
public class TooltipOverlayHandlerMixin {
    @Mixin(TooltipOverlayHandler.FoodOverlay.class)
    public static class FoodOverlayMixin implements TooltipOverlayHandlerHooks.FoodOverlay {
        @Unique
        private String sanguinityText;

        @Inject(method = "<init>", at = @At("TAIL"))
        public void init(ItemStack itemStack, FoodComponent defaultFood, FoodComponent modifiedFood, ConsumableComponent consumableComponent, PlayerEntity player, CallbackInfo ci) {
            this.sanguinityText = "" + modifiedFood.artery$sanguinity();
        }

        @Inject(method = "getHeight", at = @At("RETURN"), cancellable = true)
        public void getHeight(TextRenderer textRenderer, CallbackInfoReturnable<Integer> cir) {
            cir.setReturnValue(cir.getReturnValue() + 1 + 7);
        }

        @Inject(method = "getWidth", at = @At("RETURN"), cancellable = true)
        public void getWidth(TextRenderer textRenderer, CallbackInfoReturnable<Integer> cir) {
            cir.setReturnValue(Math.max(cir.getReturnValue(), textRenderer.getWidth(sanguinityText)));
        }

        @Override
        public String artery$getSanguinityText() {
            return sanguinityText;
        }
    }

    @Inject(method = "onRenderTooltip", at = @At("TAIL"))
    public void onRenderToolTip(DrawContext context, TooltipOverlayHandler.FoodOverlay foodOverlay, int toolTipX, int toolTipY, int tooltipZ, TextRenderer textRenderer, CallbackInfo ci, @Local(name = "matrixStack") MatrixStack matrixStack, @Local(name = "y") int y) {
        int x = toolTipX;
        y += 8;
        context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(MOD_ID, "textures/gui/singular_bars.png"), x, y + 2, 0, 10, 11, 5, 16, 16);
        x += 11;
        matrixStack.push();
        matrixStack.translate(x, y, tooltipZ);
        matrixStack.scale(0.75f, 0.75f, 0.75f);
        context.drawTextWithShadow(textRenderer, ((TooltipOverlayHandlerHooks.FoodOverlay) foodOverlay).artery$getSanguinityText(), 2, 2, 0xFFE96779);
        matrixStack.pop();
    }
}
