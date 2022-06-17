package net.zestyblaze.sorcerycraft.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;

public abstract class Spell {
    private final Identifier id;
    private final int level;

    public Spell(Identifier id, int level) {
        this.id = id;
        this.level = level;
    }

    public Identifier getID() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void execute(World world, Entity source, Entity attacker, Entity target) {
        // OPTIONAL
    }

    public void execute(World world, Entity source, Entity attacker, BlockHitResult hitResult) {
        // OPTIONAL
    }

    public abstract int getXPCost();

    public abstract ItemStack getItemCost();

    public abstract int getMaxLevel();

    public static MutableText getDefaultTranslation(Identifier id, int level) {
        MutableText text = new TranslatableText("spell." + id.getNamespace() + '.' +  id.getPath());
        if (level != 0 || SpellRegistry.getMaxLevel(id) != 1) {
            text.append(" ").append(new TranslatableText("enchantment.level." + (level + 1)));
        }
        return text;
    }

    public MutableText getTranslation() {
        return getDefaultTranslation(getID(), getLevel());
    }
}
