package net.ppvr.artery.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ppvr.artery.blocks.ArteryBlocks;

import static net.ppvr.artery.Artery.MOD_ID;

public class ArteryBlockEntities {
    public static void initialize() {
    }

    public static final BlockEntityType<AtriumBlockEntity> ATRIUM_BLOCK_ENTITY = register("atrium", AtriumBlockEntity::new, ArteryBlocks.ATRIUM);
    public static final BlockEntityType<VentricleBlockEntity> VENTRICLE_BLOCK_ENTITY = register("ventricle", VentricleBlockEntity::new, ArteryBlocks.VENTRICLE);
    public static final BlockEntityType<PressorBlockEntity> PRESSOR_BLOCK_ENTITY = register("pressor", PressorBlockEntity::new, ArteryBlocks.PRESSOR);

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, id), FabricBlockEntityTypeBuilder.<T>create(factory, blocks).build());
    }
}
