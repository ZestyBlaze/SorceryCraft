package net.zestyblaze.sorcerycraft.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreenHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class SelectSpellC2SPacket {
    public static void handle(@NotNull PacketContext context, @NotNull PacketByteBuf bytes) {
        int index = bytes.readInt();
        ScreenHandler handler = context.getPlayer().currentScreenHandler;
        if (handler instanceof CastingTableScreenHandler merchantContainer) {
            merchantContainer.setIndex(index);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void send(@NotNull MinecraftClient minecraft, int index) {
        PacketByteBuf bytes = new PacketByteBuf(Unpooled.buffer());
        bytes.writeInt(index);
        assert minecraft.getNetworkHandler() != null;
        minecraft.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new Identifier(SorceryCraft.MODID, "select_spell"), bytes));
    }
}
