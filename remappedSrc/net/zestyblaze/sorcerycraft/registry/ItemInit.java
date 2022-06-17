package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.item.SpellItem;

public class ItemInit {
    public static Item SPELL_ITEM = new SpellItem();
    public static BlockItem CASTING_TABLE_BLOCK_ITEM = new BlockItem(BlockInit.CASTING_TABLE_BLOCK, new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(SorceryCraft.MODID, "spell"), SPELL_ITEM);
        Registry.register(Registry.ITEM, new Identifier(SorceryCraft.MODID, "casting_table"), CASTING_TABLE_BLOCK_ITEM);
    }
}
