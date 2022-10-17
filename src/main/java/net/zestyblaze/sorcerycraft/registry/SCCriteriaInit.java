package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.advancements.CriteriaTriggers;
import net.zestyblaze.sorcerycraft.advancement.CreateSpellCriterion;
import net.zestyblaze.sorcerycraft.advancement.DiscoverAllOrbsCriterion;
import net.zestyblaze.sorcerycraft.advancement.DiscoverAllSpellsCriterion;

public class SCCriteriaInit {
    public static CreateSpellCriterion CREATE_SPELL_CRITERION;
    public static DiscoverAllSpellsCriterion DISCOVER_ALL_SPELLS_CRITERION;
    public static DiscoverAllOrbsCriterion DISCOVER_ALL_ORBS_CRITERION;

    public static void registerCriteria() {
        CREATE_SPELL_CRITERION = CriteriaTriggers.register(new CreateSpellCriterion());
        DISCOVER_ALL_SPELLS_CRITERION = CriteriaTriggers.register(new DiscoverAllSpellsCriterion());
        DISCOVER_ALL_ORBS_CRITERION = CriteriaTriggers.register(new DiscoverAllOrbsCriterion());
    }
}
