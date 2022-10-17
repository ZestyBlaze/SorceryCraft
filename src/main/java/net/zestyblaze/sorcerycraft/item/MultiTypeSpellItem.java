package net.zestyblaze.sorcerycraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;
import net.zestyblaze.sorcerycraft.api.util.MagicHelper;
import net.zestyblaze.sorcerycraft.api.util.SpellSoundUtil;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;
import net.zestyblaze.sorcerycraft.registry.SCStatInit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultiTypeSpellItem extends Item {
    public MultiTypeSpellItem() {
        super(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(16).group(SorceryCraft.ITEM_GROUP));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, @NotNull InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        if(playerEntity.isCrouching()) {
            if (!world.isClientSide()) {
                SpellSoundUtil.playSpellSound(world, playerEntity.blockPosition());
                playerEntity.awardStat(SCStatInit.CAST_SPELL_STAT);
                Map<ResourceLocation, Integer> spells = MagicHelper.getSpells(itemStack);
                if (MagicHelper.didSpellSucceed(playerEntity)) {
                    for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
                        Spell spell = SpellRegistry.getSpell(entry);
                        if (spell != null) {
                            spell.execute(world, itemStack, playerEntity);
                        }
                    }
                } else {
                    SpellSoundUtil.playSpellFailSound(world, playerEntity.blockPosition());
                    playerEntity.hurt(DamageSource.MAGIC, 4f);
                }
            }
        } else {
            if (!world.isClientSide) {
                SpellSoundUtil.playSpellSound(playerEntity.getLevel(), playerEntity.blockPosition());
                playerEntity.awardStat(SCStatInit.CAST_SPELL_STAT);
                SpellEntity entity = new SpellEntity(world, playerEntity);
                entity.setItem(itemStack);
                entity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0f, 1.5f, 1.0f);
                world.addFreshEntity(entity);
            }
        }
        if (!playerEntity.isCreative()) {
            itemStack.shrink(1);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipContext) {
        Map<ResourceLocation, Integer> spells = MagicHelper.getSpells(itemStack);
        for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
            tooltip.add(Component.translatable("spell.type").withStyle(ChatFormatting.GRAY).append(Objects.requireNonNull(MagicHelper.getTranslatedSpellType(entry.getKey(), entry.getValue()))));
        }
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab group, @NotNull NonNullList<ItemStack> stacks) {
        if(allowedIn(group)) {
            Spell[] spells = SpellRegistry.getSpells();
            for (Spell value : spells) {
                if(value.getSpellType() == SpellType.BOTH) {
                    ItemStack item = new ItemStack(this);
                    Map<ResourceLocation, Integer> spell = new HashMap<>();
                    spell.put(value.getID(), value.getLevel());
                    MagicHelper.setSpells(item, spell);
                    stacks.add(item);
                }
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
            Map<ResourceLocation, Integer> itemSpells = MagicHelper.getSpells(player.getInventory().getItem(slot));

            MagicHelper.learnSpells(player, itemSpells);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        Map<ResourceLocation, Integer> spells = MagicHelper.getSpells(stack);
        Component name = null;
        for (Map.Entry<ResourceLocation, Integer> entry : spells.entrySet()) {
            name = MagicHelper.getTranslatedSpell(entry.getKey(), entry.getValue());
        }
        if(name != null) {
            return name;
        }
        return Component.translatable("item.sorcerycraft.emptySpell");
    }
}
