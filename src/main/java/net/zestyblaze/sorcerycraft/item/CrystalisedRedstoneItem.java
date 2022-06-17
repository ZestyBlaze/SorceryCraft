package net.zestyblaze.sorcerycraft.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zestyblaze.sorcerycraft.registry.SCBlockInit;

import java.util.Objects;

public class CrystalisedRedstoneItem extends Item {
    public CrystalisedRedstoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        if(!level.isClientSide()) {
            if(state.getBlock() == SCBlockInit.DRAINED_CRYSTALISED_REDSTONE_BLOCK) {
                if(!Objects.requireNonNull(context.getPlayer()).isCreative()) {
                    context.getItemInHand().shrink(1);
                }
                level.setBlockAndUpdate(pos, SCBlockInit.CRYSTALISED_REDSTONE_BLOCK.defaultBlockState());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
