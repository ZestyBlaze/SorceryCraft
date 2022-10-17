package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.item.*;

public class SCItemInit {
    public static Item ARCANUS_NOVELLA = new GuideBookItem(new FabricItemSettings().maxCount(1).group(SorceryCraft.ITEM_GROUP));
    public static Item CRYSTAL_ORE = new BlockItem(SCBlockInit.CRYSTAL_ORE, new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item CRYSTAL = new Item(new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item HALLOWED_IRON = new BlockItem(SCBlockInit.HALLOWED_IRON, new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item CRYSTALISED_REDSTONE_BLOCK = new BlockItem(SCBlockInit.CRYSTALISED_REDSTONE_BLOCK, new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item DRAINED_CRYSTALISED_REDSTONE_BLOCK = new BlockItem(SCBlockInit.DRAINED_CRYSTALISED_REDSTONE_BLOCK, new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item SHIELD_BLOCK = new BlockItem(SCBlockInit.SHIELD_BLOCK, new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item CRYSTALISED_REDSTONE = new CrystalisedRedstoneItem(new FabricItemSettings().group(SorceryCraft.ITEM_GROUP));
    public static Item BLANK_SCROLL = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).group(SorceryCraft.ITEM_GROUP));
    public static Item SELF_SPELL = new SelfUseSpellItem();
    public static Item PROJECTILE_SPELL = new ProjectileSpellItem();
    public static Item MULTI_TYPE_SPELL = new MultiTypeSpellItem();
    public static Item ORB = new OrbItem();

    public static void registerItems() {
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "arcanus_novela"), ARCANUS_NOVELLA);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "crystal_ore"), CRYSTAL_ORE);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "crystal"), CRYSTAL);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "hallowed_iron"), HALLOWED_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "crystalised_redstone_block"), CRYSTALISED_REDSTONE_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "drained_crystalised_redstone_block"), DRAINED_CRYSTALISED_REDSTONE_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "shield_block"), SHIELD_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "crystalised_redstone"), CRYSTALISED_REDSTONE);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "blank_scroll"), BLANK_SCROLL);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "self_use_spell"), SELF_SPELL);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "projectile_spell"), PROJECTILE_SPELL);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "multi_type_spell"), MULTI_TYPE_SPELL);
        Registry.register(Registry.ITEM, new ResourceLocation(SorceryCraft.MODID, "orb_item"), ORB);
    }
}
