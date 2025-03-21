package net.ppvr.artery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SingleStackRecipe;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ppvr.artery.blocks.ArteryBlocks;

import java.util.List;

import static net.ppvr.artery.Artery.MOD_ID;

public class InfusionRecipe extends SingleStackRecipe {
    private static final Identifier ID = Identifier.of(MOD_ID, "infusion");
    public static final int DEFAULT_INFUSED_AMOUNT = 25;
    public static final RecipeType<InfusionRecipe> TYPE = Registry.register(Registries.RECIPE_TYPE, ID,
            new RecipeType<InfusionRecipe>() {
                @Override
                public String toString() {
                    return ID.toString();
                }
            });
    public static final RecipeSerializer<InfusionRecipe> SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, ID, new Serializer<>(InfusionRecipe::new, DEFAULT_INFUSED_AMOUNT));
    public static final RecipeBookCategory BOOK_CATEGORY = Registry.register(Registries.RECIPE_BOOK_CATEGORY, ID, new RecipeBookCategory());
    private final int infusedAmount;

    public static void initialize() {
        Registry.register(Registries.RECIPE_DISPLAY, ID, InfusionRecipeDisplay.SERIALIZER);
    }
    public InfusionRecipe(String group, Ingredient ingredient, ItemStack result, int infusedAmount) {
        super(group, ingredient, result);
        this.infusedAmount = infusedAmount;
    }

    @FunctionalInterface
    public interface RecipeFactory<T extends InfusionRecipe> {
        T create(String group, Ingredient ingredient, ItemStack result, int infusedAmount);
    }

    public static class Serializer<T extends InfusionRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final PacketCodec<RegistryByteBuf, T> packetCodec;

        protected Serializer(InfusionRecipe.RecipeFactory<T> factory, int defaultInfusedAmount) {
            this.codec = RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                            Codec.STRING.optionalFieldOf("group", "").forGetter(InfusionRecipe::getGroup),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(InfusionRecipe::ingredient),
                            ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(InfusionRecipe::result),
                            Codec.INT.fieldOf("infused_amount").orElse(defaultInfusedAmount).forGetter(InfusionRecipe::getInfusedAmount)
                    ).apply(instance, factory::create)
            );

            this.packetCodec = PacketCodec.tuple(
                    PacketCodecs.STRING,
                    InfusionRecipe::getGroup,
                    Ingredient.PACKET_CODEC,
                    InfusionRecipe::ingredient,
                    ItemStack.PACKET_CODEC,
                    InfusionRecipe::result,
                    PacketCodecs.INTEGER,
                    InfusionRecipe::getInfusedAmount,
                    factory::create
            );
        }


        @Override
        public MapCodec<T> codec() {
            return codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, T> packetCodec() {
            return packetCodec;
        }
    }

    public int getInfusedAmount() {
        return infusedAmount;
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        return List.of(
                new InfusionRecipeDisplay(
                        ingredient().toDisplay(),
                        new SlotDisplay.StackSlotDisplay(result()),
                        new SlotDisplay.ItemSlotDisplay(ArteryBlocks.VENTRICLE.asItem()),
                        infusedAmount
                )
        );
    }

    @Override
    public RecipeSerializer<? extends SingleStackRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends SingleStackRecipe> getType() {
        return TYPE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return BOOK_CATEGORY;
    }
}
