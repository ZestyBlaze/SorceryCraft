package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;

public class HealSpell extends Spell {
    public HealSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.BOTH);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        if (target instanceof LivingEntity) {
            MobEffects.HEAL.applyInstantenousEffect(source, attacker, (LivingEntity)target, getLevel(), 1.0d);
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
