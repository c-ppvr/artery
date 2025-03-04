package net.ppvr.artery.blocks;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryBlocks {
    public static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();

    public static void initialize() {
    }

    public static final Block ATRIUM = Builder.create("atrium")
            .factory(AtriumBlock::new)
            .configure(settings ->
                    settings.requiresTool()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.SCULK)
                            .luminance(OrganBlock.getLuminanceSupplier(3))
            )
            .shouldRegisterItem()
            .register();

    public static final Block VENTRICLE = Builder.create("ventricle")
            .factory(VentricleBlock::new)
            .configure(settings ->
                    settings.requiresTool()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.SCULK)
                            .luminance(OrganBlock.getLuminanceSupplier(3))
            )
            .shouldRegisterItem()
            .register();

    public static final Block FIBROBLASTER = Builder.create("fibroblaster")
            .factory(FibroblasterBlock::new)
            .configure(settings ->
                    settings.requiresTool()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.SCULK)
                            .luminance(OrganBlock.getLuminanceSupplier(3))
            )
            .shouldRegisterItem()
            .register();

    public static final Block ERYTHRITE_ORE = Builder.create("erythrite_ore")
            .factory(settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings))
            .settings(AbstractBlock.Settings.copy(Blocks.IRON_ORE))
            .setBlockKey()
            .shouldRegisterItem()
            .register();

    public static final Block DEEPSLATE_ERYTHRITE_ORE = Builder.create("deepslate_erythrite_ore")
            .factory(settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings))
            .settings(AbstractBlock.Settings.copy(ERYTHRITE_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            .setBlockKey()
            .shouldRegisterItem()
            .register();

    public static final Block ERYTHRITE_BLOCK = Builder.create("erythrite_block")
            .factory(Block::new)
            .configure(settings ->
                    settings.mapColor(MapColor.RED)
                            .requiresTool()
                            .strength(3.0f, 6.0f)
                            .sounds(BlockSoundGroup.SCULK)
            )
            .shouldRegisterItem()
            .register();

    public static final Block LEUKIUM_ORE = Builder.create("leukium_ore")
            .factory(settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings))
            .settings(AbstractBlock.Settings.copy(Blocks.IRON_ORE))
            .setBlockKey()
            .shouldRegisterItem()
            .register();

    public static final Block DEEPSLATE_LEUKIUM_ORE = Builder.create("deepslate_leukium_ore")
            .factory(settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings))
            .settings(AbstractBlock.Settings.copy(LEUKIUM_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            .setBlockKey()
            .shouldRegisterItem()
            .register();

    public static final Block RAW_LEUKIUM_BLOCK = Builder.create("raw_leukium_block")
            .factory(Block::new)
            .configure(settings ->
                    settings.mapColor(MapColor.CYAN)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresTool()
                            .strength(5.0f, 6.0f))
            .shouldRegisterItem()
            .register();

    public static final Block LEUKIUM_BLOCK = Builder.create("leukium_block")
            .factory(Block::new)
            .configure(settings ->
                    settings.mapColor(MapColor.CYAN)
                            .requiresTool()
                            .strength(3.0f, 6.0f)
                            .sounds(BlockSoundGroup.METAL)
            )
            .shouldRegisterItem()
            .register();

    public static final Block THROMBIUM_ORE = Builder.create("thrombium_ore")
            .factory(settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings))
            .settings(AbstractBlock.Settings.copy(Blocks.IRON_ORE))
            .setBlockKey()
            .shouldRegisterItem()
            .register();

    public static final Block DEEPSLATE_THROMBIUM_ORE = Builder.create("deepslate_thrombium_ore")
            .factory(settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings))
            .settings(AbstractBlock.Settings.copy(THROMBIUM_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            .setBlockKey()
            .shouldRegisterItem()
            .register();

    public static final Block RAW_THROMBIUM_BLOCK = Builder.create("raw_thrombium_block")
            .factory(Block::new)
            .configure(settings ->
                    settings.mapColor(MapColor.CYAN)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresTool()
                            .strength(5.0f, 6.0f))
            .shouldRegisterItem()
            .register();

    public static final Block THROMBIUM_BLOCK = Builder.create("thrombium_block")
            .factory(Block::new)
            .configure(settings ->
                    settings.mapColor(MapColor.CYAN)
                            .requiresTool()
                            .strength(5.0f, 6.0f)
                            .sounds(BlockSoundGroup.METAL)
            )
            .shouldRegisterItem()
            .register();

    /**
     * A builder to make block registering easier.
     */
    static class Builder {
        private final String id;
        private Function<AbstractBlock.Settings, Block> factory = Block::new;
        private AbstractBlock.Settings settings = AbstractBlock.Settings.create();
        private final RegistryKey<Block> blockKey;
        private boolean shouldRegisterItem;

        /**
         * Creates a new builder. An ID is required.
         *
         * @param id The ID of the block. The registry key of the {@link Block} and {@link BlockItem} (if registered) defaults to using this.
         * @return A {@link Builder}.
         */
        public static Builder create(String id) {
            return new Builder(id);
        }

        /**
         * Just a constructor. Initializes the block ID.
         *
         * @param id The ID of the block.
         */
        private Builder(String id) {
            this.id = id;
            this.blockKey = RegistryKey.of(Registries.BLOCK.getKey(), Identifier.of(MOD_ID, id));
            this.settings.registryKey(blockKey);
        }

        /**
         * Sets the factory for the {@link Block}.
         *
         * @param factory Factory for the {@link Block}, defaults to {@link Block#Block(AbstractBlock.Settings) Block::new}.
         * @return Itself.
         */
        public Builder factory(Function<AbstractBlock.Settings, Block> factory) {
            this.factory = factory;
            return this;
        }

        /**
         * Sets the settings for the {@link Block}.
         *
         * @param settings Settings for the block, defaults to using {@link AbstractBlock.Settings#create()}.
         * @return Itself.
         */
        public Builder settings(AbstractBlock.Settings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * Set the block registry key in the settings. This is done automatically during builder initialization, but you might need to call this if you overwrote the settings with {@link #settings(AbstractBlock.Settings)}.
         *
         * @return Itself.
         */
        public Builder setBlockKey() {
            this.settings.registryKey(blockKey);
            return this;
        }

        /**
         * Configures the {@linkplain AbstractBlock.Settings block settings}.
         *
         * @param config A function that takes an {@link AbstractBlock.Settings} as a parameter that can be used as a builder.
         * @return Itself.
         */
        public Builder configure(ConfigureSettings config) {
            config.configure(this.settings);
            return this;
        }

        /**
         * {@link #register()} will register a corresponding {@link BlockItem} if this is called. The builder defaults to not registering one, but you would almost always want one.
         *
         * @return Itself.
         */
        public Builder shouldRegisterItem() {
            this.shouldRegisterItem = true;
            return this;
        }

        /**
         * Registers the {@link Block} and the {@link BlockItem} (if {@link #shouldRegisterItem} is called).
         *
         * @return The {@link Block} registered.
         */
        public Block register() {
            if (settings == null) settings = AbstractBlock.Settings.create();
            Block block = factory.apply(settings);
            if (shouldRegisterItem) {
                RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, id));
                BlockItem item = Registry.register(Registries.ITEM, itemKey, new BlockItem(block, new Item.Settings().registryKey(itemKey)));
                ArteryBlocks.BLOCK_ITEMS.add(item);
            }
            return Registry.register(Registries.BLOCK, blockKey, block);
        }

        @FunctionalInterface
        interface ConfigureSettings {
            void configure(AbstractBlock.Settings settings);
        }
    }
}