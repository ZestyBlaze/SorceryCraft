package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.api.Spell;

public class TeleportSpell extends Spell {
    public TeleportSpell(ResourceLocation id, int level) {
        super(id, level);
    }

    private int getMaxTeleport(Level world) {
        return world.dimension() != Level.OVERWORLD ? 128 : 256;
    }

    @Override
    public void execute(Level world, Entity source, Entity attacker, Entity target) {
        int range = 16 + (8 * getLevel());
        if (target instanceof LivingEntity user) {
            if (!world.isClientSide) {
                double d = user.getX();
                double e = user.getY();
                double f = user.getZ();

                for (int i = 0; i < range; ++i) {
                    double x = user.getX() + (user.getRandom().nextDouble() - 0.5D) * range;
                    double y = Mth.clamp(user.getY() + (double) (user.getRandom().nextInt(range) - (range / 2)), 0d, getMaxTeleport(world) - 1);
                    double z = user.getZ() + (user.getRandom().nextDouble() - 0.5D) * range;
                    if (user.isPassenger()) {
                        user.stopRiding();
                    }

                    if (user.randomTeleport(x, y, z, true)) {
                        world.playSound(null, d, e, f, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        user.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
