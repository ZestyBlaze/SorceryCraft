package net.zestyblaze.sorcerycraft.magic.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;
import net.zestyblaze.sorcerycraft.registry.SCMobEffectInit;

public class SteadfastSpell extends Spell {
    public SteadfastSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.SELF);
    }

    @Override
    public void execute(Level level, ItemStack stack, Entity caster) {
        if(caster instanceof LivingEntity) {
            ((LivingEntity)caster).addEffect(new MobEffectInstance(SCMobEffectInit.STEADFAST, 3600, 0, true, true));
        }
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
