package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.spell.*;

public class SpellsInit {
    public static Identifier COOLING_SPELL;
    public static Identifier DAMAGE_SPELL;
    public static Identifier DISSOLVE_SPELL;
    public static Identifier FLAME_SPELL;
    public static Identifier HEAL_SPELL;
    public static Identifier INWARD_SPELL;
    public static Identifier LEVITATE_SPELL;
    public static Identifier LIGHTNING_SPELL;
    public static Identifier STEADFAST_SPELL;
    public static Identifier TELEPORT_SPELL;

    public static void registerSpells() {
        COOLING_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "cooling_spell"), CoolingSpell.class);
        DAMAGE_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "damage_spell"), DamageSpell.class);
        DISSOLVE_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "dissolve_spell"), DissolveSpell.class);
        FLAME_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "flame_spell"), FlameSpell.class);
        HEAL_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "heal_spell"), HealSpell.class);
        INWARD_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "inward_spell"), InwardSpell.class);
        LEVITATE_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "levitate_spell"), LevitateSpell.class);
        LIGHTNING_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "lightning_spell"), LightningSpell.class);
        STEADFAST_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "steadfast_spell"), SteadfastSpell.class);
        TELEPORT_SPELL = SpellRegistry.register(new Identifier(SorceryCraft.MODID, "teleport_spell"), TeleportSpell.class);
    }
}
