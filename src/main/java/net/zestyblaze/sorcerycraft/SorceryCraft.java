package net.zestyblaze.sorcerycraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.zestyblaze.sorcerycraft.registry.*;

public class SorceryCraft implements ModInitializer {
	public static final String MODID = "sorcerycraft";
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(
			new ResourceLocation(MODID, "spells"),
			() -> new ItemStack(SCItemInit.BLANK_SCROLL)
	);

	@Override
	public void onInitialize() {
		SCConfigInit.registerConfig();
		SCSpellsInit.registerSpells();
		SCBlockInit.registerBlocks();
		SCItemInit.registerItems();
		SCBlockEntityInit.registerBlockEntities();
		SCEntityInit.registerEntities();
		SCMobEffectInit.registerMobEffects();
		SCWorldInit.registerOres();
		SCRecipeInit.registerRecipes();
		SCCriteriaInit.registerCriteria();
		SCBehaviourInit.registerDispenserBehaviour();
		SCLootInit.registerLootTables();
		SCLootInit.registerLootFunctions();
		SCStatInit.registerStats();
	}
}
