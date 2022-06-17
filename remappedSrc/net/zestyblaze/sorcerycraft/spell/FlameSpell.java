package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class FlameSpell extends Spell {
    public FlameSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, Entity target) {
        if (attacker instanceof LivingEntity) {
            ((LivingEntity) attacker).onAttacking(target);
        }
        target.setOnFireFor(8 + (getLevel() * 4));
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, BlockHitResult hitResult) {
        Items.FLINT_AND_STEEL.useOnBlock(new AutomaticItemPlacementContext(world, hitResult.getBlockPos(), hitResult.getSide().getOpposite(), new ItemStack(Items.FLINT_AND_STEEL), hitResult.getSide()));
    }

    @Override
    public int getXPCost() {
        switch (getLevel()) {
            case 0 -> {
                return 8;
            }
            case 1 -> {
                return 12;
            }
        }
        return -1;
    }

    @Override
    public ItemStack getItemCost() {
        switch (getLevel()) {
            case 0 -> {
                return new ItemStack(Items.FLINT_AND_STEEL);
            }
            case 1 -> {
                return new ItemStack(Items.FIRE_CHARGE);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
