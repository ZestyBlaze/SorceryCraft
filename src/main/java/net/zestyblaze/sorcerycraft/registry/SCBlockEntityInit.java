package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.block.entity.HallowedIronBlockEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class SCBlockEntityInit {
    private static final Map<BlockEntityType<?>, ResourceLocation> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    public static final BlockEntityType<HallowedIronBlockEntity> HALLOWED_IRON = create("hallowed_iron", FabricBlockEntityTypeBuilder.create(HallowedIronBlockEntity::new, SCBlockInit.HALLOWED_IRON).build(null));

    private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new ResourceLocation(SorceryCraft.MODID, name));
        return type;
    }

    public static void registerBlockEntities() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
