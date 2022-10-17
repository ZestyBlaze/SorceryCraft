package net.zestyblaze.sorcerycraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.orb.Orb;
import net.zestyblaze.sorcerycraft.api.registry.OrbRegistry;
import net.zestyblaze.sorcerycraft.api.util.MagicHelper;
import net.zestyblaze.sorcerycraft.api.util.SpellSoundUtil;
import net.zestyblaze.sorcerycraft.registry.SCStatInit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class OrbItem extends Item {
    public OrbItem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(SorceryCraft.ITEM_GROUP));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide()) {
            SpellSoundUtil.playOrbSound(level, player.blockPosition());
            player.awardStat(SCStatInit.CAST_ORB_STAT);
            Map<ResourceLocation, Integer> spells = MagicHelper.getOrbs(stack);
            if(MagicHelper.didOrbSucceed(player)) {
                for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
                    Orb orb = OrbRegistry.getOrb(entry);
                    if(orb != null) {
                        orb.castOrb(level, player);
                    }
                }
            } else {
                SpellSoundUtil.playOrbFailSound(level, player.blockPosition());
                player.hurt(DamageSource.MAGIC, 6f);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab group, @NotNull NonNullList<ItemStack> stacks) {
        if(allowedIn(group)) {
            Orb[] orbs = OrbRegistry.getOrbs();
            for (Orb value : orbs) {
                ItemStack item = new ItemStack(this);
                Map<ResourceLocation, Integer> orb = new HashMap<>();
                orb.put(value.getId(), value.getLevel());
                MagicHelper.setOrbs(item, orb);
                stacks.add(item);
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClientSide() && entity instanceof Player player) {
            Map<ResourceLocation, Integer> itemOrbs = MagicHelper.getOrbs(player.getInventory().getItem(slot));

            MagicHelper.learnOrbs(player, itemOrbs);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        Map<ResourceLocation, Integer> orbs = MagicHelper.getOrbs(stack);
        Component name = null;
        for (Map.Entry<ResourceLocation, Integer> entry : orbs.entrySet()) {
            name = MagicHelper.getTranslatedOrb(entry.getKey(), entry.getValue());
        }
        if(name != null) {
            return name;
        }
        return Component.translatable("item.sorcerycraft.emptyOrb");
    }
}
