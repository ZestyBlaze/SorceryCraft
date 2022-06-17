package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;
import net.zestyblaze.sorcerycraft.registry.EntityInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPacketListenerMixin {
    @Shadow private ClientWorld level;

    @Inject(method = "handleAddEntity", at = @At("TAIL"))
    private void handleSpellSpawn(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        EntityType<?> entityType = packet.getEntityTypeId();
        Entity entity = null;

        if (entityType == EntityInit.SPELL_ENTITY) {
            entity = new SpellEntity(level, packet.getX(), packet.getY(), packet.getZ());
        }

        if (entity != null) {
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            entity.updateTrackedPosition(x, y, z);
            entity.setPitch((float)(packet.getPitch() * 360) / 250F);
            entity.setYaw((float)(packet.getYaw() * 360) / 250F);
            entity.setId(packet.getId());
            entity.setUuid(packet.getUuid());
            level.addEntity(packet.getId(), entity);
        }
    }
}
