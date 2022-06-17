package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreenHandler;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import net.zestyblaze.sorcerycraft.util.spell.SpellPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(PlayerEntity.class)
public class PlayerMixin implements SpellPlayerEntity {
    @Shadow public ScreenHandler containerMenu;
    @Unique private Map<Identifier, Integer> discoveredSpells = new HashMap<>();

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readSpells(NbtCompound compound, CallbackInfo ci) {
        discoveredSpells = SpellHelper.getSpells(compound);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void writeSpells(@NotNull NbtCompound compound, CallbackInfo ci) {
        compound.put(SpellHelper.SPELL_TAG, SpellHelper.createSpellsTag(discoveredSpells));
    }

    @Override
    public void setDiscoveredSpells(Map<Identifier, Integer> spells) {
        discoveredSpells = spells;
        if(containerMenu instanceof CastingTableScreenHandler) {
            ((CastingTableScreenHandler)containerMenu).setSpells((PlayerEntity)(Object)this);
        }
    }

    @Override
    public Map<Identifier, Integer> getDiscoveredSpells() {
        return discoveredSpells;
    }
}
