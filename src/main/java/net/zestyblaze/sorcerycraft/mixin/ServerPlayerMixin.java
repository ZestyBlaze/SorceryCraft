package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.zestyblaze.sorcerycraft.network.UpdateKnownSpellsS2CPacket;
import net.zestyblaze.sorcerycraft.api.util.SpellHelper;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;
import net.zestyblaze.sorcerycraft.util.SpellServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin extends PlayerMixin implements SpellServerPlayerEntity {
    @Inject(method = "restoreFrom", at = @At("HEAD"))
    private void copySpells(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        SpellPlayerEntity oldSpellPlayer = (SpellPlayerEntity)that;
        SpellPlayerEntity newSpellPlayer = this;

        newSpellPlayer.setDiscoveredSpells(oldSpellPlayer.getDiscoveredSpells());
    }

    @Override
    public void sync() {
        CompoundTag tag = new CompoundTag();
        tag.put(SpellHelper.SPELL_TAG, SpellHelper.createSpellsTag(getDiscoveredSpells()));
        UpdateKnownSpellsS2CPacket.send((ServerPlayer)(Object)this, tag);
    }
}
