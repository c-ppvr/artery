package net.ppvr.artery.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

import java.util.HashMap;
import java.util.Map;

public class ArteryToolMaterial {
    public static final ToolMaterial LEUKIUM = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 371, 6.0f, 2.0F, 16, ArteryItemTags.LEUKIUM_TOOL_MATERIALS);

    public static final Map<Object, Double> TRANSFUSION_RATE_MAP = new HashMap<>();

    static {
        TRANSFUSION_RATE_MAP.put(LEUKIUM, -1.5);
    }
}
