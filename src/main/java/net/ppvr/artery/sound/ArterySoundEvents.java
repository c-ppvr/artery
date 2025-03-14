package net.ppvr.artery.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArterySoundEvents {
    public static final SoundEvent BLOCK_PRESSOR_FIRE_CRACKLE = register("block.pressor.fire_crackle");

    public static void initialize() {}

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.of(MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
