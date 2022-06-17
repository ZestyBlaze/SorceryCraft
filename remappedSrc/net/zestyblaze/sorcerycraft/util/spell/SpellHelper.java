package net.zestyblaze.sorcerycraft.util.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.MessageType;
import net.minecraft.network.chat.*;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;

import java.util.HashMap;
import java.util.Map;

public class SpellHelper {
    public static final String SPELL_TAG = "Spells";

    public static void setSpells(ItemStack itemStack, Map<Identifier, Integer> map) {
        NbtCompound tag;
        if (itemStack.hasNbt()) {
            tag = itemStack.getNbt();
        } else {
            tag = new NbtCompound();
            tag.put(SPELL_TAG, new NbtList());
        }
        assert tag != null;

        tag.put(SPELL_TAG, createSpellsTag(map));

        itemStack.setNbt(tag);
    }

    public static NbtList createSpellsTag(Map<Identifier, Integer> map) {
        NbtList spells = new NbtList();

        for (Map.Entry<Identifier, Integer> entry : map.entrySet()) {
            NbtCompound spell = new NbtCompound();
            spell.putString("id", entry.getKey().toString());
            spell.putInt("level", entry.getValue());
            spells.add(spell);
        }

        return spells;
    }

    public static Map<Identifier, Integer> getSpells(ItemStack itemStack) {
        return getSpells(itemStack.getNbt());
    }

    public static Map<Identifier, Integer> getSpells(NbtCompound tag) {
        if (tag == null) {
            tag = new NbtCompound();
            tag.put(SPELL_TAG, new NbtList());
        }
        NbtElement spellsTag = tag.get(SPELL_TAG);
        NbtList spells;
        if (spellsTag instanceof NbtList) {
            spells = (NbtList) spellsTag;
        } else {
            spells = new NbtList();
        }

        Map<Identifier, Integer> map = new HashMap<>();

        for (int i = 0; i < spells.size(); i++) {
            NbtCompound spell = spells.getCompound(i);

            Identifier id = new Identifier(spell.getString("id"));
            int level = spell.getInt("level");

            if (map.get(id) == null || map.get(id) < level) {
                map.put(id, level);
            }
        }

        return map;
    }

    public static Text getTranslatedSpell(Identifier id, int level) {
        Spell spell = SpellRegistry.getSpell(id, level);
        MutableText text;
        if (spell != null) {
            text = spell.getTranslation();
        } else {
            text = Spell.getDefaultTranslation(id, level);
        }
        text.formatted(Formatting.GRAY);
        return text;
    }

    public static Text getTranslatedSpellChat(Identifier id, int level) {
        return new LiteralText("[").append(SpellHelper.getTranslatedSpell(id, level).getString()).append("]").formatted(Formatting.GREEN);
    }

    public static void learnSpells(PlayerEntity player, Map<Identifier, Integer> itemSpells) {
        SpellPlayerEntity spellPlayer = (SpellPlayerEntity)player;
        World world = player.getWorld();

        Map<Identifier, Integer> playerSpells = spellPlayer.getDiscoveredSpells();

        boolean changed = false;

        for (Map.Entry<Identifier, Integer> entry : itemSpells.entrySet()) {
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
                    Text text = getTranslatedSpellChat(spell.getID(), spell.getLevel());
                    world.getServer().getPlayerManager().sendToAll(new GameMessageS2CPacket(new TranslatableText("chat." + SorceryCraft.MODID + ".discovered_spell", player.getDisplayName(), text), MessageType.CHAT, Util.NIL_UUID));
                }
            }
        }
        /*
        if (changed) {
            SorceryCraft.playSpellSound(player);
            spellPlayer.setDiscoveredSpells(playerSpells);
            SorceryCraft.DISCOVER_ALL_SPELLS_CRITERION.trigger((ServerPlayerEntity) player);
        }

         */
    }
}
