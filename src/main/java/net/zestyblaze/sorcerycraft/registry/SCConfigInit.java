package net.zestyblaze.sorcerycraft.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.zestyblaze.sorcerycraft.config.SCModConfig;

public class SCConfigInit {
    public static void registerConfig() {
        AutoConfig.register(SCModConfig.class, GsonConfigSerializer::new);
    }
}
