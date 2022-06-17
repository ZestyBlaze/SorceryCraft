package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class DissolveSpell extends Spell {
    public DissolveSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public void execute(World level, Entity source, Entity attacker, Entity target) {
        if(target instanceof LivingEntity) {
            ((LivingEntity)target).clearStatusEffects();
        }
    }

    @Override
    public int getXPCost() {
        return 8;
    }

    @Override
    public ItemStack getItemCost() {
        return new ItemStack(Items.CLOCK);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
