package net.zestyblaze.sorcerycraft.entity;

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
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.config.SCModConfig;
import net.zestyblaze.sorcerycraft.registry.SCEntityInit;
import net.zestyblaze.sorcerycraft.registry.SCItemInit;
import net.zestyblaze.sorcerycraft.registry.SCMobEffectInit;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Deprecated
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

    private boolean didSpellSucceed() {
        return ((Player) Objects.requireNonNull(getOwner())).isCreative() || Math.random() > SCModConfig.get().failureChance || ((LivingEntity) Objects.requireNonNull(getOwner())).hasEffect(SCMobEffectInit.STEADFAST);
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!getLevel().isClientSide) {
            Map<ResourceLocation, Integer> spells = SpellHelper.getSpells(getItem());
            if (!((LivingEntity)Objects.requireNonNull(getOwner())).hasEffect(SCMobEffectInit.INWARD)) {
                boolean success = didSpellSucceed();
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
                            if (getOwner() instanceof Player player) {
                                player.playNotifySound(SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 1.0f, 1.0f);
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
            Map<ResourceLocation, Integer> spells = SpellHelper.getSpells(getItem());
            if (((LivingEntity) Objects.requireNonNull(getOwner())).hasEffect(SCMobEffectInit.INWARD)) {
                if (getOwner() != null) {
                    boolean success = didSpellSucceed();
                    for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
                        Spell spell = SpellRegistry.getSpell(entry);
                        if (spell != null) {
                            if (success) {
                                spell.execute(level, this, getOwner(), getOwner());
                            } else if (getOwner() instanceof Player player) {
                                player.playNotifySound(SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 1.0f, 1.0f);
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
        return SCItemInit.SPELL_ITEM;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
