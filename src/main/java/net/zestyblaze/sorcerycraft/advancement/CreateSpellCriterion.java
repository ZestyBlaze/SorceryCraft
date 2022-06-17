package net.zestyblaze.sorcerycraft.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import org.jetbrains.annotations.NotNull;

public class CreateSpellCriterion extends SimpleCriterionTrigger<CreateSpellCriterion.Conditions> {
    private static final ResourceLocation ID = new ResourceLocation(SorceryCraft.MODID, "create_spell");

    public CreateSpellCriterion() {}

    @Override
    protected Conditions createInstance(@NotNull JsonObject json, EntityPredicate.@NotNull Composite player, @NotNull DeserializationContext context) {
        return new CreateSpellCriterion.Conditions(player);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, (conditions -> true));
    }

    public static class Conditions extends AbstractCriterionTriggerInstance {
        public Conditions(EntityPredicate.Composite player) {
            super(ID, player);
        }
    }
}
