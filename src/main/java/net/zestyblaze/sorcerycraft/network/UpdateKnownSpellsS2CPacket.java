package net.zestyblaze.sorcerycraft.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.util.SpellHelper;
import net.zestyblaze.sorcerycraft.util.SpellPlayerEntity;

public class UpdateKnownSpellsS2CPacket {
    @Environment(EnvType.CLIENT)
    public static void handle(Minecraft client, ClientPacketListener network, FriendlyByteBuf buf, PacketSender sender) {
        int id = buf.readInt();
        client.execute(() -> {
            ClientLevel world = client.level;
            if(world != null) {
                Entity entity = world.getEntity(id);
                CompoundTag tag = buf.readNbt();
                if(entity instanceof Player) {
                    SpellPlayerEntity spellPlayer = (SpellPlayerEntity)entity;
                    spellPlayer.setDiscoveredSpells(SpellHelper.getSpells(tag));
                }
            }
        });
    }

    public static void send(ServerPlayer player, CompoundTag tag) {
        FriendlyByteBuf bytes = new FriendlyByteBuf(Unpooled.buffer());
        bytes.writeNbt(tag);
        player.connection.send(new ClientboundCustomPayloadPacket(new ResourceLocation(SorceryCraft.MODID, "update_known_spells"), bytes));
    }
}
