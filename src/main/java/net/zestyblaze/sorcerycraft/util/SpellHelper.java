package net.zestyblaze.sorcerycraft.util;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.registry.SCCriteriaInit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SpellHelper {
    public static final String SPELL_TAG = "Spells";

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

    public static @NotNull Map<ResourceLocation, Integer> getSpells(@NotNull ItemStack itemStack) {
        return getSpells(itemStack.getTag());
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

    public static @NotNull Component getTranslatedSpell(ResourceLocation id, int level) {
        Spell spell = SpellRegistry.getSpell(id, level);
        MutableComponent text;
        if (spell != null) {
            text = spell.getTranslation();
        } else {
            text = Spell.getDefaultTranslation(id, level);
        }
        text.withStyle(ChatFormatting.GRAY);
        return text;
    }

    public static Component getTranslatedSpellChat(ResourceLocation id, int level) {
        return Component.literal("[").append(SpellHelper.getTranslatedSpell(id, level).getString()).append("]").withStyle(ChatFormatting.GREEN);
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
                    world.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(Component.translatable("chat." + SorceryCraft.MODID + ".discovered_spell", player.getDisplayName(), text), ChatType.CHAT, Util.NIL_UUID));
                }
            }
        }
        if (changed) {
            SoundUtil.playSpellSound(player);
            spellPlayer.setDiscoveredSpells(playerSpells);
            SCCriteriaInit.DISCOVER_ALL_SPELLS_CRITERION.trigger((ServerPlayer)player);
        }
    }
}
