package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class DamageSpell extends Spell {
    public DamageSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public void execute(World level, Entity source, Entity attacker, Entity target) {
        if(target instanceof LivingEntity) {
            StatusEffects.INSTANT_DAMAGE.applyInstantEffect(source, attacker, (LivingEntity)target, getLevel(), 1.0d);
        }
    }

    @Override
    public int getXPCost() {
        switch (getLevel()) {
            case 0 -> {
                return 4;
            }
            case 1 -> {
                return 8;
            }
        }
        return -1;
    }

    @Override
    public ItemStack getItemCost() {
        switch (getLevel()) {
            case 0 -> {
                return ItemStack.EMPTY;
            }
            case 1 -> {
                return new ItemStack(Items.WITHER_ROSE);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
