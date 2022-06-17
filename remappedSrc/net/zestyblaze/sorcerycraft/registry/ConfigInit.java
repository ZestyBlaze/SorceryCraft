package net.zestyblaze.sorcerycraft.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.zestyblaze.sorcerycraft.config.ModConfig;

public class ConfigInit {
    public static void registerConfig() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }
}
