package net.zestyblaze.sorcerycraft.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpellSoundUtil {
    private static final SoundEvent SPELL_SOUND_EFFECT = SoundEvents.ENCHANTMENT_TABLE_USE;

    public static void playSpellSound(Level world, BlockPos pos) {
        world.playSound(null, pos, SPELL_SOUND_EFFECT, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void playSpellSound(BlockSource block) {
        playSpellSound(block.getLevel(), block.getPos());
    }

    public static void playSpellSound(Player player) {
        player.playNotifySound(SPELL_SOUND_EFFECT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
}
