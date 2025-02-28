package net.ppvr.artery.gui.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.ppvr.artery.screen.ArteryScreenHandlerTypes;

public class ArteryScreens {
    public static void initialize() {
        HandledScreens.register(ArteryScreenHandlerTypes.ATRIUM_SCREEN_HANDLER, AtriumScreen::new);
        HandledScreens.register(ArteryScreenHandlerTypes.VENTRICLE_SCREEN_HANDLER, VentricleScreen::new);
        HandledScreens.register(ArteryScreenHandlerTypes.FIBROBLASTER_SCREEN_HANDLER, FibroblasterScreen::new);
    }
}
