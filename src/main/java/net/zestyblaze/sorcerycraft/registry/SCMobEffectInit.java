package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.mob_effect.SCSimpleMobEffect;

public class SCMobEffectInit {
    public static final MobEffect INWARD = new SCSimpleMobEffect(MobEffectCategory.NEUTRAL, 0xFFA500);
    public static final MobEffect STEADFAST = new SCSimpleMobEffect(MobEffectCategory.NEUTRAL, 0x00FFFF);

    public static void registerMobEffects() {
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(SorceryCraft.MODID, "inward"), INWARD);
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(SorceryCraft.MODID, "steadfast"), STEADFAST);
    }
}
