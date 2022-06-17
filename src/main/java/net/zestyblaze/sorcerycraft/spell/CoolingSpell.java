package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.zestyblaze.sorcerycraft.api.Spell;

public class CoolingSpell extends Spell {
    public CoolingSpell(ResourceLocation id, int level) {
        super(id, level);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        target.setSecondsOnFire(0);
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, BlockHitResult hitResult) {
        BlockPos blockPos = hitResult.getBlockPos();
        if (world.getBlockState(blockPos).is(BlockTags.FIRE)) {
            world.removeBlock(blockPos, false);
        }
        if (world.getBlockState(blockPos).hasProperty(BlockStateProperties.LIT) && world.getBlockState(blockPos).getValue(BlockStateProperties.LIT)) {
            world.setBlock(blockPos, world.getBlockState(blockPos).setValue(BlockStateProperties.LIT, false), 2);
        }

        BlockPos sideBlockPos = blockPos.relative(hitResult.getDirection());
        if (world.getBlockState(sideBlockPos).is(BlockTags.FIRE)) {
            world.removeBlock(sideBlockPos, false);
        }
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
