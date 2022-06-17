package net.zestyblaze.sorcerycraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.registry.*;

public class SorceryCraft implements ModInitializer {
	public static final String MODID = "sorcerycraft";

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MODID, "spells"),
			() -> new ItemStack(ItemInit.SPELL_ITEM)
	);

	@Override
	public void onInitialize() {
		ConfigInit.registerConfig();
		SpellsInit.registerSpells();
		BlockInit.registerBlocks();
		ItemInit.registerItems();
		EntityInit.registerEntities();
		ContainerInit.registerContainers();
		CriteriaInit.registerCriteria();
		BehaviourInit.registerDispenserBehaviour();
		NetworkingInit.registerServerNetworks();
		LootInit.registerLootTables();
		LootInit.registerLootFunctions();
		StatInit.registerStats();
	}
}
