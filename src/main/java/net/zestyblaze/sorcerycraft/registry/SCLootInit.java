package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.util.RandomMultiTypeSpellLootTableFunction;
import net.zestyblaze.sorcerycraft.util.RandomProjectileSpellLootTableFunction;
import net.zestyblaze.sorcerycraft.util.RandomSelfSpellLootTableFunction;

@SuppressWarnings("deprecation")
public class SCLootInit {
    public static final ResourceLocation[] LOOT_TABLES = new ResourceLocation[]{
            BuiltInLootTables.SIMPLE_DUNGEON,
            BuiltInLootTables.END_CITY_TREASURE,
            BuiltInLootTables.NETHER_BRIDGE,
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.SHIPWRECK_TREASURE,
            BuiltInLootTables.DESERT_PYRAMID,
            BuiltInLootTables.JUNGLE_TEMPLE,
            BuiltInLootTables.STRONGHOLD_LIBRARY,
            BuiltInLootTables.PILLAGER_OUTPOST,
            BuiltInLootTables.WOODLAND_MANSION,
            BuiltInLootTables.BURIED_TREASURE,
            BuiltInLootTables.FISHING_TREASURE
    };

    private static boolean isSelectedLootTable(ResourceLocation lootTable) {
        for(ResourceLocation id : LOOT_TABLES) {
            if(id.equals(lootTable)) {
                return true;
            }
        }
        return false;
    }

    public static void registerLootTables() {
        //Needs to be fixed to register all 3 spell types
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if(isSelectedLootTable(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .withEntry(LootItem.lootTableItem(SCItemInit.PROJECTILE_SPELL).build())
                        .withFunction(new RandomProjectileSpellLootTableFunction.Builder().build());
                supplier.withPool(poolBuilder.build());
            }
        }));
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if(isSelectedLootTable(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .withEntry(LootItem.lootTableItem(SCItemInit.SELF_SPELL).build())
                        .withFunction(new RandomSelfSpellLootTableFunction.Builder().build());
                supplier.withPool(poolBuilder.build());
            }
        }));
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if(isSelectedLootTable(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .withEntry(LootItem.lootTableItem(SCItemInit.MULTI_TYPE_SPELL).build())
                        .withFunction(new RandomMultiTypeSpellLootTableFunction.Builder().build());
                supplier.withPool(poolBuilder.build());
            }
        }));
    }

    public static void registerLootFunctions() {
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(SorceryCraft.MODID, "random_projectile_spell"), new LootItemFunctionType(new RandomProjectileSpellLootTableFunction.Factory()));
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(SorceryCraft.MODID, "random_self_spell"), new LootItemFunctionType(new RandomSelfSpellLootTableFunction.Factory()));
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(SorceryCraft.MODID, "random_multi_type_spell"), new LootItemFunctionType(new RandomMultiTypeSpellLootTableFunction.Factory()));
    }
}
