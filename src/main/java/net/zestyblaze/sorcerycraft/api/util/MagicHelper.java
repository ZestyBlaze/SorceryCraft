package net.zestyblaze.sorcerycraft.api.util;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.orb.Orb;
import net.zestyblaze.sorcerycraft.api.registry.OrbRegistry;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;
import net.zestyblaze.sorcerycraft.config.SCModConfig;
import net.zestyblaze.sorcerycraft.registry.SCCriteriaInit;
import net.zestyblaze.sorcerycraft.registry.SCMobEffectInit;
import net.zestyblaze.sorcerycraft.util.OrbPlayerEntity;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MagicHelper {
    public static final String SPELL_TAG = "Spells";
    public static final String ORB_TAG = "Orbs";

    public static boolean didSpellSucceed(Entity entity) {
        return ((Player)Objects.requireNonNull(entity)).isCreative() || Math.random() > SCModConfig.get().spellFailureChance || ((LivingEntity)Objects.requireNonNull(entity)).hasEffect(SCMobEffectInit.STEADFAST);
    }

    public static boolean didOrbSucceed(Entity entity) {
        return ((Player)Objects.requireNonNull(entity)).isCreative() || Math.random() > SCModConfig.get().orbFailureChance;
    }

    public static void setSpells(@NotNull ItemStack itemStack, Map<ResourceLocation, Integer> map) {
        CompoundTag tag;
        if (itemStack.hasTag()) {
            tag = itemStack.getTag();
        } else {
            tag = new CompoundTag();
            tag.put(SPELL_TAG, new ListTag());
        }
        assert tag != null;

        tag.put(SPELL_TAG, createSpellsTag(map));

        itemStack.setTag(tag);
    }

    public static void setOrbs(@NotNull ItemStack itemStack, Map<ResourceLocation, Integer> map) {
        CompoundTag tag;
        if (itemStack.hasTag()) {
            tag = itemStack.getTag();
        } else {
            tag = new CompoundTag();
            tag.put(ORB_TAG, new ListTag());
        }
        assert tag != null;

        tag.put(ORB_TAG, createOrbsTag(map));

        itemStack.setTag(tag);
    }

    public static @NotNull ListTag createSpellsTag(@NotNull Map<ResourceLocation, Integer> map) {
        ListTag spells = new ListTag();

        for (Map.Entry<ResourceLocation, Integer> entry : map.entrySet()) {
            CompoundTag spell = new CompoundTag();
            spell.putString("id", entry.getKey().toString());
            spell.putInt("level", entry.getValue());
            spells.add(spell);
        }

        return spells;
    }

    public static @NotNull ListTag createOrbsTag(@NotNull Map<ResourceLocation, Integer> map) {
        ListTag orbs = new ListTag();

        for (Map.Entry<ResourceLocation, Integer> entry : map.entrySet()) {
            CompoundTag orb = new CompoundTag();

            orb.putString("id", entry.getKey().toString());
            orb.putInt("level", entry.getValue());
            orbs.add(orb);
        }

        return orbs;
    }

    public static @NotNull Map<ResourceLocation, Integer> getSpells(@NotNull ItemStack itemStack) {
        return getSpells(itemStack.getTag());
    }

    public static @NotNull Map<ResourceLocation, Integer> getOrbs(@NotNull ItemStack itemStack) {
        return getOrbs(itemStack.getTag());
    }

    public static @NotNull Map<ResourceLocation, Integer> getSpells(CompoundTag tag) {
        if (tag == null) {
            tag = new CompoundTag();
            tag.put(SPELL_TAG, new ListTag());
        }
        Tag spellsTag = tag.get(SPELL_TAG);
        ListTag spells;
        if (spellsTag instanceof ListTag) {
            spells = (ListTag) spellsTag;
        } else {
            spells = new ListTag();
        }

        Map<ResourceLocation, Integer> map = new HashMap<>();

        for (int i = 0; i < spells.size(); i++) {
            CompoundTag spell = spells.getCompound(i);

            ResourceLocation id = new ResourceLocation(spell.getString("id"));
            int level = spell.getInt("level");

            if (map.get(id) == null || map.get(id) < level) {
                map.put(id, level);
            }
        }

        return map;
    }

    public static @NotNull Map<ResourceLocation, Integer> getOrbs(CompoundTag tag) {
        if (tag == null) {
            tag = new CompoundTag();
            tag.put(ORB_TAG, new ListTag());
        }
        Tag orbsTag = tag.get(ORB_TAG);
        ListTag orbs;
        if (orbsTag instanceof ListTag) {
            orbs = (ListTag) orbsTag;
        } else {
            orbs = new ListTag();
        }

        Map<ResourceLocation, Integer> map = new HashMap<>();

        for (int i = 0; i < orbs.size(); i++) {
            CompoundTag orb = orbs.getCompound(i);

            ResourceLocation id = new ResourceLocation(orb.getString("id"));
            int level = orb.getInt("level");

            if (map.get(id) == null || map.get(id) < level) {
                map.put(id, level);
            }
        }

        return map;
    }


    public static Component getTranslatedSpellType(ResourceLocation id, int level) {
        Spell spell = SpellRegistry.getSpell(id, level);
        MutableComponent text;
        if(spell != null) {
            SpellType spellType = spell.getSpellType();
            if(spellType != null) {
                switch (spellType) {
                    case SELF -> text = Component.translatable("spellType.self");
                    case PROJECTILE -> text = Component.translatable("spellType.projectile");
                    case BOTH -> text = Component.translatable("spellType.both");
                    default -> throw new IllegalStateException("Unexpected value: " + spellType);
                }
                text.withStyle(ChatFormatting.GREEN);
                return text;
            }
        }
        return null;
    }


    public static @NotNull Component getTranslatedSpell(ResourceLocation id, int level) {
        Spell spell = SpellRegistry.getSpell(id, level);
        MutableComponent text;
        if (spell != null) {
            text = spell.getTranslation();
        } else {
            text = Spell.getDefaultTranslation(id, level);
        }
        return text;
    }

    public static @NotNull Component getTranslatedOrb(ResourceLocation id, int level) {
        Orb orb = OrbRegistry.getOrb(id, level);
        MutableComponent text;
        if (orb != null) {
            text = orb.getTranslation();
        } else {
            text = Orb.getDefaultTranslation(id, level);
        }
        return text;
    }

    public static Component getTranslatedSpellChat(ResourceLocation id, int level) {
        return Component.literal("[").append(MagicHelper.getTranslatedSpell(id, level).getString()).append("]").withStyle(ChatFormatting.GREEN);
    }

    public static Component getTranslatedOrbChat(ResourceLocation id, int level) {
        return Component.literal("[").append(MagicHelper.getTranslatedOrb(id, level).getString()).append("]").withStyle(ChatFormatting.GREEN);
    }

    public static void learnSpells(@NotNull Player player, @NotNull Map<ResourceLocation, Integer> itemSpells) {
        SpellPlayerEntity spellPlayer = (SpellPlayerEntity)player;
        Level world = player.getLevel();

        Map<ResourceLocation, Integer> playerSpells = spellPlayer.getDiscoveredSpells();

        boolean changed = false;

        for (Map.Entry<ResourceLocation, Integer> entry : itemSpells.entrySet()) {
            Spell spell = SpellRegistry.getSpell(entry);
            if (spell != null) {
                if (spell.getLevel() >= spell.getMaxLevel()) {
                    spell = SpellRegistry.getSpell(entry.getKey(), spell.getMaxLevel() - 1);
                }
                assert spell != null;
                if (!playerSpells.containsKey(spell.getID()) || playerSpells.get(spell.getID()) < spell.getLevel()) {
                    changed = true;

                    playerSpells.put(spell.getID(), spell.getLevel());
                    assert world.getServer() != null;
                    Component text = getTranslatedSpellChat(spell.getID(), spell.getLevel());
                    world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("chat." + SorceryCraft.MODID + ".discovered_spell", player.getDisplayName(), text), ChatType.SYSTEM);
                }
            }
        }
        if (changed) {
            SpellSoundUtil.playSpellDiscoverSound(player.getLevel(), player.blockPosition());
            spellPlayer.setDiscoveredSpells(playerSpells);
            SCCriteriaInit.DISCOVER_ALL_SPELLS_CRITERION.trigger((ServerPlayer)player);
        }
    }

    public static void learnOrbs(@NotNull Player player, @NotNull Map<ResourceLocation, Integer> itemOrbs) {
        OrbPlayerEntity orbPlayer = (OrbPlayerEntity)player;
        Level world = player.getLevel();

        Map<ResourceLocation, Integer> playerOrbs = orbPlayer.getDiscoveredOrbs();

        boolean changed = false;

        for (Map.Entry<ResourceLocation, Integer> entry : itemOrbs.entrySet()) {
            Orb orb = OrbRegistry.getOrb(entry);
            if (orb != null) {
                if (orb.getLevel() >= orb.getMaxLevel()) {
                    orb = OrbRegistry.getOrb(entry.getKey(), orb.getMaxLevel() - 1);
                }
                assert orb != null;
                if (!playerOrbs.containsKey(orb.getId()) || playerOrbs.get(orb.getId()) < orb.getLevel()) {
                    changed = true;

                    playerOrbs.put(orb.getId(), orb.getLevel());
                    assert world.getServer() != null;
                    Component text = getTranslatedOrbChat(orb.getId(), orb.getLevel());
                    world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("chat." + SorceryCraft.MODID + ".discovered_orb", player.getDisplayName(), text), ChatType.SYSTEM);
                }
            }
        }
        if (changed) {
            SpellSoundUtil.playOrbDiscoverSound(player.getLevel(), player.blockPosition());
            orbPlayer.setDiscoveredOrbs(playerOrbs);
            SCCriteriaInit.DISCOVER_ALL_ORBS_CRITERION.trigger((ServerPlayer)player);
        }
    }
}
