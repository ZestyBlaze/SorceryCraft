package net.zestyblaze.sorcerycraft.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.api.Spell;

public class TeleportSpell extends Spell {
    public TeleportSpell(Identifier id, int level) {
        super(id, level);
    }

    private int getMaxTeleport(World world) {
        return world.getRegistryKey() != World.OVERWORLD ? 128 : 256;
    }

    @Override
    public void execute(World world, Entity source, Entity attacker, Entity target) {
        int range = 16 + (8 * getLevel());
        if (target instanceof LivingEntity user) {
            if (!world.isClient) {
                double d = user.getX();
                double e = user.getY();
                double f = user.getZ();

                for (int i = 0; i < range; ++i) {
                    double x = user.getX() + (user.getRandom().nextDouble() - 0.5D) * range;
                    double y = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt(range) - (range / 2)), 0d, getMaxTeleport(world) - 1);
                    double z = user.getZ() + (user.getRandom().nextDouble() - 0.5D) * range;
                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }

                    if (user.teleport(x, y, z, true)) {
                        world.playSound(null, d, e, f, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        user.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getXPCost() {
        switch (getLevel()) {
            case 0 -> {
                return 12;
            }
            case 1 -> {
                return 18;
            }
            case 2 -> {
                return 24;
            }
        }
        return -1;
    }

    @Override
    public ItemStack getItemCost() {
        switch (getLevel()) {
            case 0 -> {
                return new ItemStack(Items.ENDER_PEARL);
            }
            case 1 -> {
                return new ItemStack(Items.CHORUS_FRUIT);
            }
            case 2 -> {
                return new ItemStack(Items.CHORUS_FLOWER);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
