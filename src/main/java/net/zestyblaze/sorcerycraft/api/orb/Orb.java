package net.zestyblaze.sorcerycraft.api.orb;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.registry.OrbRegistry;

public abstract class Orb {
    private final ResourceLocation id;
    private final int level;

    public Orb(ResourceLocation id, int level) {
        this.id = id;
        this.level = level;
    }

    /**
     * Get the ID of this orb
     * @return The Orb id
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * Get the level of this orb
     * @return The Orb's Level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Cast the spell from the player
     * @param level World
     * @param caster The Entity that cast this spell
     */
    public void castOrb(Level level, Entity caster) {
        // OPTIONAL
    }

    /**
     * Get the maximum level of this orb
     * @return The Orb's Max Level
     */
    public abstract int getMaxLevel();

    /**
     * Default Implementation for Spell.getTranslation()
     * @param id Spell ID
     * @param level Spell Level
     * @return Translated Display Name
     */
    public static MutableComponent getDefaultTranslation(ResourceLocation id, int level) {
        MutableComponent text = Component.translatable("orb." + id.getNamespace() + '.' +  id.getPath());
        if (level != 0 || OrbRegistry.getMaxLevel(id) != 1) {
            text.append(" ").append(Component.translatable("enchantment.level." + (level + 1)));
        }
        return text;
    }

    /**
     * Get Translated Display Name
     * @return Translated Display Name
     */
    public MutableComponent getTranslation() {
        return getDefaultTranslation(getId(), getLevel());
    }
}
