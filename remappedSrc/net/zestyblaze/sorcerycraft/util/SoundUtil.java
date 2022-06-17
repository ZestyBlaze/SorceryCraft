package net.zestyblaze.sorcerycraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundUtil {
    private static final SoundEvent SPELL_SOUND_EFFECT = SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE;

    public static void playSpellSound(World world, BlockPos pos) {
        world.playSound(null, pos, SPELL_SOUND_EFFECT, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public static void playSpellSound(BlockPointer block) {
        playSpellSound(block.getWorld(), block.getPos());
    }

    public static void playSpellSound(PlayerEntity player) {
        player.playSound(SPELL_SOUND_EFFECT, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
}
