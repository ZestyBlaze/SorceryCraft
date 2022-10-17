package net.zestyblaze.sorcerycraft.magic.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;

public class CleanseSpell extends Spell {
    public CleanseSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.BOTH);
    }

    @Override
    public void execute(Level level, Entity source, Entity attacker, Entity target) {
        if(target instanceof LivingEntity) {
            ((LivingEntity)target).removeAllEffects();
        }
    }

    @Override
    public void execute(Level world, ItemStack source, Entity self) {
        if(self instanceof LivingEntity) {
            ((LivingEntity)self).removeAllEffects();
        }
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
