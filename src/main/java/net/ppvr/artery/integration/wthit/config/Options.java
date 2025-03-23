package net.ppvr.artery.integration.wthit.config;

import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class Options {
    public static final Identifier ATRIUM = id("atrium");
    
    private static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
