package net.ppvr.artery.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ppvr.artery.blocks.ArteryBlocks;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryItems {
    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ARTERY_ITEM_GROUP_KEY, ARTERY_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ARTERY_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item: ArteryBlocks.BLOCK_ITEMS) {
                itemGroup.add(item);
            }
        });
    }

    public static final RegistryKey<ItemGroup> ARTERY_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
    public static final ItemGroup ARTERY_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.EGG))
            .displayName(Text.translatable("itemGroup.artery.artery"))
            .build();
}
