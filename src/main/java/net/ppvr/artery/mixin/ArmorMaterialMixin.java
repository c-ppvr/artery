package net.ppvr.artery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.util.Identifier;
import net.ppvr.artery.ArteryEntityAttributes;
import net.ppvr.artery.items.ArteryArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.ppvr.artery.Artery.MOD_ID;

@Mixin(ArmorMaterial.class)
public class ArmorMaterialMixin {
    @Redirect(method = "createAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/AttributeModifiersComponent$Builder;build()Lnet/minecraft/component/type/AttributeModifiersComponent;"))
    public AttributeModifiersComponent createAttributeModifiers(AttributeModifiersComponent.Builder builder, @Local(argsOnly = true) EquipmentType type) {
        double coagulationRate = ArteryArmorMaterial.COAGULATION_RATE_MAP.getOrDefault(this, 0.0);
        double maxSanguinity = ArteryArmorMaterial.MAX_SANGUINITY_MAP.getOrDefault(this, 0.0);
        if (coagulationRate != 0) {
            builder.add(
                    ArteryEntityAttributes.COAGULATION_RATE,
                    new EntityAttributeModifier(
                            Identifier.of(MOD_ID, "armor." + type.getName() + ".coagulation_rate"),
                            coagulationRate,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                    AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot())
            );
        }
        if (maxSanguinity != 0) {
            builder.add(
                    ArteryEntityAttributes.MAX_SANGUINITY,
                    new EntityAttributeModifier(
                            Identifier.of(MOD_ID, "armor." + type.getName() + ".max_sanguinity"),
                            maxSanguinity,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                    AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot())
            );
        }
        return builder.build();
    }
}
