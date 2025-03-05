package net.ppvr.artery.screen;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryScreenHandlerTypes {
    public static void initialize() {
    }

    public static final ScreenHandlerType<AtriumScreenHandler> ATRIUM_SCREEN_HANDLER = register("atrium", ((syncId, playerInventory) -> new AtriumScreenHandler(syncId, playerInventory.player, new ArrayPropertyDelegate(2), ScreenHandlerContext.EMPTY)));
    public static final ScreenHandlerType<VentricleScreenHandler> VENTRICLE_SCREEN_HANDLER = register("ventricle", (syncId, playerInventory) -> new VentricleScreenHandler(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(4)));
    public static final ScreenHandlerType<PressorScreenHandler> PRESSOR_SCREEN_HANDLER = register("pressor", (syncId, playerInventory) -> new PressorScreenHandler(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(4)));

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MOD_ID, id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
}
