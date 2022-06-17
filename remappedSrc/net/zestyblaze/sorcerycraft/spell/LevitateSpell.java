package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class LevitateSpell extends Spell {
    public LevitateSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, Entity target) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 400 + (getLevel() * 160)));
        }
    }

    @Override
    public int getXPCost() {
        switch (getLevel()) {
            case 0 -> {
                return 12;
            }
            case 1 -> {
                return 24;
            }
        }
        return -1;
    }

    @Override
    public ItemStack getItemCost() {
        switch (getLevel()) {
            case 0 -> {
                return new ItemStack(Items.SHULKER_SHELL);
            }
            case 1 -> {
                return new ItemStack(Items.SHULKER_SHELL, 4);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
