package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.advancements.CriteriaTriggers;
import net.zestyblaze.sorcerycraft.advancement.CreateSpellCriterion;
import net.zestyblaze.sorcerycraft.advancement.DiscoverAllSpellsCriterion;

public class SCCriteriaInit {
    public static DiscoverAllSpellsCriterion DISCOVER_ALL_SPELLS_CRITERION;
    public static CreateSpellCriterion CREATE_SPELL_CRITERION;

    public static void registerCriteria() {
        DISCOVER_ALL_SPELLS_CRITERION = CriteriaTriggers.register(new DiscoverAllSpellsCriterion());
        CREATE_SPELL_CRITERION = CriteriaTriggers.register(new CreateSpellCriterion());
    }
}
