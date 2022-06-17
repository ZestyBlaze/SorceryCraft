package net.zestyblaze.sorcerycraft.api;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;

/**
 * Spell Implementation Base Class
 */
@Deprecated
public abstract class Spell {
    private final ResourceLocation id;
    private final int level;

    public Spell(ResourceLocation id, int level) {
        this.id = id;
        this.level = level;
    }

    /**
     * Get the ID of this spell
     * @return The Spell ID
     */
    public ResourceLocation getID() {
        return id;
    }

    /**
     * Get the level of this spell
     * @return The Spell's Level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Execute this spell on an Entity
     * @param world World
     * @param source A SpellEntity
     * @param attacker The Entity that cast this spell
     * @param target The Target
     */
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        // OPTIONAL
    }

    /**
     * Execute this spell on a block
     * @param world World
     * @param source A SpellEntity
     * @param attacker The Entity that cast this spell
     * @param hitResult The block's HitResult
     */
    public void execute(Level world, Entity source, Entity attacker, BlockHitResult hitResult) {
        // OPTIONAL
    }

    /**
     * Get the maximum level of this spell
     * @return The Spell's Max Level
     */
    public abstract int getMaxLevel();

    /**
     * Default Implementation for Spell.getTranslation()
     * @param id Spell ID
     * @param level Spell Level
     * @return Translated Display Name
     */
    public static MutableComponent getDefaultTranslation(ResourceLocation id, int level) {
        MutableComponent text = new TranslatableComponent("spell." + id.getNamespace() + '.' +  id.getPath());
        if (level != 0 || SpellRegistry.getMaxLevel(id) != 1) {
            text.append(" ").append(new TranslatableComponent("enchantment.level." + (level + 1)));
        }
        return text;
    }

    /**
     * Get Translated Display Name
     * @return Translated Display Name
     */
    public MutableComponent getTranslation() {
        return getDefaultTranslation(getID(), getLevel());
    }
}
