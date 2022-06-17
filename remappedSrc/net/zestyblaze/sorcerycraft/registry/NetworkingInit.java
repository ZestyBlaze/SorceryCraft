package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.network.SelectSpellC2SPacket;
import net.zestyblaze.sorcerycraft.network.UpdateKnownSpellsS2CPacket;

@SuppressWarnings("deprecation")
public class NetworkingInit {
    public static void registerClientNetworks() {
        ClientSidePacketRegistryImpl.INSTANCE.register(new Identifier(SorceryCraft.MODID, "update_known_spells"), UpdateKnownSpellsS2CPacket::handle);
    }

    public static void registerServerNetworks() {
        ServerSidePacketRegistryImpl.INSTANCE.register(new Identifier(SorceryCraft.MODID, "select_spell"), SelectSpellC2SPacket::handle);
    }
}
