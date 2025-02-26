package net.ppvr.artery.mixin;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ToolMaterial;
import net.ppvr.artery.ArteryEntityAttributes;
import net.ppvr.artery.items.ArteryItems;
import net.ppvr.artery.items.ArteryToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ToolMaterial.class)
public abstract class ToolMaterialMixin {
    @Shadow public abstract int hashCode();

    @Redirect(method = "createToolAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/AttributeModifiersComponent$Builder;build()Lnet/minecraft/component/type/AttributeModifiersComponent;"))
    public AttributeModifiersComponent createToolAttributeModifiers(AttributeModifiersComponent.Builder builder){
        return addTransfusionRateModifier(builder).build();
    }

    @Redirect(method = "createSwordAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/AttributeModifiersComponent$Builder;build()Lnet/minecraft/component/type/AttributeModifiersComponent;"))
    public AttributeModifiersComponent createSwordAttributeModifiers(AttributeModifiersComponent.Builder builder){
        return addTransfusionRateModifier(builder).build();
    }


    @Unique
    private AttributeModifiersComponent.Builder addTransfusionRateModifier(AttributeModifiersComponent.Builder builder) {
        double amount = ArteryToolMaterial.TRANSFUSION_RATE_MAP.getOrDefault(this, 0.0);
        if (amount != 0) {
            builder.add(
                    ArteryEntityAttributes.TRANSFUSION_RATE,
                    new EntityAttributeModifier(
                            ArteryItems.BASE_TRANSFUSION_RATE_MODIFIER_ID,
                            amount,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                    AttributeModifierSlot.MAINHAND);
        }
        return builder;
    }
}
