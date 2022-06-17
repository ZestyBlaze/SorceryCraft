package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;
import net.zestyblaze.sorcerycraft.util.SoundUtil;

public class BehaviourInit {
    public static void registerDispenserBehaviour() {
        DispenserBlock.registerBehavior(ItemInit.SPELL_ITEM, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World level, Position position, ItemStack stack) {
                SpellEntity entity = new SpellEntity(level, position.getX(), position.getY(), position.getZ());
                entity.setItem(stack);
                return entity;
            }

            @Override
            protected void playSound(BlockPointer source) {
                SoundUtil.playSpellSound(source);
            }
        });
    }
}
