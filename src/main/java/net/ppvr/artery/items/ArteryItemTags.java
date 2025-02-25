package net.ppvr.artery.items;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryItemTags {
    public static final TagKey<Item> LEUKIUM_TOOL_MATERIALS = of("leukium_tool_materials");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, id));
    }
}
