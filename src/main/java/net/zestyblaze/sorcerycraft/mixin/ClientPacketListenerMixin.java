package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;
import net.zestyblaze.sorcerycraft.registry.SCEntityInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow private ClientLevel level;

    @Inject(method = "handleAddEntity", at = @At("TAIL"))
    private void handleSpellSpawn(ClientboundAddEntityPacket packet, CallbackInfo ci) {
        EntityType<?> entityType = packet.getType();
        Entity entity = null;

        if (entityType == SCEntityInit.SPELL_ENTITY) {
            entity = new SpellEntity(level, packet.getX(), packet.getY(), packet.getZ());
        }

        if (entity != null) {
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            entity.syncPacketPositionCodec(x, y, z);
            entity.setXRot((packet.getXRot() * 360) / 250F);
            entity.setYRot((packet.getYRot() * 360) / 250F);
            entity.setId(packet.getId());
            entity.setUUID(packet.getUUID());
            level.putNonPlayerEntity(packet.getId(), entity);
        }
    }
}
