package net.ppvr.artery.hooks;

import net.minecraft.component.type.FoodComponent;

public interface FoodComponentHooks {
    default int artery$sanguinity() { throw new RuntimeException(); }
    default void artery$setSanguinity(int sanguinity) { throw new RuntimeException(); }

    interface Builder {
        default FoodComponent.Builder artery$sanguinity(int sanguinity) { throw new RuntimeException(); }
    }
}
