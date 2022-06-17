package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreenHandler;
import net.zestyblaze.sorcerycraft.network.UpdateKnownSpellsS2CPacket;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import net.zestyblaze.sorcerycraft.util.spell.SpellPlayerEntity;
import net.zestyblaze.sorcerycraft.util.spell.SpellServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin extends PlayerMixin implements SpellServerPlayerEntity {
    @Inject(method = "restoreFrom", at = @At("HEAD"))
    private void copySpells(ServerPlayerEntity that, boolean keepEverything, CallbackInfo ci) {
        SpellPlayerEntity oldSpellPlayer = (SpellPlayerEntity)that;
        SpellPlayerEntity newSpellPlayer = this;

        newSpellPlayer.setDiscoveredSpells(oldSpellPlayer.getDiscoveredSpells());
    }

    @Override
    public void setDiscoveredSpells(Map<Identifier, Integer> spells) {
        super.setDiscoveredSpells(spells);
        if (containerMenu instanceof CastingTableScreenHandler) {
            sync();
        }
    }

    @Override
    public void sync() {
        NbtCompound tag = new NbtCompound();
        tag.put(SpellHelper.SPELL_TAG, SpellHelper.createSpellsTag(getDiscoveredSpells()));
        UpdateKnownSpellsS2CPacket.send((ServerPlayerEntity)(Object)this, tag);
    }
}
