package net.zestyblaze.sorcerycraft.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("ALL")
public class ShieldBlock extends Block {
    private static UUID CASTER_UUID = null;
    private int timer;

    public ShieldBlock() {
        super(FabricBlockSettings.of(Material.BARRIER)
                .strength(-1.0F, 3600000.8F)
                .noOcclusion()
                .isViewBlocking((blockState, blockGetter, blockPos) -> false)
                .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
                .isSuffocating((state, getter, pos) -> false)
                .isValidSpawn((state, getter, pos, type) -> false));
    }

    public static void setUUID(UUID owner) {
        CASTER_UUID = owner;
    }

    public static UUID getUUID() {
        return CASTER_UUID;
    }

    public static void removeUUID(BlockState state) {
        CASTER_UUID = null;
    }


    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if(!level.isClientSide() && livingEntity instanceof Player player) {
            timer = 0;
            setUUID(player.getUUID());
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if(collisionContext instanceof EntityCollisionContext content) {
            Entity entity = content.getEntity();
            if(entity != null && entity.getUUID() != null && entity.getUUID() == getUUID()) {
                return Shapes.empty();
            }
        }
        return Shapes.block();
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if(!serverLevel.isClientSide()) {
            SorceryCraft.LOG.info("1");
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 1.0F;
    }
}
