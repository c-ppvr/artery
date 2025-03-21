package net.ppvr.artery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Shadow
    private int foodTickTimer;
    @Unique
    private int sanguinityTickTimer;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    public void update(ServerPlayerEntity player, CallbackInfo ci) {
        ++sanguinityTickTimer;
        if (sanguinityTickTimer >= 5) {
            if (player.artery$getUnconvertedSanguinity() >= 1) {
                player.artery$addUnconvertedSanguinity(-1);
                player.artery$addSanguinity(1);
                if (player.artery$getSanguinity() == player.artery$getMaxSanguinity()) {
                    player.artery$setUnconvertedSanguinity(0);
                }
                this.sanguinityTickTimer = 0;
            }
        }
    }

    @Inject(method = "update", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"), cancellable = true)
    public void update(ServerPlayerEntity player, CallbackInfo ci, @Local ServerWorld serverWorld) {
        float sanguinity = player.artery$getSanguinity();
        float coagulationRate = player.artery$getCoagulationRate();
        if (sanguinity >= coagulationRate && serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION) && player.canFoodHeal()) {
            ++foodTickTimer;
            if (foodTickTimer >= 1600 / (9 * sanguinity + 80)) {
                player.heal(1);
                player.artery$addSanguinity(-coagulationRate);
                this.foodTickTimer = 0;
            }
            ci.cancel();
        }
    }
}
