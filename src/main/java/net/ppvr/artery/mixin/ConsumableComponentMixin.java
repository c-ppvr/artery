package net.ppvr.artery.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConsumableComponent.class)
public abstract class ConsumableComponentMixin {
    @Inject(method = "canConsume", at = @At(value = "RETURN"), cancellable = true)
    public void canConsume(LivingEntity user, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || (
                user instanceof PlayerEntity player
                        && player.artery$getSanguinity() != player.artery$getMaxSanguinity()
                        && stack.getComponents().get(DataComponentTypes.FOOD).artery$sanguinity() > 0
                )
        );
    }
}
