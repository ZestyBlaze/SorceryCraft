package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.network.SpawnSmokeParticlesPacket;
import net.zestyblaze.sorcerycraft.network.UpdateKnownMagicS2CPacket;

public class SCNetworkingInit {
    public static void registerClientNetworks() {
        ClientPlayNetworking.registerGlobalReceiver(SpawnSmokeParticlesPacket.ID, SpawnSmokeParticlesPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(SorceryCraft.MODID, "update_known_spells"), UpdateKnownMagicS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(SorceryCraft.MODID, "update_known_orbs"), UpdateKnownMagicS2CPacket::handle);
    }
}
