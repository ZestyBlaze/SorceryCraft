package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.zestyblaze.sorcerycraft.api.util.SpellSoundUtil;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;

public class SCBehaviourInit {
    public static void registerDispenserBehaviour() {
        DispenserBlock.registerBehavior(SCItemInit.PROJECTILE_SPELL, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                SpellEntity entity = new SpellEntity(level, position.x(), position.y(), position.z());
                entity.setItem(stack);
                return entity;
            }

            @Override
            protected void playSound(BlockSource source) {
                SpellSoundUtil.playSpellSound(source.getLevel(), source.getPos());
            }
        });
    }
}
