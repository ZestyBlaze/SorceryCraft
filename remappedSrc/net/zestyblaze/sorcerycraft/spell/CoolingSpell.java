package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class CoolingSpell extends Spell {
    public CoolingSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, Entity target) {
        target.setOnFireFor(0);
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, BlockHitResult hitResult) {
        BlockPos blockPos = hitResult.getBlockPos();
        if (world.getBlockState(blockPos).isIn(BlockTags.FIRE)) {
            world.removeBlock(blockPos, false);
        }
        if (world.getBlockState(blockPos).get(Properties.LIT) && world.getBlockState(blockPos).get(Properties.LIT)) {
            world.setBlockState(blockPos, world.getBlockState(blockPos).with(Properties.LIT, false), 2);
        }

        BlockPos sideBlockPos = blockPos.offset(hitResult.getSide());
        if (world.getBlockState(sideBlockPos).isIn(BlockTags.FIRE)) {
            world.removeBlock(sideBlockPos, false);
        }
    }

    @Override
    public int getXPCost() {
        return 10;
    }

    @Override
    public ItemStack getItemCost() {
        return new ItemStack(Items.ICE);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
