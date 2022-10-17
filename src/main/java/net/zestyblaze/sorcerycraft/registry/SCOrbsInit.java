package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.resources.ResourceLocation;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.registry.OrbRegistry;
import net.zestyblaze.sorcerycraft.magic.orb.ShieldOrb;

public class SCOrbsInit {
    public static ResourceLocation SHIELD_ORB;

    public static void registerOrbs() {
        SHIELD_ORB = OrbRegistry.register(new ResourceLocation(SorceryCraft.MODID, "shield_orb"), ShieldOrb.class);
    }
}
