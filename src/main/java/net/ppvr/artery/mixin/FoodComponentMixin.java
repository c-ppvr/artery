package net.ppvr.artery.mixin;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import net.ppvr.artery.hooks.FoodComponentHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(FoodComponent.class)
public class FoodComponentMixin implements FoodComponentHooks{
    @Unique
    int sanguinity;

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/RecordCodecBuilder;create(Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"), remap = false)
    private static Codec<FoodComponent> putCodec(Function<RecordCodecBuilder.Instance<FoodComponent>, ? extends App<RecordCodecBuilder.Mu<FoodComponent>, FoodComponent>> builder) {
        return RecordCodecBuilder.create(instance -> instance.group(
                        Codecs.NON_NEGATIVE_INT.fieldOf("nutrition").forGetter(FoodComponent::nutrition),
                        Codec.FLOAT.fieldOf("saturation").forGetter(FoodComponent::saturation),
                        Codec.INT.fieldOf("sanguinity").forGetter(FoodComponentHooks::artery$sanguinity),
                        Codec.BOOL.optionalFieldOf("can_always_eat", false).forGetter(FoodComponent::canAlwaysEat)
                ).apply(instance, FoodComponentMixin::newWithSanguinity));
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function3;)Lnet/minecraft/network/codec/PacketCodec;"))
    private static PacketCodec<RegistryByteBuf, FoodComponent> putPacketCodec(
            PacketCodec<? super RegistryByteBuf, Integer> nutritionCodec,
            Function<FoodComponent, Integer> nutritionGetter,
            PacketCodec<? super RegistryByteBuf, Float> saturationCodec,
            Function<FoodComponent, Float> saturationGetter,
            PacketCodec<? super RegistryByteBuf, Boolean> canAlwaysEatCodec,
            Function<FoodComponent, Boolean> canAlwaysEatGetter,
            Function3<Integer, Float, Boolean, FoodComponent> constructor
    ) {
        return PacketCodec.tuple(
                nutritionCodec, nutritionGetter,
                saturationCodec, saturationGetter,
                PacketCodecs.INTEGER, FoodComponent::artery$sanguinity,
                canAlwaysEatCodec, canAlwaysEatGetter,
                FoodComponentMixin::newWithSanguinity
                );
    }
    @Unique
    private static FoodComponent newWithSanguinity(int nutrition, float saturation, int sanguinity, boolean canAlwaysEat) {
        FoodComponent foodComponent = new FoodComponent(nutrition, saturation, canAlwaysEat);
        foodComponent.artery$setSanguinity(sanguinity);
        return foodComponent;
    }
    @Override
    public int artery$sanguinity() {
        return sanguinity;
    }

    @Override
    public void artery$setSanguinity(int sanguinity) {
        this.sanguinity = sanguinity;
    }

    @Mixin(FoodComponent.Builder.class)
    public static abstract class Builder implements FoodComponentHooks.Builder {
        @Unique
        private int sanguinity = 0;

        @Override
        public FoodComponent.Builder artery$sanguinity(int sanguinity) {
            this.sanguinity = sanguinity;
            return (FoodComponent.Builder) (Object) this;
        }

        @Inject(method = "build", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent;<init>(IFZ)V"))
        public void build(CallbackInfoReturnable<FoodComponent> cir) {
            if (cir.getReturnValue() != null) {
                cir.getReturnValue().artery$setSanguinity(sanguinity);
            }
        }
    }
}
