package net.zestyblaze.sorcerycraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.item.*;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;
import net.zestyblaze.sorcerycraft.registry.StatInit;
import net.zestyblaze.sorcerycraft.util.SoundUtil;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellItem extends Item {
    public SpellItem() {
        super(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(16).group(SorceryCraft.ITEM_GROUP));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, @NotNull Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);

        if (!world.isClient) {
            SoundUtil.playSpellSound(playerEntity);

            playerEntity.incrementStat(StatInit.CAST_SPELL_STAT);

            SpellEntity entity = new SpellEntity(world, playerEntity);
            entity.setItem(itemStack);
            entity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 1.5f, 1.0f);
            world.spawnEntity(entity);
        }

        if (!playerEntity.isCreative()) {
            itemStack.decrement(1);
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
    }

    @Override
    public ActionResult useOnEntity(@NotNull ItemStack stack, @NotNull PlayerEntity user, @NotNull LivingEntity entity, @NotNull Hand hand) {
        use(user.getWorld(), user, hand);
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasGlint(@NotNull ItemStack stack) {
        Map<Identifier, Integer> spells = SpellHelper.getSpells(stack);
        return spells.size() > 0 || super.hasGlint(stack);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack itemStack, World world, @NotNull List<Text> tooltip, @NotNull TooltipContext tooltipContext) {
        Map<Identifier, Integer> spells = SpellHelper.getSpells(itemStack);
        for (Map.Entry<Identifier, Integer> entry : spells.entrySet()) {
            tooltip.add(SpellHelper.getTranslatedSpell(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(isIn(group)) {
            stacks.add(new ItemStack(this));
            Spell[] spells = SpellRegistry.getSpells();
            for (Spell value : spells) {
                ItemStack item = new ItemStack(this);
                Map<Identifier, Integer> spell = new HashMap<>();
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
    public void inventoryTick(@NotNull ItemStack stack, @NotNull World world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClient() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            Map<Identifier, Integer> itemSpells = SpellHelper.getSpells(player.getInventory().getStack(slot));

            SpellHelper.learnSpells(player, itemSpells);
        }
    }
}
