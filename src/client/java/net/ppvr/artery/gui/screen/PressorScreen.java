package net.ppvr.artery.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.ppvr.artery.screen.PressorScreenHandler;

public class PressorScreen extends HandledScreen<PressorScreenHandler> {
    public PressorScreen(PressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        renderInGameBackground(context);
    }
}
