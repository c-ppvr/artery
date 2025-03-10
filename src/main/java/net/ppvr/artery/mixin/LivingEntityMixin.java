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
import net.ppvr.artery.items.ArteryItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin extends Entity {
    @Unique boolean paid;

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
    private void tryUseDeathProtector(ItemStack itemStack, int amount, @Local Hand hand, @Local(ordinal = 0) ItemStack usedStack) {
        paid = false;
        if (itemStack.isOf(ArteryItems.TOTEM_OF_REVIVAL)) {
            if ((LivingEntity) (Object) this instanceof PlayerEntity playerEntity && playerEntity.artery$getSanguinity() >= 240) {
                itemStack.damage(1, playerEntity, LivingEntity.getSlotForHand(hand));
                playerEntity.artery$addSanguinity(-240);
                paid = true;
            }
        } else {
            itemStack.decrement(amount);
            paid = true;
        }
    }

    @Inject(method = "tryUseDeathProtector", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"), cancellable = true)
    private void doNotIncrementIfUnpaid(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (!paid) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tryUseDeathProtector", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), cancellable = true)
    private void doNotReviveIfUnpaid(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (!paid) {
            cir.setReturnValue(false);
        }
    }
}
