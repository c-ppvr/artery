package net.ppvr.artery.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.ppvr.artery.ArteryEntityAttributes;
import net.ppvr.artery.hooks.PlayerEntityHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityHooks {
    @Unique
    private static TrackedData<Float> SANGUINITY;
    @Unique
    private static TrackedData<Float> UNCONVERTED_SANGUINITY;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        cir.setReturnValue(
                cir.getReturnValue()
                        .add(ArteryEntityAttributes.MAX_SANGUINITY, 80.0)
                        .add(ArteryEntityAttributes.TRANSFUSION_RATE, 4.0)
                        .add(ArteryEntityAttributes.COAGULATION_RATE, 4.0)
        );
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void registerCustomData(CallbackInfo ci) {
        SANGUINITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
        UNCONVERTED_SANGUINITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(SANGUINITY, 0.0f);
        builder.add(UNCONVERTED_SANGUINITY, 0.0f);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat("Sanguinity", this.artery$getSanguinity());
        nbt.putFloat("UnconvertedSanguinity", this.artery$getUnconvertedSanguinity());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(SANGUINITY, nbt.getFloat("Sanguinity"));
        this.dataTracker.set(UNCONVERTED_SANGUINITY, nbt.getFloat("UnconvertedSanguinity"));
    }

    public float artery$getMaxSanguinity() {
        return (float) this.getAttributeValue(ArteryEntityAttributes.MAX_SANGUINITY);
    }
    public float artery$getTransfusionRate() {
        return (float) this.getAttributeValue(ArteryEntityAttributes.TRANSFUSION_RATE);
    }
    public float artery$getCoagulationRate() {
        return (float) this.getAttributeValue(ArteryEntityAttributes.COAGULATION_RATE);
    }
    public float artery$getSanguinity() {
        return this.dataTracker.get(SANGUINITY);
    }
    public void artery$setSanguinity(float amount) {
        this.dataTracker.set(SANGUINITY, MathHelper.clamp(amount, 0.0f, this.artery$getMaxSanguinity()));
    }
    public void artery$addSanguinity(float amount) {
        this.artery$setSanguinity(this.artery$getSanguinity()+amount);
    }
    public float artery$getUnconvertedSanguinity() {
        return this.dataTracker.get(UNCONVERTED_SANGUINITY);
    }
    public void artery$setUnconvertedSanguinity(float amount) {
        this.dataTracker.set(UNCONVERTED_SANGUINITY, Math.max(0.0f, amount));
    }
    public void artery$addUnconvertedSanguinity(float amount) {
        this.artery$setUnconvertedSanguinity(this.artery$getUnconvertedSanguinity()+amount);
    }
}