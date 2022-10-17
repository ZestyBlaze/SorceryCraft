package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.block.HallowedIronBlock;
import net.zestyblaze.sorcerycraft.block.ShieldBlock;

public class SCBlockInit {
    public static final Block CRYSTAL_ORE = new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE));
    public static final Block HALLOWED_IRON = new HallowedIronBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).luminance(6));
    public static final Block CRYSTALISED_REDSTONE_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).luminance(6));
    public static final Block DRAINED_CRYSTALISED_REDSTONE_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK));
    public static final Block SHIELD_BLOCK = new ShieldBlock();

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new ResourceLocation(SorceryCraft.MODID, "crystal_ore"), CRYSTAL_ORE);
        Registry.register(Registry.BLOCK, new ResourceLocation(SorceryCraft.MODID, "hallowed_iron"), HALLOWED_IRON);
        Registry.register(Registry.BLOCK, new ResourceLocation(SorceryCraft.MODID, "crystalised_redstone_block"), CRYSTALISED_REDSTONE_BLOCK);
        Registry.register(Registry.BLOCK, new ResourceLocation(SorceryCraft.MODID, "drained_crystalised_redstone_block"), DRAINED_CRYSTALISED_REDSTONE_BLOCK);
        Registry.register(Registry.BLOCK, new ResourceLocation(SorceryCraft.MODID, "shield_block"), SHIELD_BLOCK);
    }
}
