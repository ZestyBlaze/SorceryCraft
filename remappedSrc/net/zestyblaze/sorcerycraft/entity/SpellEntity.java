package net.zestyblaze.sorcerycraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.registry.EntityInit;
import net.zestyblaze.sorcerycraft.registry.SpellsInit;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.config.ModConfig;
import net.zestyblaze.sorcerycraft.registry.ItemInit;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpellEntity extends ThrownItemEntity {
    public SpellEntity(EntityType<SpellEntity> entityType, World level) {
        super(entityType, level);
    }

    public SpellEntity(World world, LivingEntity owner) {
        super(EntityInit.SPELL_ENTITY, owner, world);
    }

    public SpellEntity(World world, double x, double y, double z) {
        super(EntityInit.SPELL_ENTITY, x, y, z, world);
    }

    public SpellEntity(World world) {
        super(EntityInit.SPELL_ENTITY, world);
    }

    private boolean didSpellSucceed(Map<Identifier, Integer> spells) {
        return Math.random() > ModConfig.get().failureChance || spells.containsKey(SpellsInit.STEADFAST_SPELL);
    }

    @Override
    protected void onCollision(@NotNull HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient) {
            Map<Identifier, Integer> spells = SpellHelper.getSpells(getStack());
            if (!spells.containsKey(SpellsInit.INWARD_SPELL)) {
                boolean success = didSpellSucceed(spells);
                for (Map.Entry<Identifier, Integer> entry : spells.entrySet()) {
                    Spell spell = SpellRegistry.getSpell(entry);
                    if (spell != null) {
                        if (success) {
                            if (hitResult.getType() == HitResult.Type.BLOCK) {
                                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                                spell.execute(getWorld(), this, getOwner(), blockHitResult);
                            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                                Entity entity = ((EntityHitResult) hitResult).getEntity();
                                spell.execute(world, this, getOwner(), entity);
                            }
                        } else if (getOwner() != null) {
                            if (getOwner() instanceof PlayerEntity player) {
                                player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                            }
                            spell.execute(world, this, getOwner(), getOwner());
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
        if (!getWorld().isClient) {
            Map<Identifier, Integer> spells = SpellHelper.getSpells(getStack());
            if (spells.containsKey(SpellsInit.INWARD_SPELL)) {
                if (getOwner() != null) {
                    boolean success = didSpellSucceed(spells);
                    for (Map.Entry<Identifier, Integer> entry : spells.entrySet()) {
                        Spell spell = SpellRegistry.getSpell(entry);
                        if (spell != null) {
                            if (success) {
                                spell.execute(world, this, getOwner(), getOwner());
                            } else if (getOwner() instanceof PlayerEntity player) {
                                player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                            }
                        }
                    }
                }
                discard();
                return;
            }

            List<ServerPlayerEntity> viewers = Objects.requireNonNull(getServer()).getPlayerManager().getPlayerList();
            for (ServerPlayerEntity viewer : viewers) {
                ((ServerWorld)getWorld()).spawnParticles(viewer, ParticleTypes.WITCH, true, getX(), getY(), getZ(), 8, 0.1d, 0.1d, 0.1d, 0d);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.SPELL_ITEM;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
