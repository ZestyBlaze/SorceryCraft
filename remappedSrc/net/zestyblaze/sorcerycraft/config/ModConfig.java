package net.zestyblaze.sorcerycraft.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.zestyblaze.sorcerycraft.SorceryCraft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Config(name = SorceryCraft.MODID)
public class ModConfig implements ConfigData {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface UsePercentage {
        double min();
        double max();
    }

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public LimitCraftingTable limitCraftingTable = new LimitCraftingTable();

    @UsePercentage(min = 0, max = 1)
    public double failureChance = 0.3;

    public static class LimitCraftingTable {
        public boolean creative = false;
        public boolean survival = true;
    }

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
