package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import org.jetbrains.annotations.NotNull;

public class SCStatInit {
    public static ResourceLocation CAST_SPELL_STAT;

    private static @NotNull ResourceLocation registerStat(String name) {
        ResourceLocation statID = new ResourceLocation(SorceryCraft.MODID, name);
        Registry.register(Registry.CUSTOM_STAT, statID, statID);
        Stats.CUSTOM.get(statID, StatFormatter.DEFAULT);
        return statID;
    }

    public static void registerStats() {
        CAST_SPELL_STAT = registerStat("cast_spell");
    }
}
