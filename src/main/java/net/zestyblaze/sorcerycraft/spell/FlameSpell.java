package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;

public class FlameSpell extends Spell {
    public FlameSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.PROJECTILE);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        if (attacker instanceof LivingEntity) {
            ((LivingEntity) attacker).setLastHurtMob(target);
        }
        target.setSecondsOnFire(8 + (getLevel() * 4));
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, BlockHitResult hitResult) {
        Items.FLINT_AND_STEEL.useOn(new DirectionalPlaceContext(world, hitResult.getBlockPos(), hitResult.getDirection().getOpposite(), new ItemStack(Items.FLINT_AND_STEEL), hitResult.getDirection()));
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
