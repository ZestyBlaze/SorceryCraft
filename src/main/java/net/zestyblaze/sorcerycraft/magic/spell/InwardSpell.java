package net.zestyblaze.sorcerycraft.magic.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;
import net.zestyblaze.sorcerycraft.registry.SCMobEffectInit;

public class InwardSpell extends Spell {
    public InwardSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.PROJECTILE);
    }

    @Override
    public void execute(Level level, Entity source, Entity attacker, Entity target) {
        if(target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new MobEffectInstance(SCMobEffectInit.INWARD, 3600, 0, true, true));
        }
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
