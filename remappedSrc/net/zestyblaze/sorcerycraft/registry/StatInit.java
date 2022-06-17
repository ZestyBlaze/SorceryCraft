package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import org.jetbrains.annotations.NotNull;

public class StatInit {
    public static Identifier INTERACT_WITH_CASTING_TABLE_STAT;
    public static Identifier CAST_SPELL_STAT;

    private static @NotNull Identifier registerStat(String name) {
        Identifier statID = new Identifier(SorceryCraft.MODID, name);
        Registry.register(Registry.CUSTOM_STAT, statID, statID);
        Stats.CUSTOM.getOrCreateStat(statID, StatFormatter.DEFAULT);
        return statID;
    }

    public static void registerStats() {
        INTERACT_WITH_CASTING_TABLE_STAT = registerStat("interact_with_casting_table");
        CAST_SPELL_STAT = registerStat("cast_spell");
    }
}
