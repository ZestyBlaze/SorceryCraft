package net.zestyblaze.sorcerycraft.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.zestyblaze.sorcerycraft.api.util.SpellSoundUtil;
import net.zestyblaze.sorcerycraft.network.SpawnSmokeParticlesPacket;
import net.zestyblaze.sorcerycraft.recipe.RitualRecipe;
import net.zestyblaze.sorcerycraft.registry.SCBlockEntityInit;
import net.zestyblaze.sorcerycraft.registry.SCCriteriaInit;
import net.zestyblaze.sorcerycraft.registry.SCRecipeInit;
import net.zestyblaze.sorcerycraft.util.MultiblockUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HallowedIronBlockEntity extends BlockEntity implements Container {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);

    public HallowedIronBlockEntity(BlockPos pos, BlockState state) {
        super(SCBlockEntityInit.HALLOWED_IRON, pos, state);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void sync() {
        if(level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < getContainerSize(); i++) {
            if(getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(inventory, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(inventory, index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        inventory.set(index, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) < 16;
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    public void syncIron() {
        sync();
    }

    public void onUse(Level level, BlockPos pos, LivingEntity user) {
        SimpleContainer test = new SimpleContainer(getContainerSize());
        List<ItemEntity> items = level.getEntities(EntityType.ITEM, new AABB(pos).inflate(2, 1, 2), entity -> true);
        for(ItemEntity entity : items) {
            test.addItem(entity.getItem().copy().split(1));
        }
        RitualRecipe recipe = level.getRecipeManager().getAllRecipesFor(SCRecipeInit.RITUAL_RECIPE_TYPE).stream().filter(ritualRecipe -> ritualRecipe.matches(test, level)).findFirst().orElse(null);
        if(recipe != null && recipe.cost.size() == items.size()) {
            if(!((Player)user).isCreative() && ((Player)user).experienceLevel < recipe.xpCost) {
                Player player = (Player) user;
                player.displayClientMessage(Component.translatable("ritual.invalidXp"), true);
                return;
            }
            if(!((Player)user).isCreative()) {
                Player player = (Player) user;
                player.giveExperienceLevels(-recipe.xpCost);
            }

            for(int i = 0; i < items.size(); i++) {
                for(Player trackingPlayer : PlayerLookup.tracking(items.get(i))) {
                    SpawnSmokeParticlesPacket.send(trackingPlayer, items.get(i));
                }
                setItem(i, items.get(i).getItem().split(1));
            }
            if(!level.isClientSide()) {
                clearContent();
                SpellSoundUtil.playSpellSound(level, pos);
                Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, recipe.getResultItem().copy());

                //This is to make things a little easier for devs to not worry about the circle snuffing during testing
                if(!FabricLoader.getInstance().isDevelopmentEnvironment()) {
                    MultiblockUtil.executeFinish(level, pos);
                }

                SCCriteriaInit.CREATE_SPELL_CRITERION.trigger((ServerPlayer)user);
            }
            syncIron();
            return;
        }
        level.playSound(null, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 1, 1);
        if(user instanceof Player player) {
            player.displayClientMessage(Component.translatable("ritual.none"), true);
        }
    }
}
