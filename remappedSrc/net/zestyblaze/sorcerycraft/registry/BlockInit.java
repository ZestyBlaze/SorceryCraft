package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.block.CastingTableBlock;

public class BlockInit {
    public static Block CASTING_TABLE_BLOCK = new CastingTableBlock();

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(SorceryCraft.MODID, "casting_table"), CASTING_TABLE_BLOCK);
    }
}
