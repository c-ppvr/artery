package net.ppvr.artery.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Style;
import net.ppvr.artery.items.ArteryArmorTrimMaterials;
import net.ppvr.artery.items.ArteryItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorTrimMaterials.class)
public class ArmorTrimMaterialsMixin {
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void bootstrap(Registerable<ArmorTrimMaterial> registry, CallbackInfo ci) {
        register(registry, ArteryArmorTrimMaterials.THROMBIUM, ArteryItems.THROMBIUM_INGOT, Style.EMPTY.withColor(0xF7F7C8));
    }

    @Shadow
    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Item ingredient, Style style) {
    }
}
