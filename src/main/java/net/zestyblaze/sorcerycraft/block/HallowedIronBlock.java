package net.zestyblaze.sorcerycraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.zestyblaze.sorcerycraft.block.entity.HallowedIronBlockEntity;
import net.zestyblaze.sorcerycraft.util.MultiblockUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class HallowedIronBlock extends Block implements EntityBlock {
    public HallowedIronBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HallowedIronBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!level.isClientSide() && player.getItemInHand(hand) == ItemStack.EMPTY) {
            if(MultiblockUtil.checkStructure(level, pos)) {
                ((HallowedIronBlockEntity)Objects.requireNonNull(level.getBlockEntity(pos))).onUse(level, pos, player);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
