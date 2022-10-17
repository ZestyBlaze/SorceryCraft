package net.zestyblaze.sorcerycraft.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.orb.Orb;
import net.zestyblaze.sorcerycraft.api.registry.OrbRegistry;
import net.zestyblaze.sorcerycraft.util.OrbPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DiscoverAllOrbsCriterion extends SimpleCriterionTrigger<DiscoverAllOrbsCriterion.Conditions> {
    private static final ResourceLocation ID = new ResourceLocation(SorceryCraft.MODID, "discover_all_orbs");

    public DiscoverAllOrbsCriterion() {}

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected DiscoverAllOrbsCriterion.Conditions createInstance(@NotNull JsonObject json, EntityPredicate.@NotNull Composite player, @NotNull DeserializationContext context) {
        return new DiscoverAllOrbsCriterion.Conditions(player);
    }

    public void trigger(ServerPlayer player) {
        trigger(player, (conditions -> {
            OrbPlayerEntity orbPlayer = (OrbPlayerEntity)player;
            Map<ResourceLocation, Integer> orbs = orbPlayer.getDiscoveredOrbs();
            Orb[] maxOrbs = OrbRegistry.getMaxOrbs();
            boolean match = true;
            for(Orb orb : maxOrbs) {
                if (!orbs.containsKey(orb.getId()) || orbs.get(orb.getId()) < (orb.getLevel() - 1)) {
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
