package net.ppvr.artery.items.components.consume;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public record GainSanguinityConsumeEffect(float amount) implements ConsumeEffect {
    public static final MapCodec<GainSanguinityConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codecs.POSITIVE_FLOAT.fieldOf("amount").forGetter(GainSanguinityConsumeEffect::amount))
                    .apply(instance, GainSanguinityConsumeEffect::new)
    );
    public static final PacketCodec<RegistryByteBuf, GainSanguinityConsumeEffect> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.FLOAT, GainSanguinityConsumeEffect::amount, GainSanguinityConsumeEffect::new);

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ArteryConsumeEffectTypes.GAIN_SANGUINITY;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            player.artery$addSanguinity(amount);
            return true;
        }
        return false;
    }
}
