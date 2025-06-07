package net.ppvr.artery.mixin.client;

import net.minecraft.client.data.ItemModelGenerator;
import net.ppvr.artery.datagen.ArteryModelProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Mixin(ItemModelGenerator.class)
public class ItemModelGeneratorMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/List;of([Ljava/lang/Object;)Ljava/util/List;"))
    private static <E> List<E> addExtraTrimMaterials(E[] list) {
        return (List<E>) Stream.concat(Arrays.stream(list), ArteryModelProvider.EXTRA_TRIM_MATERIALS.stream()).toList();
    }
}
