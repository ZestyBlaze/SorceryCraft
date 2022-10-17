package net.zestyblaze.sorcerycraft.util;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface OrbPlayerEntity {
    void setDiscoveredOrbs(Map<ResourceLocation, Integer> orbs);

    Map<ResourceLocation, Integer> getDiscoveredOrbs();
}
