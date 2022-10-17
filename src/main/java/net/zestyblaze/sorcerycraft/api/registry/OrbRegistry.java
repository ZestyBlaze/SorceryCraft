package net.zestyblaze.sorcerycraft.api.registry;

import net.minecraft.resources.ResourceLocation;
import net.zestyblaze.sorcerycraft.api.orb.Orb;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrbRegistry {
    private static final Map<ResourceLocation, Class<?>> orbs = new HashMap<>();

    /**
     * Get Orb Implementation
     * @param entry Map Entry
     * @return Orb Implementation
     */
    public static Orb getOrb(Map.Entry<ResourceLocation, Integer> entry) {
        return getOrb(entry.getKey(), entry.getValue());
    }

    /**
     * Get Orb Implementation
     * @param id Orb ID
     * @return Orb Implementation
     */
    public static Orb getOrb(ResourceLocation id, int level) {
        if (!orbs.containsKey(id)) {
            return null;
        }
        try {
            return (Orb)orbs.get(id).getConstructor(ResourceLocation.class, int.class).newInstance(id, level);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Max Level of the Orb
     * @param id Orb ID
     * @return Max Level
     */
    public static int getMaxLevel(ResourceLocation id) {
        Orb tempOrb = getOrb(id, 0);
        if (tempOrb == null) {
            return -1;
        }
        return tempOrb.getMaxLevel();
    }

    /**
     * Get All Orb Implementations
     * @return List of Orb Implementations
     */
    public static Orb[] getOrbs() {
        List<Orb> out = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Class<?>> entry : orbs.entrySet()) {
            int maxLevel = getMaxLevel(entry.getKey());
            if (maxLevel == -1) {
                continue;
            }
            for (int i = 0; i < maxLevel; i++) {
                Orb orb = getOrb(entry.getKey(), i);
                if (orb != null) {
                    out.add(orb);
                }
            }
        }
        return out.toArray(new Orb[0]);
    }

    /**
     * Get All Max Level Orb Implementations
     * @return List of Orb Implementations
     */
    public static Orb[] getMaxOrbs() {
        List<Orb> out = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Class<?>> entry : orbs.entrySet()) {
            int maxLevel = getMaxLevel(entry.getKey());
            if (maxLevel == -1) {
                continue;
            }
            out.add(getOrb(entry.getKey(), maxLevel - 1));
        }
        return out.toArray(new Orb[0]);
    }

    /**
     * Register a Orb
     * @param id The Orb ID
     * @param orb The Orb Class
     * @return The Orb ID
     */
    public static ResourceLocation register(ResourceLocation id, Class<?> orb) {
        orbs.put(id, orb);
        return id;
    }

    /**
     * Gat All Orb IDs
     * @return List of Orb IDs
     */
    public static ResourceLocation[] getOrbsID() {
        return orbs.keySet().toArray(new ResourceLocation[0]);
    }
}
