package net.zestyblaze.sorcerycraft.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.zestyblaze.sorcerycraft.SorceryCraft;

public class SpawnSmokeParticlesPacket {
    public static final ResourceLocation ID = new ResourceLocation(SorceryCraft.MODID, "spawn_smoke_particles");

    public static void send(Player player, Entity entity) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(entity.getId());
        ServerPlayNetworking.send((ServerPlayer)player, ID, buf);
    }

    public static void handle(Minecraft client, ClientPacketListener network, FriendlyByteBuf buf, PacketSender sender) {
        int id = buf.readInt();
        client.execute(() -> {
            ClientLevel world = client.level;
            if (world != null) {
                Entity entity = world.getEntity(id);
                if (entity != null) {
                    for (int i = 0; i < 32; i++) {
                        world.addParticle(ParticleTypes.SMOKE, entity.getRandomX(1), entity.getRandomY(), entity.getRandomZ(1), 0, 0, 0);
                    }
                }
            }
        });
    }
}
