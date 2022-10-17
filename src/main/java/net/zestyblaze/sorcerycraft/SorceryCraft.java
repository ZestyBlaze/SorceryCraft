package net.zestyblaze.sorcerycraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.zestyblaze.sorcerycraft.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SorceryCraft implements ModInitializer {
	public static final String MODID = "sorcerycraft";
	public static final Logger LOG = LogManager.getLogger("SorceryCraft");
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(
			new ResourceLocation(MODID, "sorcerycraft"),
			() -> new ItemStack(SCItemInit.ORB)
	);

	@Override
	public void onInitialize() {
		SCConfigInit.registerConfig();
		SCSpellsInit.registerSpells();
		SCOrbsInit.registerOrbs();
		SCBlockInit.registerBlocks();
		SCItemInit.registerItems();
		SCBlockEntityInit.registerBlockEntities();
		SCEntityInit.registerEntities();
		SCMobEffectInit.registerMobEffects();
		SCSoundInit.registerSounds();
		SCWorldInit.registerOres();
		SCRecipeInit.registerRecipes();
		SCCriteriaInit.registerCriteria();
		SCBehaviourInit.registerDispenserBehaviour();
		SCLootInit.registerLootTables();
		SCLootInit.registerLootFunctions();
		SCStatInit.registerStats();
	}
}
