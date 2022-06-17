package net.zestyblaze.sorcerycraft.util.spell;

import java.util.Map;
import net.minecraft.util.Identifier;

public interface SpellPlayerEntity {
    void setDiscoveredSpells(Map<Identifier, Integer> spells);

    Map<Identifier, Integer> getDiscoveredSpells();
}
