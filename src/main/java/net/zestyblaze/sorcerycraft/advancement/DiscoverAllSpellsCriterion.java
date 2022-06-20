package net.zestyblaze.sorcerycraft.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DiscoverAllSpellsCriterion extends SimpleCriterionTrigger<DiscoverAllSpellsCriterion.Conditions> {
    private static final ResourceLocation ID = new ResourceLocation(SorceryCraft.MODID, "discover_all_spells");

    public DiscoverAllSpellsCriterion() {}

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Conditions createInstance(@NotNull JsonObject json, EntityPredicate.@NotNull Composite player, @NotNull DeserializationContext context) {
        return new Conditions(player);
    }

    public void trigger(ServerPlayer player) {
        trigger(player, (conditions -> {
            SpellPlayerEntity spellPlayer = (SpellPlayerEntity)player;
            Map<ResourceLocation, Integer> spells = spellPlayer.getDiscoveredSpells();
            Spell[] maxSpells = SpellRegistry.getMaxSpells();
            boolean match = true;
            for(Spell spell : maxSpells) {
                if (!spells.containsKey(spell.getID()) || spells.get(spell.getID()) < (spell.getLevel() - 1)) {
                    match = false;
                    break;
                }
            }
            return match;
        }));
    }

    public static class Conditions extends AbstractCriterionTriggerInstance {
        public Conditions(EntityPredicate.Composite player) {
            super(ID, player);
        }
    }
}
