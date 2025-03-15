package net.ppvr.artery.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PressorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final World world;

    public PressorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ArteryScreenHandlerTypes.PRESSOR_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.getWorld();
        addSlot(new Slot(inventory, 0, 80, 17));
        addSlot(new PressorFuelSlot(this, inventory, 1, 80, 53));
        addPlayerSlots(playerInventory, 8, 84);
        addProperties(propertyDelegate);
    }

    public boolean isFuel(ItemStack item) {
        return world.getFuelRegistry().isFuel(item);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot originSlot = slots.get(slot);
        if (originSlot.hasStack()) {
            ItemStack movedStack = originSlot.getStack();
            originalStack = movedStack.copy();
            if (slot == 0 && !insertItem(movedStack, 2, 38, true)) {
                return ItemStack.EMPTY;
            } else if (slot == 1 && !insertItem(movedStack, 2, 38, false)) {
                return ItemStack.EMPTY;
            } else {
                if (isPressurizable(movedStack) && !insertItem(movedStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (isFuel(movedStack) && !insertItem(movedStack, 1, 2, false)) {
                    return ItemStack.EMPTY;
                } else if (slot >= 2 && slot < 29 && !insertItem(movedStack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                } else if (slot >= 29 && slot < 38 && !insertItem(movedStack, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (movedStack.isEmpty()) {
                originSlot.setStack(ItemStack.EMPTY);
            } else {
                originSlot.markDirty();
            }
            if (movedStack.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            originSlot.onTakeItem(player, movedStack);
        }
        return originalStack;
    }

    private boolean isPressurizable(ItemStack item) {
        return item.artery$getMaxPressure() > 0;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    public boolean isBurning() {
        return propertyDelegate.get(2) > 0;
    }

    public float getFuelProgress() {
        int totalTime = this.propertyDelegate.get(3);
        if (totalTime == 0) {
            totalTime = 200;
        }

        return MathHelper.clamp((float) this.propertyDelegate.get(2) / totalTime, 0.0f, 1.0f);
    }

    public int getSanguinity() {
        return propertyDelegate.get(0);
    }

    public int getCapacity() {
        return propertyDelegate.get(1);
    }
}
