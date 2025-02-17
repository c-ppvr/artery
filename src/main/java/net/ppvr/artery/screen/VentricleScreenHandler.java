package net.ppvr.artery.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.InputSlotFiller;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.ppvr.artery.recipe.InfusionRecipe;

import java.util.List;

public class VentricleScreenHandler extends AbstractRecipeScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public VentricleScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ArteryScreenHandlerTypes.VENTRICLE_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        addSlot(new Slot(inventory, 0, 79, 17));
        addSlot(new Slot(inventory, 1, 79, 58) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        addPlayerSlots(playerInventory, 8, 84);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot originSlot = slots.get(slot);
        if (originSlot.hasStack()) {
            ItemStack movingStack = originSlot.getStack();
            originalStack = movingStack.copy();
            if (slot == 1) {
                if (!insertItem(movingStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                originSlot.onQuickTransfer(movingStack, originalStack);
            } else if (slot != 0) {
                if (!insertItem(movingStack, 0, 1, false)) {
                    if (slot >= 2 && slot < 29) {
                        if (!insertItem(movingStack, 29, 38, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slot >= 29 && slot < 38) {
                        if (!insertItem(movingStack, 2, 29, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!insertItem(movingStack, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (movingStack.isEmpty()) {
                originSlot.setStack(ItemStack.EMPTY);
            } else {
                originSlot.markDirty();
            }

            if (movingStack.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            originSlot.onTakeItem(player, originalStack);
        }

        return originalStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public int getSanguinity() {
        return propertyDelegate.get(0);
    }

    public int getCapacity() {
        return propertyDelegate.get(1);
    }

    public int getInfusedAmountDone() {
        return propertyDelegate.get(2);
    }

    public int getInfusedTotalAmount() {
        return propertyDelegate.get(3);
    }

    @Override
    public PostFillAction fillInputSlots(boolean craftAll, boolean creative, RecipeEntry<?> recipe, ServerWorld world, PlayerInventory inventory) {
        return InputSlotFiller.fill(new InputSlotFiller.Handler<>() {
            @Override
            public void populateRecipeFinder(RecipeFinder finder) {
                VentricleScreenHandler.this.populateRecipeFinder(finder);
            }

            @Override
            public void clear() {
                getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
            }

            @Override
            public boolean matches(RecipeEntry<InfusionRecipe> entry) {
                return entry.value().matches(new SingleStackRecipeInput(VentricleScreenHandler.this.inventory.getStack(0)), world);
            }
        }, 1, 1, List.of(getSlot(0)), List.of(getSlot(0)), inventory, (RecipeEntry<InfusionRecipe>) recipe, craftAll, creative);
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (inventory instanceof RecipeInputProvider recipeInputProvider) {
            recipeInputProvider.provideRecipeInputs(finder);
        }
    }

    @Override
    public RecipeBookType getCategory() {
        return RecipeBookType.CRAFTING;
    }
}
