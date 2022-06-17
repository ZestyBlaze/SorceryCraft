package net.zestyblaze.sorcerycraft.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.util.spell.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DiscoverAllSpellsCriterion extends AbstractCriterion<DiscoverAllSpellsCriterion.Conditions> {
    private static final Identifier ID = new Identifier(SorceryCraft.MODID, "discover_all_spells");

    public DiscoverAllSpellsCriterion() {}

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    protected Conditions conditionsFromJson(@NotNull JsonObject json, EntityPredicate.@NotNull Extended player, @NotNull AdvancementEntityPredicateDeserializer context) {
        return new Conditions(player);
    }

    public void trigger(ServerPlayerEntity player) {
        trigger(player, (conditions -> {
            SpellPlayerEntity spellPlayer = (SpellPlayerEntity)player;
            Map<Identifier, Integer> spells = spellPlayer.getDiscoveredSpells();
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

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(EntityPredicate.Extended player) {
            super(ID, player);
        }
    }
}
