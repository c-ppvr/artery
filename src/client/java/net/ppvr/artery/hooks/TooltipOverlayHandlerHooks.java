package net.ppvr.artery.hooks;
public interface TooltipOverlayHandlerHooks {
     interface FoodOverlay {
        default String artery$getSanguinityText() {
            throw new RuntimeException();
        }
    }
}
