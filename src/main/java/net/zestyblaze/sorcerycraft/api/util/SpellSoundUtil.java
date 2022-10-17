package net.zestyblaze.sorcerycraft.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.registry.SCSoundInit;

public class SpellSoundUtil {
    private static final SoundEvent SPELL_SOUND = SCSoundInit.SPELL_USE;
    private static final SoundEvent SPELL_DISCOVER_SOUND = SCSoundInit.SPELL_DISCOVERED;
    private static final SoundEvent SPELL_FAIL_SOUND = SCSoundInit.SPELL_FAIL;
    private static final SoundEvent ORB_SOUND = SCSoundInit.ORB_USE;
    private static final SoundEvent ORB_DISCOVER_SOUND = SCSoundInit.ORB_DISCOVERED;
    private static final SoundEvent ORB_FAIL_SOUND = SCSoundInit.ORB_FAIL;

    public static void playSpellSound(Level world, BlockPos pos) {
        world.playSound(null, pos, SPELL_SOUND, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void playSpellDiscoverSound(Level world, BlockPos pos) {
        world.playSound(null, pos, SPELL_DISCOVER_SOUND, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void playSpellFailSound(Level world, BlockPos pos) {
        world.playSound(null, pos, SPELL_FAIL_SOUND, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void playOrbSound(Level world, BlockPos pos) {
        world.playSound(null, pos, ORB_SOUND, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void playOrbDiscoverSound(Level world, BlockPos pos) {
        world.playSound(null, pos, ORB_DISCOVER_SOUND, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void playOrbFailSound(Level world, BlockPos pos) {
        world.playSound(null, pos, ORB_FAIL_SOUND, SoundSource.BLOCKS, 1.0f, 1.0f);
    }
}
