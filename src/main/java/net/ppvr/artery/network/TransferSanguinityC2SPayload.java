package net.ppvr.artery.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public record TransferSanguinityC2SPayload(int amount) implements CustomPayload {
    public static final PacketCodec<RegistryByteBuf, TransferSanguinityC2SPayload> CODEC;
    public static final Id<TransferSanguinityC2SPayload> ID;

    static {
        CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, TransferSanguinityC2SPayload::amount, TransferSanguinityC2SPayload::new);
        ID = new Id<>(Identifier.of(MOD_ID, "transfer_sanguinity"));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
