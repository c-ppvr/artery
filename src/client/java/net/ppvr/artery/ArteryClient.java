package net.ppvr.artery;

import net.fabricmc.api.ClientModInitializer;
import net.ppvr.artery.gui.screen.ArteryScreens;

public class ArteryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArteryScreens.initialize();
    }
}