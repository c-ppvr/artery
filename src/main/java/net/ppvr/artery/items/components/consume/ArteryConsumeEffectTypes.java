package net.ppvr.artery.items.components.consume;

import com.mojang.serialization.MapCodec;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ArteryConsumeEffectTypes {
    public static final ConsumeEffect.Type<GainSanguinityConsumeEffect> GAIN_SANGUINITY = register(
            "gain_sanguinity", GainSanguinityConsumeEffect.CODEC, GainSanguinityConsumeEffect.PACKET_CODEC
    );

    private static <T extends ConsumeEffect> ConsumeEffect.Type<T> register(String id, MapCodec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.CONSUME_EFFECT_TYPE, id, new ConsumeEffect.Type<>(codec, packetCodec));
    }
}
