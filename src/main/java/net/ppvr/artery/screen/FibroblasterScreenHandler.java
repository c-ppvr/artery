package net.ppvr.artery.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FibroblasterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public FibroblasterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ArteryScreenHandlerTypes.FIBROBLASTER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        addSlot(new Slot(inventory, 0, 80, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isDamageable();
            }
        });
        addProperties(propertyDelegate);
        addPlayerSlots(playerInventory, 8, 84);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot originSlot = slots.get(slot);
        if (originSlot.hasStack()) {
            ItemStack movingStack = originSlot.getStack();
            originalStack = movingStack.copy();
            if (slot == 0) {
                if (!insertItem(movingStack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!insertItem(movingStack, 0, 1, false)) {
                if (slot >= 1 && slot < 28) {
                    if (!insertItem(movingStack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= 28 && slot < 37) {
                    if (!insertItem(movingStack, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (movingStack.isEmpty()) {
                originSlot.setStack(ItemStack.EMPTY);
            } else {
                originSlot.markDirty();
            }

            if (movingStack.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            originSlot.onTakeItem(player, movingStack);
        }

        return originalStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    public int getSanguinity() {
        return propertyDelegate.get(0);
    }

    public int getCapacity() {
        return propertyDelegate.get(1);
    }
}
