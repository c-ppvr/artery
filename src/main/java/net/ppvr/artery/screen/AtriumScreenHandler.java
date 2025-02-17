package net.ppvr.artery.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.world.World;
import net.ppvr.artery.blocks.ArteryBlocks;

public class AtriumScreenHandler extends ScreenHandler {
    private final PlayerEntity player;
    private final PropertyDelegate propertyDelegate;
    private final ScreenHandlerContext context;

    public AtriumScreenHandler(int syncId, PlayerEntity player, PropertyDelegate propertyDelegate, ScreenHandlerContext context) {
        super(ArteryScreenHandlerTypes.ATRIUM_SCREEN_HANDLER, syncId);
        this.player = player;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        this.context = context;
    }

    public void transferSanguinity(int amount) {
        amount = Math.min(amount, getCapacity());
        if (this.player.isCreative() || player.artery$getSanguinity() >= amount) {
            this.propertyDelegate.set(0, propertyDelegate.get(0) + amount);
            if (!player.isCreative()) {
                this.player.artery$addSanguinity(-amount);
            }
            this.context.run(World::markDirty);
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context, player, ArteryBlocks.ATRIUM);
    }

    public int getSanguinity() {
        return propertyDelegate.get(0);
    }

    public int getCapacity() {
        return propertyDelegate.get(1);
    }

    public int getPlayerSanguinity() {
        return (int) player.artery$getSanguinity();
    }
}


