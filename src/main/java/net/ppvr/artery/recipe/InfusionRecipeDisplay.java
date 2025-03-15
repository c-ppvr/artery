package net.ppvr.artery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;

public record InfusionRecipeDisplay(SlotDisplay ingredient, SlotDisplay result,  SlotDisplay craftingStation, int infusedAmount)
        implements RecipeDisplay {
    public static MapCodec<InfusionRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SlotDisplay.CODEC.fieldOf("ingredient").forGetter(InfusionRecipeDisplay::ingredient),
                    SlotDisplay.CODEC.fieldOf("result").forGetter(InfusionRecipeDisplay::result),
                    SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(InfusionRecipeDisplay::craftingStation),
                    Codec.INT.fieldOf("infused_amount").forGetter(InfusionRecipeDisplay::infusedAmount)
            ).apply(instance, InfusionRecipeDisplay::new)
    );
    public static PacketCodec<RegistryByteBuf, InfusionRecipeDisplay> PACKET_CODEC = PacketCodec.tuple(
            SlotDisplay.PACKET_CODEC,
            InfusionRecipeDisplay::ingredient,
            SlotDisplay.PACKET_CODEC,
            InfusionRecipeDisplay::result,
            SlotDisplay.PACKET_CODEC,
            InfusionRecipeDisplay::craftingStation,
            PacketCodecs.VAR_INT,
            InfusionRecipeDisplay::infusedAmount,
            InfusionRecipeDisplay::new
    );

    public static RecipeDisplay.Serializer<InfusionRecipeDisplay> SERIALIZER = new RecipeDisplay.Serializer<>(CODEC, PACKET_CODEC);

    public SlotDisplay craftingStation() {
        return craftingStation;
    }

    public int infusedAmount() {
        return infusedAmount;
    }

    public Serializer<InfusionRecipeDisplay> serializer() {
        return SERIALIZER;
    }
}
