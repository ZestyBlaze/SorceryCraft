package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;

public class DamageSpell extends Spell {
    public DamageSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.PROJECTILE);
    }

    @Override
    public void execute(Level level, Entity source, Entity attacker, Entity target) {
        if(target instanceof LivingEntity) {
            MobEffects.HARM.applyInstantenousEffect(source, attacker, (LivingEntity)target, getLevel(), 1.0d);
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
