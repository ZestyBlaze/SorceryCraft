package net.zestyblaze.sorcerycraft.util;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface SpellPlayerEntity {
    void setDiscoveredSpells(Map<ResourceLocation, Integer> spells);

    Map<ResourceLocation, Integer> getDiscoveredSpells();
}
