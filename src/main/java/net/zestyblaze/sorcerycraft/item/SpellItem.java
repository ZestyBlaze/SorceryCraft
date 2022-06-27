package net.zestyblaze.sorcerycraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;
import net.zestyblaze.sorcerycraft.registry.SCStatInit;
import net.zestyblaze.sorcerycraft.util.SoundUtil;
import net.zestyblaze.sorcerycraft.util.SpellHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated(forRemoval = true)
public class SpellItem extends Item {
    public SpellItem() {
        super(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(16).group(SorceryCraft.ITEM_GROUP));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, @NotNull InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);

        if (!world.isClientSide) {
            SoundUtil.playSpellSound(playerEntity);

            playerEntity.awardStat(SCStatInit.CAST_SPELL_STAT);

            SpellEntity entity = new SpellEntity(world, playerEntity);
            entity.setItem(itemStack);
            entity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0f, 1.5f, 1.0f);
            world.addFreshEntity(entity);
        }

        if (!playerEntity.isCreative()) {
            itemStack.shrink(1);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
    }

    @Override
    public InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player user, @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
        use(user.getLevel(), user, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        Map<ResourceLocation, Integer> spells = SpellHelper.getSpells(stack);
        return spells.size() > 0 || super.isFoil(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipContext) {
        Map<ResourceLocation, Integer> spells = SpellHelper.getSpells(itemStack);
        for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
            tooltip.add(SpellHelper.getTranslatedSpell(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab group, @NotNull NonNullList<ItemStack> stacks) {
        if(allowedIn(group)) {
            stacks.add(new ItemStack(this));
            Spell[] spells = SpellRegistry.getSpells();
            for (Spell value : spells) {
                ItemStack item = new ItemStack(this);
                Map<ResourceLocation, Integer> spell = new HashMap<>();
                spell.put(value.getID(), value.getLevel());
                SpellHelper.setSpells(item, spell);
                stacks.add(item);
            }
        }
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClientSide() && entity instanceof Player player) {
            Map<ResourceLocation, Integer> itemSpells = SpellHelper.getSpells(player.getInventory().getItem(slot));

            SpellHelper.learnSpells(player, itemSpells);
        }
    }
}
