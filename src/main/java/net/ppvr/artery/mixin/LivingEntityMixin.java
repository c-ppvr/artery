package net.ppvr.artery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/PlayerHurtEntityCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;FFZ)V"))
    private void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity attacker = (PlayerEntity) source.getAttacker();
        double falloff = 1.0;
        if (!source.isDirect()) {
            falloff = Math.max(1.0, distanceTo(source.getAttacker())/4.0);
        }
        attacker.artery$addUnconvertedSanguinity((float) (amount / attacker.artery$getTransfusionRate() / falloff));
    }

    @Redirect(method = "tryUseDeathProtector", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private void tryUseDeathProtector(ItemStack itemStack, int amount, @Local Hand hand) {
        if (itemStack.isDamageable()) {
            itemStack.damage(1, (LivingEntity) (Object) this, LivingEntity.getSlotForHand(hand));
        } else {
            itemStack.decrement(amount);
        }
    }
}
