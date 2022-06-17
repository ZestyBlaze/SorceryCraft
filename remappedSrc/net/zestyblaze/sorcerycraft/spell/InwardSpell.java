package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.api.Spell;

public class InwardSpell extends Spell {
    public InwardSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public int getXPCost() {
        return 16;
    }

    @Override
    public ItemStack getItemCost() {
        return new ItemStack(Items.ENDER_EYE);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
