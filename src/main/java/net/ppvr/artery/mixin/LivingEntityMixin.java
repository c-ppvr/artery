package net.ppvr.artery.mixin;

import net.minecraft.advancement.criterion.PlayerHurtEntityCriterion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/PlayerHurtEntityCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;FFZ)V"))
    private void damage(PlayerHurtEntityCriterion instance, ServerPlayerEntity player, Entity entity, DamageSource damage, float dealt, float taken, boolean blocked) {
        double falloff = 1.0;
        if (!damage.isDirect()) {
            falloff = Math.max(1.0, distanceTo(damage.getAttacker())/4.0);
        }
        player.artery$addUnconvertedSanguinity((float) (taken / player.artery$getTransfusionRate() / falloff));
    }
}
