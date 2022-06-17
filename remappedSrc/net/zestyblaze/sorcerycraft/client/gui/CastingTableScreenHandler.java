package net.zestyblaze.sorcerycraft.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.inventory.*;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.config.ModConfig;
import net.zestyblaze.sorcerycraft.registry.BlockInit;
import net.zestyblaze.sorcerycraft.registry.CriteriaInit;
import net.zestyblaze.sorcerycraft.registry.ItemInit;
import net.zestyblaze.sorcerycraft.util.SoundUtil;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import net.zestyblaze.sorcerycraft.util.spell.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CastingTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final Inventory result;
    private Spell[] spells = new Spell[0];
    private final ScreenHandlerContext context;
    private int index = 0;

    public CastingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext blockContext) {
        super(ScreenHandlerType.STONECUTTER, syncId);

        inventory = new SimpleInventory(2) {
            @Override
            public void markDirty() {
                super.markDirty();
                onContentChanged(this);
            }
        };
        context = blockContext;
        result = new CraftingResultInventory();

        setSpells(playerInventory.player);

        addSlot(new Slot(inventory, 0, 136, 37) {
            @Override
            public boolean canInsert(@NotNull ItemStack stack) {
                return stack.getItem().equals(ItemInit.SPELL_ITEM);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void onQuickTransfer(@NotNull ItemStack originalItem, @NotNull ItemStack itemStack) {
                super.onQuickTransfer(originalItem, itemStack);
                markDirty();
            }
        });

        addSlot(new Slot(inventory, 1, 162, 37) {
            @Override
            public void onQuickTransfer(@NotNull ItemStack originalItem, @NotNull ItemStack itemStack) {
                super.onQuickTransfer(originalItem, itemStack);
                markDirty();
            }
        });

        addSlot(new Slot(result, 2, 220, 37) {
            @Override
            public boolean canInsert(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(@NotNull PlayerEntity playerEntity) {
                return canTakeResult(playerEntity) && hasStack();
            }

            @Override
            public void onTakeItem(@NotNull PlayerEntity player, @NotNull ItemStack stack) {
                if (!player.isCreative()) {
                    player.addExperienceLevels(-spells[id].getXPCost());
                }

                context.run((world, blockPos) -> {
                    SoundUtil.playSpellSound(world, blockPos);
                    if (!world.isClient) {
                        CriteriaInit.CREATE_SPELL_CRITERION.trigger((ServerPlayerEntity) player);
                    }
                });

                inventory.setStack(0, ItemStack.EMPTY);
                inventory.removeStack(1, spells[id].getItemCost().getCount());
            }
        });

        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + k * 9 + 9, 108 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 108 + k * 18, 142));
        }
    }

    private void resetIndex() {
        index = 0;
        onContentChanged(inventory);
    }

    public void setSpells(PlayerEntity player) {
        if (player.isCreative() ? ModConfig.get().limitCraftingTable.creative : ModConfig.get().limitCraftingTable.survival) {
            SpellPlayerEntity spellPlayer = (SpellPlayerEntity) player;
            Map<Identifier, Integer> spellsMap = spellPlayer.getDiscoveredSpells();
            List<Spell> spellsArray = new ArrayList<>();

            Spell[] allSpells = SpellRegistry.getSpells();
            for (Spell spell : allSpells) {
                if (spellsMap.containsKey(spell.getID()) && spellsMap.get(spell.getID()) >= spell.getLevel()) {
                    spellsArray.add(spell);
                }
            }
            spells = spellsArray.toArray(new Spell[0]);
        } else {
            spells = SpellRegistry.getSpells();
        }
        resetIndex();
    }

    public boolean canTakeResult(PlayerEntity playerEntity) {
        return playerEntity.isCreative() || playerEntity.experienceLevel >= spells[index].getXPCost();
    }
    
    public void setIndex(int index) {
        this.index = index;
        onContentChanged(inventory);

        if (inventory.getStack(0).isEmpty() && inventory.getStack(1).isEmpty()) {
            ItemStack spellItem = new ItemStack(ItemInit.SPELL_ITEM);
            autoFill(0, spellItem, true);
            ItemStack paymentItem = getRecipes()[index].getItemCost();
            autoFill(1, paymentItem, false);
        }
    }

    @Override
    public ItemStack transferSlot(@NotNull PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot == 2) {
                if (!insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (invSlot != 0 && invSlot != 1) {
                if (invSlot < 30) {
                    if (!insertItem(itemStack2, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (invSlot < 39 && !insertItem(itemStack2, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!insertItem(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public void close(@NotNull PlayerEntity player) {
        super.close(player);
        context.run((world, blockPos) -> dropInventory(player, inventory));
    }

    @Override
    public void onContentChanged(@NotNull Inventory inventory) {
        super.onContentChanged(inventory);
        ItemStack item = inventory.getStack(0);
        ItemStack cost = inventory.getStack(1);
        if (inventory == this.inventory) {
            if (spells.length > 0 &&
                    !item.isEmpty() &&
                    cost.getItem() == spells[index].getItemCost().getItem() &&
                    cost.getCount() >= spells[index].getItemCost().getCount()) {
                ItemStack resultItem = item.copy();
                Map<Identifier, Integer> resultSpells = SpellHelper.getSpells(resultItem);
                if (!resultSpells.containsKey(spells[index].getID()) || resultSpells.get(spells[index].getID()) <= spells[index].getLevel()) {
                    resultSpells.put(spells[index].getID(), spells[index].getLevel());
                }
                SpellHelper.setSpells(resultItem, resultSpells);
                result.setStack(2, resultItem);
            } else {
                result.setStack(2, ItemStack.EMPTY);
            }
        }
    }

    private void autoFill(int slot, ItemStack stack, boolean onlyOne) {
        if (!stack.isEmpty()) {
            for (int i = 3; i < 39; ++i) {
                ItemStack itemStack = slots.get(i).getStack();
                if (!itemStack.isEmpty() && itemCompatible(stack, itemStack)) {
                    ItemStack invSlot = inventory.getStack(slot);
                    int count = invSlot.isEmpty() ? 0 : invSlot.getCount();
                    int requiredCount = Math.min((onlyOne ? 1 : stack.getMaxCount()) - count, itemStack.getCount());
                    ItemStack modifiedItem = itemStack.copy();
                    int totalCount = count + requiredCount;
                    itemStack.decrement(requiredCount);
                    modifiedItem.setCount(totalCount);
                    inventory.setStack(slot, modifiedItem);
                    if (totalCount >= stack.getMaxCount() || onlyOne) {
                        break;
                    }
                }
            }
        }
    }

    private boolean itemCompatible(ItemStack itemStack, ItemStack otherItemStack) {
        return itemStack.getItem() == otherItemStack.getItem();
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return context.get((world, blockPos) -> world.getBlockState(blockPos).getBlock().equals(BlockInit.CASTING_TABLE_BLOCK) && player.squaredDistanceTo((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    public Spell[] getRecipes() {
        return spells;
    }
}
