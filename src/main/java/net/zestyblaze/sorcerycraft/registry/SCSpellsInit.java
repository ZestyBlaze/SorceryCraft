package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.resources.ResourceLocation;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.spell.*;

public class SCSpellsInit {
    public static ResourceLocation CLEANSE_SPELL;
    public static ResourceLocation COOLING_SPELL;
    public static ResourceLocation DAMAGE_SPELL;
    public static ResourceLocation FLAME_SPELL;
    public static ResourceLocation HEAL_SPELL;
    public static ResourceLocation INWARD_SPELL;
    public static ResourceLocation LEVITATE_SPELL;
    public static ResourceLocation LIGHTNING_SPELL;
    public static ResourceLocation STEADFAST_SPELL;
    public static ResourceLocation TELEPORT_SPELL;

    public static void registerSpells() {
        CLEANSE_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "cleanse_spell"), CleanseSpell.class);
        COOLING_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "cooling_spell"), CoolingSpell.class);
        DAMAGE_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "damage_spell"), DamageSpell.class);
        FLAME_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "flame_spell"), FlameSpell.class);
        HEAL_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "heal_spell"), HealSpell.class);
        INWARD_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "inward_spell"), InwardSpell.class);
        LEVITATE_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "levitate_spell"), LevitateSpell.class);
        LIGHTNING_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "lightning_spell"), LightningSpell.class);
        STEADFAST_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "steadfast_spell"), SteadfastSpell.class);
        TELEPORT_SPELL = SpellRegistry.register(new ResourceLocation(SorceryCraft.MODID, "teleport_spell"), TeleportSpell.class);
    }
}
