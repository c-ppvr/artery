package net.ppvr.artery.mixin.appleskin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.util.math.MathHelper;
import net.ppvr.artery.items.components.consume.GainSanguinityConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.appleskin.helpers.ConsumableFood;
import squeek.appleskin.helpers.FoodHelper;

@Mixin(FoodHelper.class)
public class FoodHelperMixin {
    @Inject(method = "getEstimatedHealthIncrement(Lnet/minecraft/entity/player/PlayerEntity;Lsqueek/appleskin/helpers/ConsumableFood;)F", at = @At("RETURN"), cancellable = true)
    private static void getEstimatedHealth(PlayerEntity player, ConsumableFood consumableFood, CallbackInfoReturnable<Float> cir) {
        float totalAmount = 0;
        for (ConsumeEffect effect : consumableFood.consumable().onConsumeEffects()) {
            if (effect instanceof GainSanguinityConsumeEffect(float amount)) {
                totalAmount += amount;
            }
        }

        cir.setReturnValue(cir.getReturnValue() + MathHelper.floor(totalAmount / player.artery$getCoagulationRate()));
    }
}
