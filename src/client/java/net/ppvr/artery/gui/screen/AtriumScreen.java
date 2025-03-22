package net.ppvr.artery.gui.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ppvr.artery.gui.widget.IntFieldWidget;
import net.ppvr.artery.network.TransferSanguinityC2SPayload;
import net.ppvr.artery.screen.AtriumScreenHandler;

import static net.ppvr.artery.Artery.MOD_ID;

public class AtriumScreen extends HandledScreen<AtriumScreenHandler> {
    public static final Identifier ATRIUM_CONTAINER = Identifier.of(MOD_ID, "textures/gui/atrium.png");

    private IntFieldWidget amountField;
    private ConfirmButtonWidget confirmButton;
    private ButtonWidget maxButton;

    public AtriumScreen(AtriumScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.backgroundHeight = 175;
        this.amountField = new IntFieldWidget(textRenderer, x + 64, y + 23, 48, 16, 0, 800);
        this.amountField.setEditable(true);
        this.addSelectableChild(amountField);
        this.confirmButton = new ConfirmButtonWidget(x + 77, y + 69);
        this.addSelectableChild(confirmButton);
        this.maxButton = ButtonWidget.builder(
                        Text.translatable("container.artery.atrium.max_button"),
                        button -> amountField.setInt(handler.getPlayerSanguinity()))
                .dimensions(x + 30, y + 72, 40, 16)
                .build();
        this.addSelectableChild(maxButton);
        this.setFocused(amountField);
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.amountField.setMaxInt(handler.getCapacity());
        this.confirmButton.active = !amountField.getText().equals("0") && (handler.getPlayerSanguinity() >= amountField.getInt()) || client.player.isCreative();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        confirmButton.render(context, mouseX, mouseY, delta);
        amountField.render(context, mouseX, mouseY, delta);
        maxButton.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, title, titleX, titleY, 0xFF000000, false);
        int width = context.getScaledWindowWidth();
        int sanguinity = handler.getPlayerSanguinity();
        int stored = handler.getSanguinity();
        int left, right, color;
        if (confirmButton.isHovered()) {
            color = confirmButton.active ? 0xFF0000C0 : 0xFFFF0000;
            int d = amountField.getInt();
            left = client.player.isCreative() ? sanguinity : sanguinity - d;
            right = stored + d;
        } else {
            color = 0xFF000000;
            left = sanguinity;
            right = stored;
        }
        context.drawText(textRenderer, Text.of("" + left), width / 2 - 20 - textRenderer.getWidth("" + left) - x, 49, color, false);
        context.drawText(textRenderer, Text.of("%d/%d".formatted(right, handler.getCapacity())), width / 2 + 20 - x, 49, color, false);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, ATRIUM_CONTAINER, x, y, 0, 0, 176, 135, 256, 256);
    }

    class ConfirmButtonWidget extends PressableWidget {
        static final Identifier BUTTON_DISABLED_TEXTURE = Identifier.ofVanilla("container/beacon/button_disabled");
        static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("container/beacon/button_highlighted");
        static final Identifier BUTTON_TEXTURE = Identifier.ofVanilla("container/beacon/button");
        static final Identifier CONFIRM_TEXTURE = Identifier.ofVanilla("container/beacon/confirm");

        public ConfirmButtonWidget(int x, int y) {
            super(x, y, 22, 22, ScreenTexts.EMPTY);
        }

        @Override
        public void onPress() {
            if (active) {
                ClientPlayNetworking.send(new TransferSanguinityC2SPayload(amountField.getInt()));
                amountField.setInt(0);
            }
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            Identifier texture;
            if (!active) {
                texture = BUTTON_DISABLED_TEXTURE;
            } else if (isSelected()) {
                texture = BUTTON_HIGHLIGHTED_TEXTURE;
            } else {
                texture = BUTTON_TEXTURE;
            }
            context.drawGuiTexture(RenderLayer::getGuiTextured, texture, getX(), getY(), width, height);
            context.drawGuiTexture(RenderLayer::getGuiTextured, CONFIRM_TEXTURE, getX() + 2, getY() + 2, 18, 18);
        }
    }
}
