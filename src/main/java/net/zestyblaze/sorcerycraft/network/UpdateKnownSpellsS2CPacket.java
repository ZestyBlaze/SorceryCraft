package net.zestyblaze.sorcerycraft.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import net.zestyblaze.sorcerycraft.util.spell.SpellPlayerEntity;

@SuppressWarnings("deprecation")
public class UpdateKnownSpellsS2CPacket {
    @Environment(EnvType.CLIENT)
    public static void handle(PacketContext context, FriendlyByteBuf bytes) {
        CompoundTag tag = bytes.readNbt();
        if (context.getPlayer() != null) {
            SpellPlayerEntity spellPlayer = (SpellPlayerEntity) context.getPlayer();
            spellPlayer.setDiscoveredSpells(SpellHelper.getSpells(tag));
        }
    }

    public static void send(ServerPlayer player, CompoundTag tag) {
        FriendlyByteBuf bytes = new FriendlyByteBuf(Unpooled.buffer());
        bytes.writeNbt(tag);
        player.connection.send(new ClientboundCustomPayloadPacket(new ResourceLocation(SorceryCraft.MODID, "update_known_spells"), bytes));
    }
}
