package net.ppvr.artery.mixin;



import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.ppvr.artery.hooks.ItemStackHooks;
import net.ppvr.artery.items.ArteryDataComponentTypes;
import net.ppvr.artery.items.ArteryItems;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, ItemStackHooks {
    @Unique
    private boolean shouldFlipColor;

    @Shadow
    public abstract <T> T set(ComponentType<? super T> type, @Nullable T value);

    @Inject(method = "appendAttributeModifierTooltip", at = @At("HEAD"))
    public void appendAttributeModifierTooltip(Consumer<Text> textConsumer, @Nullable PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, CallbackInfo ci) {
        String id = modifier.id().toString();
        shouldFlipColor = id.endsWith("coagulation_rate") || modifier.idMatches(ArteryItems.BASE_TRANSFUSION_RATE_MODIFIER_ID);
    }

    @ModifyArg(method = "appendAttributeModifierTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttribute;getFormatting(Z)Lnet/minecraft/util/Formatting;"))
    public boolean appendAttributeModifierTooltip(boolean addition) {
        return addition != shouldFlipColor;
    }

    public int artery$getMaxPressure() {
        return getOrDefault(ArteryDataComponentTypes.MAX_PRESSURE, 0);
    }

    public int artery$getPressure() {
        return MathHelper.clamp(getOrDefault(ArteryDataComponentTypes.PRESSURE, 0), 0, artery$getMaxPressure());
    }

    public void artery$setPressure(int amount) {
        set(DataComponentTypes.DAMAGE, MathHelper.clamp(amount, 0, artery$getMaxPressure()));
    }

    public void artery$addPressure(int amount) {
        artery$setPressure(artery$getPressure() + amount);
    }
}
