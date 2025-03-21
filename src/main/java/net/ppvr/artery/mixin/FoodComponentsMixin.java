// I wrote a simple generator to generate the following code, and I don't think it's a good idea.
// However, I don't have any real solution and this works so there's that.

package net.ppvr.artery.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(FoodComponents.class)
public class FoodComponentsMixin {
    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;BEEF:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putBeef(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;CHICKEN:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putChicken(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COD:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCod(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;MUTTON:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putMutton(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;PORKCHOP:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putPorkchop(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;PUFFERFISH:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putPufferfish(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;RABBIT:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putRabbit(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;SALMON:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putSalmon(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;TROPICAL_FISH:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putTropicalFish(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(2).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_BEEF:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedBeef(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_CHICKEN:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedChicken(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_COD:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedCod(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_MUTTON:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedMutton(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_PORKCHOP:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedPorkchop(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_RABBIT:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedRabbit(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;COOKED_SALMON:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putCookedSalmon(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(1).build();
    }

    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/FoodComponent$Builder;build()Lnet/minecraft/component/type/FoodComponent;", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/component/type/FoodComponents;ROTTEN_FLESH:Lnet/minecraft/component/type/FoodComponent;", shift = At.Shift.BY, by = -2)))
    private static FoodComponent putRottenFlesh(FoodComponent.Builder instance) {
        return instance.artery$sanguinity(5).build();
    }
}
