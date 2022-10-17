package net.zestyblaze.sorcerycraft.magic.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;

public class LevitateSpell extends Spell {
    public LevitateSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.PROJECTILE);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 400 + (getLevel() * 160)));
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
