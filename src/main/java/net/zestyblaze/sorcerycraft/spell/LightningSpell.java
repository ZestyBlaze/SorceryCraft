package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;

public class LightningSpell extends Spell {
    public LightningSpell(ResourceLocation id, int level) {
        super(id, level, SpellType.PROJECTILE);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        strike(world, target.position(), attacker);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, BlockHitResult hitResult) {
        strike(world, hitResult.getLocation(), attacker);
    }

    private void strike(Level world, Vec3 pos, Entity attacker) {
        ServerLevel serverLevel = (ServerLevel) world;
        LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
        assert lightningEntity != null;
        lightningEntity.absMoveTo(pos.x, pos.y, pos.z);
        if (attacker instanceof ServerPlayer) {
            lightningEntity.setCause((ServerPlayer) attacker);
        }
        serverLevel.addFreshEntity(lightningEntity);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
