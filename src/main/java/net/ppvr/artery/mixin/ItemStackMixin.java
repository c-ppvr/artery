package net.ppvr.artery.mixin;



import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.ppvr.artery.items.ArteryToolMaterial;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    private boolean shouldFlipColor;

    @Inject(method = "appendAttributeModifierTooltip", at = @At("HEAD"))
    public void appendAttributeModifierTooltip(Consumer<Text> textConsumer, @Nullable PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, CallbackInfo ci) {
        shouldFlipColor = modifier.idMatches(ArteryToolMaterial.BASE_TRANSFUSION_RATE_MODIFIER_ID);
    }

    @ModifyArg(method = "appendAttributeModifierTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttribute;getFormatting(Z)Lnet/minecraft/util/Formatting;"))
    public boolean appendAttributeModifierTooltip(boolean addition) {
        return addition != shouldFlipColor;
    }
}
