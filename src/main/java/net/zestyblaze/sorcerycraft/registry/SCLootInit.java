package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.util.RandomSpellLootTableFunction;

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
<<<<<<< Updated upstream
        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {
            if(setter.isBuiltin() && isSelectedLootTable(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .with(LootItem.lootTableItem(SCItemInit.SPELL_ITEM).build())
                        .apply(new RandomSpellLootTableFunction.Builder().build());
                supplier.withPool(poolBuilder);
=======
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(source.isBuiltin() && isSelectedLootTable(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .with(LootItem.lootTableItem(SCItemInit.PROJECTILE_SPELL).build())
                        .apply(new RandomProjectileSpellLootTableFunction.Builder().build());
                tableBuilder.withPool(poolBuilder);
            }
        }));

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(source.isBuiltin() && isSelectedLootTable(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .with(LootItem.lootTableItem(SCItemInit.SELF_SPELL).build())
                        .apply(new RandomSelfSpellLootTableFunction.Builder().build());
                tableBuilder.withPool(poolBuilder);
            }
        }));

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(source.isBuiltin() && isSelectedLootTable(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(BinomialDistributionGenerator.binomial(2, 0.5f))
                        .with(LootItem.lootTableItem(SCItemInit.MULTI_TYPE_SPELL).build())
                        .apply(new RandomMultiTypeSpellLootTableFunction.Builder().build());
                tableBuilder.withPool(poolBuilder);
>>>>>>> Stashed changes
            }
        }));

    }

    public static void registerLootFunctions() {
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(SorceryCraft.MODID, "random_spell"), new LootItemFunctionType(new RandomSpellLootTableFunction.Factory()));
    }
}
