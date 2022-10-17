package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.zestyblaze.sorcerycraft.api.util.MagicHelper;
import net.zestyblaze.sorcerycraft.network.UpdateKnownMagicS2CPacket;
import net.zestyblaze.sorcerycraft.util.MagicServerPlayerEntity;
import net.zestyblaze.sorcerycraft.util.OrbPlayerEntity;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin extends PlayerMixin implements MagicServerPlayerEntity {
    @Inject(method = "restoreFrom", at = @At("HEAD"))
    private void copySpells(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        SpellPlayerEntity oldSpellPlayer = (SpellPlayerEntity)that;
        SpellPlayerEntity newSpellPlayer = this;
        OrbPlayerEntity oldOrbPlayer = (OrbPlayerEntity)that;
        OrbPlayerEntity newOrbPlayer = this;

        newSpellPlayer.setDiscoveredSpells(oldSpellPlayer.getDiscoveredSpells());
        newOrbPlayer.setDiscoveredOrbs(oldOrbPlayer.getDiscoveredOrbs());
    }

    @Override
    public void sync() {
        CompoundTag tag = new CompoundTag();
        tag.put(MagicHelper.SPELL_TAG, MagicHelper.createSpellsTag(getDiscoveredSpells()));
        tag.put(MagicHelper.ORB_TAG, MagicHelper.createOrbsTag(getDiscoveredOrbs()));
        UpdateKnownMagicS2CPacket.send((ServerPlayer)(Object)this, tag);
    }
}
