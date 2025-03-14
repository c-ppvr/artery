package net.ppvr.artery;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.ppvr.artery.blocks.ArteryBlocks;
import net.ppvr.artery.blocks.entity.ArteryBlockEntities;
import net.ppvr.artery.items.ArteryItems;
import net.ppvr.artery.network.TransferSanguinityC2SPayload;
import net.ppvr.artery.recipe.ArteryRecipes;
import net.ppvr.artery.screen.ArteryScreenHandlerTypes;
import net.ppvr.artery.screen.AtriumScreenHandler;
import net.ppvr.artery.sound.ArterySoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Artery implements ModInitializer {
    public static final String MOD_ID = "artery";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ArteryEntityAttributes.initialize();
        ArteryBlocks.initialize();
        ArteryItems.initialize();
        ArteryBlockEntities.initialize();
        ArteryScreenHandlerTypes.initialize();
        ArteryRecipes.initialize();
        ArterySoundEvents.initialize();

        PayloadTypeRegistry.playC2S().register(TransferSanguinityC2SPayload.ID, TransferSanguinityC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TransferSanguinityC2SPayload.ID, (payload, context) -> {
            if (context.player().currentScreenHandler instanceof AtriumScreenHandler screenHandler) {
                screenHandler.transferSanguinity(payload.amount());
            }
        });
    }
}