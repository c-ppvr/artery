package net.ppvr.artery.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class PressorFuelSlot extends Slot {
    private final PressorScreenHandler handler;

    public PressorFuelSlot(PressorScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }
    @Override
    public boolean canInsert(ItemStack stack) {
        return this.handler.isFuel(stack) || isBucket(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.isOf(Items.BUCKET);
    }
}