package net.ppvr.artery;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryEntityAttributes {
    public static final RegistryEntry<EntityAttribute> MAX_SANGUINITY = register(
            "max_sanguinity",
            (new ClampedEntityAttribute("artery.attribute.name.max_sanguinity", 80.0, 80.0, 800.0)).setTracked(true)
    );
    public static final RegistryEntry<EntityAttribute> TRANSFUSION_RATE = register(
            "transfusion_rate",
            (new ClampedEntityAttribute("artery.attribute.name.transfusion_rate", 4.0, 1.0, 8.0)).setTracked(true)
    );
    public static final RegistryEntry<EntityAttribute> COAGULATION_RATE = register(
            "coagulation_rate",
            (new ClampedEntityAttribute("artery.attribute.name.coagulation_rate", 4.0, 1.0, 8.0)).setTracked(true)
    );

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(MOD_ID, id), attribute);
    }
    public static void initialize() {
    }
}
