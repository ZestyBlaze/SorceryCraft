package net.zestyblaze.sorcerycraft.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpellPlaguedBeast extends Monster {
    public SpellPlaguedBeast(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true, false));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0d)
                .add(Attributes.ATTACK_DAMAGE, 6.0d)
                .add(Attributes.MOVEMENT_SPEED, 0.20d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3d);
    }

    @Override
    public boolean hurt(DamageSource source, float f) {
        if(this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getDirectEntity();
            if(entity != null && source.isProjectile()) {
                f = f * 0.4f;
            } else if(entity != null && !(source.isFire()) && !(source.isMagic()) && !(source.isExplosion())) {
                f = f * 0.6f;
            }
            return super.hurt(source, f);
        }
    }
}
