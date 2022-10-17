package net.zestyblaze.sorcerycraft.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.util.MagicHelper;
import net.zestyblaze.sorcerycraft.api.util.SpellSoundUtil;
import net.zestyblaze.sorcerycraft.registry.SCEntityInit;
import net.zestyblaze.sorcerycraft.registry.SCItemInit;
import net.zestyblaze.sorcerycraft.registry.SCMobEffectInit;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpellEntity extends ThrowableItemProjectile {
    public SpellEntity(EntityType<SpellEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SpellEntity(Level world, LivingEntity owner) {
        super(SCEntityInit.SPELL_ENTITY, owner, world);
    }

    public SpellEntity(Level world, double x, double y, double z) {
        super(SCEntityInit.SPELL_ENTITY, x, y, z, world);
    }

    public SpellEntity(Level world) {
        super(SCEntityInit.SPELL_ENTITY, world);
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!getLevel().isClientSide) {
            Map<ResourceLocation, Integer> spells = MagicHelper.getSpells(getItem());
            if (!((LivingEntity)Objects.requireNonNull(getOwner())).hasEffect(SCMobEffectInit.INWARD)) {
                boolean success = MagicHelper.didSpellSucceed(getOwner());
                for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
                    Spell spell = SpellRegistry.getSpell(entry);
                    if (spell != null) {
                        if (success) {
                            if (hitResult.getType() == HitResult.Type.BLOCK) {
                                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                                spell.execute(getLevel(), this, getOwner(), blockHitResult);
                            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                                Entity entity = ((EntityHitResult) hitResult).getEntity();
                                spell.execute(level, this, getOwner(), entity);
                            }
                        } else if (getOwner() != null) {
                            if (getOwner() instanceof Player) {
                                SpellSoundUtil.playSpellFailSound(level, blockPosition());
                            }
                            spell.execute(level, this, getOwner(), getOwner());
                        }
                    }
                }
                discard();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!getLevel().isClientSide) {
            Map<ResourceLocation, Integer> spells = MagicHelper.getSpells(getItem());
            if (((LivingEntity) Objects.requireNonNull(getOwner())).hasEffect(SCMobEffectInit.INWARD)) {
                if (getOwner() != null) {
                    boolean success = MagicHelper.didSpellSucceed(getOwner());
                    for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
                        Spell spell = SpellRegistry.getSpell(entry);
                        if (spell != null) {
                            if (success) {
                                spell.execute(level, this, getOwner(), getOwner());
                            } else if (getOwner() instanceof Player) {
                                SpellSoundUtil.playSpellFailSound(level, blockPosition());
                            }
                        }
                    }
                }
                discard();
                return;
            }

            List<ServerPlayer> viewers = Objects.requireNonNull(getServer()).getPlayerList().getPlayers();
            for (ServerPlayer viewer : viewers) {
                ((ServerLevel)getLevel()).sendParticles(viewer, ParticleTypes.WITCH, true, getX(), getY(), getZ(), 8, 0.1d, 0.1d, 0.1d, 0d);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return SCItemInit.PROJECTILE_SPELL;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
