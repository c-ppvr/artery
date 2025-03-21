package net.ppvr.artery.datagen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.ppvr.artery.blocks.ArteryBlocks;

import java.util.List;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryOreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_ERYTHIRTE = keyOf("ore_erythrite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_LEUKIUM = keyOf("ore_leukium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_THROMBIUM = keyOf("ore_thrombium");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        RuleTest stoneReplaceable = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        ConfiguredFeatures.register(featureRegisterable, ORE_ERYTHIRTE, Feature.ORE, new OreFeatureConfig(
                List.of(
                        OreFeatureConfig.createTarget(stoneReplaceable, ArteryBlocks.ERYTHRITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceable, ArteryBlocks.DEEPSLATE_ERYTHRITE_ORE.getDefaultState())
                ),
                9
        ));
        ConfiguredFeatures.register(featureRegisterable, ORE_LEUKIUM, Feature.ORE, new OreFeatureConfig(
                List.of(
                        OreFeatureConfig.createTarget(stoneReplaceable, ArteryBlocks.LEUKIUM_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceable, ArteryBlocks.DEEPSLATE_LEUKIUM_ORE.getDefaultState())
                ),
                7
        ));
        ConfiguredFeatures.register(featureRegisterable, ORE_THROMBIUM, Feature.ORE, new OreFeatureConfig(
                List.of(
                        OreFeatureConfig.createTarget(stoneReplaceable, ArteryBlocks.THROMBIUM_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceable, ArteryBlocks.DEEPSLATE_THROMBIUM_ORE.getDefaultState())
                ),
                10
        ));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID, id));
    }
}
