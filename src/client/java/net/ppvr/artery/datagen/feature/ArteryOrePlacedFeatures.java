package net.ppvr.artery.datagen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryOrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> ORE_ERYTHIRTE = keyOf("ore_erythrite");
    public static final RegistryKey<PlacedFeature> ORE_LEUKIUM = keyOf("ore_leukium");
    public static final RegistryKey<PlacedFeature> ORE_THROMBIUM = keyOf("ore_thrombium");

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> erythriteEntry = registryEntryLookup.getOrThrow(ArteryOreConfiguredFeatures.ORE_ERYTHIRTE);
        RegistryEntry<ConfiguredFeature<?, ?>> leukiumEntry = registryEntryLookup.getOrThrow(ArteryOreConfiguredFeatures.ORE_LEUKIUM);
        RegistryEntry<ConfiguredFeature<?, ?>> thrombiumEntry = registryEntryLookup.getOrThrow(ArteryOreConfiguredFeatures.ORE_THROMBIUM);

        PlacedFeatures.register(
                featureRegisterable, ORE_ERYTHIRTE, erythriteEntry, modifiersWithCount(12, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(64)))
        );
        PlacedFeatures.register(
                featureRegisterable, ORE_LEUKIUM, leukiumEntry, modifiersWithCount(6, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-112), YOffset.fixed(16)))
        );
        PlacedFeatures.register(
                featureRegisterable, ORE_THROMBIUM, thrombiumEntry, modifiersWithCount(8, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(96)))
        );
    }

    public static RegistryKey<PlacedFeature> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, id));
    }
}
