package net.ppvr.artery.mixin;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.ppvr.artery.hooks.ItemHooks;
import net.ppvr.artery.items.ArteryDataComponentTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Mixin(Item.Settings.class)
    public static abstract class Settings implements ItemHooks.Settings {
        @Shadow
        public abstract <T> Item.Settings component(ComponentType<T> type, T value);

        public Item.Settings artery$maxPressure(int maxPressure) {
            component(ArteryDataComponentTypes.MAX_PRESSURE, maxPressure);
            component(DataComponentTypes.MAX_STACK_SIZE, 1);
            component(ArteryDataComponentTypes.PRESSURE, 0);
            return (Item.Settings) (Object) this;
        }
    }
}
