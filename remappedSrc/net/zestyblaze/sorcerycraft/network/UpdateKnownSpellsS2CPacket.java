package net.zestyblaze.sorcerycraft.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import net.zestyblaze.sorcerycraft.util.spell.SpellPlayerEntity;

@SuppressWarnings("deprecation")
public class UpdateKnownSpellsS2CPacket {
    @Environment(EnvType.CLIENT)
    public static void handle(PacketContext context, PacketByteBuf bytes) {
        NbtCompound tag = bytes.readNbt();
        if (context.getPlayer() != null) {
            SpellPlayerEntity spellPlayer = (SpellPlayerEntity) context.getPlayer();
            spellPlayer.setDiscoveredSpells(SpellHelper.getSpells(tag));
        }
    }

    public static void send(ServerPlayerEntity player, NbtCompound tag) {
        PacketByteBuf bytes = new PacketByteBuf(Unpooled.buffer());
        bytes.writeNbt(tag);
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(new Identifier(SorceryCraft.MODID, "update_known_spells"), bytes));
    }
}
