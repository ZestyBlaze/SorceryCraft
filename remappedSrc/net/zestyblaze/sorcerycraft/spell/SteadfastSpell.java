package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.api.Spell;

public class SteadfastSpell extends Spell {
    public SteadfastSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public int getXPCost() {
        return 18;
    }

    @Override
    public ItemStack getItemCost() {
        return new ItemStack(Items.NETHERITE_INGOT);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
