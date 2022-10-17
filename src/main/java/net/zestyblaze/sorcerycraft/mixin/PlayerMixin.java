package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.zestyblaze.sorcerycraft.api.util.MagicHelper;
import net.zestyblaze.sorcerycraft.util.OrbPlayerEntity;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public class PlayerMixin implements SpellPlayerEntity, OrbPlayerEntity {
    @Unique private Map<ResourceLocation, Integer> discoveredSpells = new HashMap<>();
    @Unique private Map<ResourceLocation, Integer> discoveredOrbs = new HashMap<>();

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readSpells(CompoundTag compound, CallbackInfo ci) {
        discoveredSpells = MagicHelper.getSpells(compound);
        discoveredOrbs = MagicHelper.getOrbs(compound);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void writeSpells(@NotNull CompoundTag compound, CallbackInfo ci) {
        compound.put(MagicHelper.SPELL_TAG, MagicHelper.createSpellsTag(discoveredSpells));
        compound.put(MagicHelper.ORB_TAG, MagicHelper.createOrbsTag(discoveredOrbs));
    }

    @Override
    public void setDiscoveredSpells(Map<ResourceLocation, Integer> spells) {
        discoveredSpells = spells;
    }

    @Override
    public Map<ResourceLocation, Integer> getDiscoveredSpells() {
        return discoveredSpells;
    }

    @Override
    public void setDiscoveredOrbs(Map<ResourceLocation, Integer> orbs) {
        discoveredOrbs = orbs;
    }

    @Override
    public Map<ResourceLocation, Integer> getDiscoveredOrbs() {
        return discoveredOrbs;
    }
}
