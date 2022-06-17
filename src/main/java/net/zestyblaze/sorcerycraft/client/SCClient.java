package net.zestyblaze.sorcerycraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.zestyblaze.sorcerycraft.registry.SCAnnotationInit;
import net.zestyblaze.sorcerycraft.registry.SCNetworkingInit;
import net.zestyblaze.sorcerycraft.registry.SCRendererInit;

@Environment(EnvType.CLIENT)
public class SCClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SCAnnotationInit.registerAnnotations();
        SCRendererInit.registerRenders();
        SCNetworkingInit.registerClientNetworks();
    }
}
