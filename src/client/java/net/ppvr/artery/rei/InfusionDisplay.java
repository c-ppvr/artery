package net.ppvr.artery.rei;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.util.Identifier;
import net.ppvr.artery.recipe.InfusionRecipeDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class InfusionDisplay extends BasicDisplay {
    public static final DisplaySerializer<InfusionDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(InfusionDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(InfusionDisplay::getOutputEntries),
                    Identifier.CODEC.optionalFieldOf("location").forGetter(InfusionDisplay::getDisplayLocation),
                    Codec.INT.fieldOf("infusedAmount").forGetter(display -> display.infusedAmount)
            ).apply(instance, InfusionDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    InfusionDisplay::getInputEntries,
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    InfusionDisplay::getOutputEntries,
                    PacketCodecs.optional(Identifier.PACKET_CODEC),
                    InfusionDisplay::getDisplayLocation,
                    PacketCodecs.INTEGER,
                    display -> display.infusedAmount,
                    InfusionDisplay::new
            ));

    private final int infusedAmount;

    public InfusionDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, int infusedAmount) {
        super(inputs, outputs);
        this.infusedAmount = infusedAmount;
    }

    public InfusionDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location, int infusedAmount) {
        super(inputs, outputs, location);
        this.infusedAmount = infusedAmount;
    }

    public InfusionDisplay(InfusionRecipeDisplay recipe, Optional<NetworkRecipeId> id) {
        this(
                List.of(EntryIngredients.ofSlotDisplay(recipe.ingredient())),
                List.of(EntryIngredients.ofSlotDisplay(recipe.result())),
                recipe.infusedAmount()
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ArteryReiPluginClient.INFUSION;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
