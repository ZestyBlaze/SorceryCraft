package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.advancement.criterion.Criteria;
import net.zestyblaze.sorcerycraft.advancement.CreateSpellCriterion;
import net.zestyblaze.sorcerycraft.advancement.DiscoverAllSpellsCriterion;

public class CriteriaInit {
    public static DiscoverAllSpellsCriterion DISCOVER_ALL_SPELLS_CRITERION;
    public static CreateSpellCriterion CREATE_SPELL_CRITERION;

    public static void registerCriteria() {
        DISCOVER_ALL_SPELLS_CRITERION = Criteria.register(new DiscoverAllSpellsCriterion());
        CREATE_SPELL_CRITERION = Criteria.register(new CreateSpellCriterion());
    }
}
