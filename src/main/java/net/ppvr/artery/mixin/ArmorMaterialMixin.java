package net.ppvr.artery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.ppvr.artery.ArteryEntityAttributes;
import net.ppvr.artery.items.ArteryArmorMaterial;
import net.ppvr.artery.items.ArteryItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArmorMaterial.class)
public class ArmorMaterialMixin {
    @Redirect(method = "createAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/AttributeModifiersComponent$Builder;build()Lnet/minecraft/component/type/AttributeModifiersComponent;"))
    public AttributeModifiersComponent createAttributeModifiers(AttributeModifiersComponent.Builder builder, @Local(argsOnly = true) EquipmentType type) {
        double amount = ArteryArmorMaterial.COAGULATION_RATE_MAP.getOrDefault(this, 0.0);
        if (amount != 0) {
            builder.add(
                    ArteryEntityAttributes.COAGULATION_RATE,
                    new EntityAttributeModifier(
                            ArteryItems.BASE_COAGULATION_RATE_MODIFIER_ID,
                            amount,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                    AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot())
            );
        }
        return builder.build();
    }
}
