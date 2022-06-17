package net.zestyblaze.sorcerycraft.api.registry;

import net.minecraft.resources.ResourceLocation;
import net.zestyblaze.sorcerycraft.api.Spell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellRegistry {
    private static final Map<ResourceLocation, Class<?>> spells = new HashMap<>();

    /**
     * Get Spell Implementation
     * @param entry Map Entry
     * @return Spell Implementation
     */
    public static Spell getSpell(Map.Entry<ResourceLocation, Integer> entry) {
        return getSpell(entry.getKey(), entry.getValue());
    }

    /**
     * Get Spell Implementation
     * @param id Spell ID
     * @param level Spell Level
     * @return Spell Implementation
     */
    public static Spell getSpell(ResourceLocation id, int level) {
        if (!spells.containsKey(id)) {
            return null;
        }
        try {
            return (Spell) spells.get(id).getConstructor(ResourceLocation.class, int.class).newInstance(id, level);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Max Level of a Spell
     * @param id Spell ID
     * @return Max Level
     */
    public static int getMaxLevel(ResourceLocation id) {
        Spell tempSpell = getSpell(id, 0);
        if (tempSpell == null) {
            return -1;
        }
        return tempSpell.getMaxLevel();
    }

    /**
     * Get All Spell Implementations
     * @return List of Spell Implementations
     */
    public static Spell[] getSpells() {
        List<Spell> out = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Class<?>> entry : spells.entrySet()) {
            int maxLevel = getMaxLevel(entry.getKey());
            if (maxLevel == -1) {
                continue;
            }
            for (int i = 0; i < maxLevel; i++) {
                Spell spell = getSpell(entry.getKey(), i);
                if (spell != null) {
                    out.add(spell);
                }
            }
        }
        return out.toArray(new Spell[0]);
    }

    /**
     * Get All Max Level Spell Implementations
     * @return List of Spell Implementations
     */
    public static Spell[] getMaxSpells() {
        List<Spell> out = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Class<?>> entry : spells.entrySet()) {
            int maxLevel = getMaxLevel(entry.getKey());
            if (maxLevel == -1) {
                continue;
            }
            out.add(getSpell(entry.getKey(), maxLevel - 1));
        }
        return out.toArray(new Spell[0]);
    }

    /**
     * Register a Spell
     * @param id The Spell ID
     * @param spell The Spell Class
     * @return The Spell ID
     */
    public static ResourceLocation register(ResourceLocation id, Class<?> spell) {
        spells.put(id, spell);
        return id;
    }

    /**
     * Gat All Spell IDs
     * @return List of Spell IDs
     */
    public static ResourceLocation[] getSpellsID() {
        return spells.keySet().toArray(new ResourceLocation[0]);
    }
}
