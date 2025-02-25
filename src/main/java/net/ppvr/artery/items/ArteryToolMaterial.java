package net.ppvr.artery.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryToolMaterial {
    public static final Identifier BASE_TRANSFUSION_RATE_MODIFIER_ID = Identifier.of(MOD_ID, "base_transfusion_rate");

    public static final ToolMaterial LEUKIUM = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 371, 6.0f, 2.0F, 16, ArteryItemTags.LEUKIUM_TOOL_MATERIALS);

    public static final Map<Object, Double> TRANSFUSION_RATE_MAP;

    static {
        TRANSFUSION_RATE_MAP = new HashMap<>();
        TRANSFUSION_RATE_MAP.put(LEUKIUM, -1.5);
    }
}
