package net.ppvr.artery.hooks;

public interface ItemStackHooks {
    default int artery$getMaxPressure() { throw new RuntimeException(); }

    default int artery$getPressure() { throw new RuntimeException(); }
    default void artery$setPressure(int amount) { throw new RuntimeException(); }
    default void artery$addPressure(int amount) { throw new RuntimeException(); }
}
