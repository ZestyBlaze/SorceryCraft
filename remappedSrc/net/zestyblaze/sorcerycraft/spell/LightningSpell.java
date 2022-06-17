package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class LightningSpell extends Spell {
    public LightningSpell(Identifier id, int level) {
        super(id, level);
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, Entity target) {
        strike(world, target.getPos(), attacker);
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, BlockHitResult hitResult) {
        strike(world, hitResult.getPos(), attacker);
    }

    private void strike(World world, Vec3d pos, Entity attacker) {
        ServerWorld serverLevel = (ServerWorld) world;
        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
        assert lightningEntity != null;
        lightningEntity.updatePosition(pos.x, pos.y, pos.z);
        if (attacker instanceof ServerPlayerEntity) {
            lightningEntity.setChanneler((ServerPlayerEntity) attacker);
        }
        serverLevel.spawnEntity(lightningEntity);
    }

    @Override
    public int getXPCost() {
        return 18;
    }

    @Override
    public ItemStack getItemCost() {
        return new ItemStack(Items.END_ROD);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
