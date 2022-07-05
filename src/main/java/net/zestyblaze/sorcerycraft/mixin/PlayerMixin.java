package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.zestyblaze.sorcerycraft.api.util.SpellHelper;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public class PlayerMixin implements SpellPlayerEntity {
    @Shadow public AbstractContainerMenu containerMenu;
    @Unique private Map<ResourceLocation, Integer> discoveredSpells = new HashMap<>();

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readSpells(CompoundTag compound, CallbackInfo ci) {
        discoveredSpells = SpellHelper.getSpells(compound);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void writeSpells(@NotNull CompoundTag compound, CallbackInfo ci) {
        compound.put(SpellHelper.SPELL_TAG, SpellHelper.createSpellsTag(discoveredSpells));
    }

    @Override
    public void setDiscoveredSpells(Map<ResourceLocation, Integer> spells) {
        discoveredSpells = spells;
    }

    @Override
    public Map<ResourceLocation, Integer> getDiscoveredSpells() {
        return discoveredSpells;
    }
}
