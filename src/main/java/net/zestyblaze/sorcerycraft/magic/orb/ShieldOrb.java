package net.zestyblaze.sorcerycraft.magic.orb;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.zestyblaze.sorcerycraft.api.orb.Orb;
import net.zestyblaze.sorcerycraft.block.ShieldBlock;
import net.zestyblaze.sorcerycraft.registry.SCBlockInit;

public class ShieldOrb extends Orb {
    public ShieldOrb(ResourceLocation id, int level) {
        super(id, level);
    }

    @Override
    public void castOrb(Level level, Entity caster) {
        for(int x = -1; x < 5; x++) {
            for(int z = -1; z < 5; z++) {
                for(int y = 1; y < 5; y++) {
                    BlockPos pos = caster.getOnPos().offset(new BlockPos(x, y, z));
                    BlockState state = level.getBlockState(pos);
                    if(isBlockReplaceable(state) && !((x == -1 && z == -1) || (x == -1 && z == 4) || (x == 4 && z == -1) || (x == 4 && z == 4)
                            || (y == 4 && x == -1) || (y == 4 && x == 4) || (y == 4 && z == -1) || (y == 4 && z == 4)) &&
                            (x == -1 || x == 4 || y == 4 || z == -1 || z == 4)) {
                        level.removeBlock(pos, true);
                        level.setBlock(pos, SCBlockInit.SHIELD_BLOCK.defaultBlockState(), 2);
                        ShieldBlock.setUUID(caster.getUUID());
                    }
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    private static boolean isBlockReplaceable(BlockState blockState) {
        Material m = blockState.getMaterial();
        return blockState.isAir() || m.equals(Material.REPLACEABLE_PLANT) || m.equals(Material.PLANT) || m.equals(Material.TOP_SNOW);
    }
}
