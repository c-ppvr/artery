package net.ppvr.artery.hooks;

public interface PlayerEntityHooks {
    default float artery$getMaxSanguinity() { throw new RuntimeException(); }
    default float artery$getTransfusionRate() { throw new RuntimeException(); }
    default float artery$getCoagulationRate() { throw new RuntimeException(); }

    default float artery$getSanguinity() { throw new RuntimeException(); }
    default void artery$setSanguinity(float amount) { throw new RuntimeException(); }
    default void artery$addSanguinity(float amount) { throw new RuntimeException(); }

    default float artery$getUnconvertedSanguinity() { throw new RuntimeException(); }
    default void artery$setUnconvertedSanguinity(float amount) { throw new RuntimeException(); }
    default void artery$addUnconvertedSanguinity(float amount) { throw new RuntimeException(); }
}
