package net.zestyblaze.sorcerycraft.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import org.jetbrains.annotations.NotNull;

public class CreateSpellCriterion extends AbstractCriterion<CreateSpellCriterion.Conditions> {
    private static final Identifier ID = new Identifier(SorceryCraft.MODID, "create_spell");

    public CreateSpellCriterion() {}

    @Override
    protected Conditions conditionsFromJson(@NotNull JsonObject json, EntityPredicate.@NotNull Extended player, @NotNull AdvancementEntityPredicateDeserializer context) {
        return new CreateSpellCriterion.Conditions(player);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        trigger(player, (conditions -> true));
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(EntityPredicate.Extended player) {
            super(ID, player);
        }
    }
}
