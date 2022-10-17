package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.zestyblaze.sorcerycraft.SorceryCraft;

public class SCSoundInit {
    public static final SoundEvent SPELL_DISCOVERED = new SoundEvent(new ResourceLocation(SorceryCraft.MODID, "item.spell.discovered"));
    public static final SoundEvent SPELL_USE = new SoundEvent(new ResourceLocation(SorceryCraft.MODID, "item.spell.cast"));
    public static final SoundEvent SPELL_FAIL = new SoundEvent(new ResourceLocation(SorceryCraft.MODID, "item.spell.fail"));
    public static final SoundEvent ORB_DISCOVERED = new SoundEvent(new ResourceLocation(SorceryCraft.MODID, "item.orb.discovered"));
    public static final SoundEvent ORB_USE = new SoundEvent(new ResourceLocation(SorceryCraft.MODID, "item.orb.use"));
    public static final SoundEvent ORB_FAIL = new SoundEvent(new ResourceLocation(SorceryCraft.MODID, "item.orb.fail"));

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation("item.spell.discovered"), SPELL_DISCOVERED);
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation("item.spell.cast"), SPELL_USE);
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation("item.spell.fail"), SPELL_FAIL);
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation("item.orb.discovered"), ORB_DISCOVERED);
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation("item.orb.use"), ORB_USE);
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation("item.orb.fail"), ORB_FAIL);
    }
}
