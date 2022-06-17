package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.util.spell.RandomSpellLootTableFunction;

public class LootInit {
    public static final Identifier[] LOOT_TABLES = new Identifier[]{
            LootTables.SIMPLE_DUNGEON_CHEST,
            LootTables.END_CITY_TREASURE_CHEST,
            LootTables.NETHER_BRIDGE_CHEST,
            LootTables.ABANDONED_MINESHAFT_CHEST,
            LootTables.SHIPWRECK_TREASURE_CHEST,
            LootTables.DESERT_PYRAMID_CHEST,
            LootTables.JUNGLE_TEMPLE_CHEST,
            LootTables.STRONGHOLD_LIBRARY_CHEST,
            LootTables.PILLAGER_OUTPOST_CHEST,
            LootTables.WOODLAND_MANSION_CHEST,
            LootTables.BURIED_TREASURE_CHEST,
            LootTables.FISHING_TREASURE_GAMEPLAY
    };

    private static boolean isSelectedLootTable(Identifier lootTable) {
        for(Identifier id : LOOT_TABLES) {
            if(id.equals(lootTable)) {
                return true;
            }
        }
        return false;
    }

    public static void registerLootTables() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if(isSelectedLootTable(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(BinomialLootNumberProvider.create(2, 0.5f))
                        .withEntry(ItemEntry.builder(ItemInit.SPELL_ITEM).build())
                        .withFunction(new RandomSpellLootTableFunction.Builder().build());
                supplier.withPool(poolBuilder.build());
            }
        }));
    }

    public static void registerLootFunctions() {
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(SorceryCraft.MODID, "random_spell"), new LootFunctionType(new RandomSpellLootTableFunction.Factory()));
    }
}
