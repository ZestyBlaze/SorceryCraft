package net.zestyblaze.sorcerycraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.zestyblaze.sorcerycraft.registry.AnnotationInit;
import net.zestyblaze.sorcerycraft.registry.NetworkingInit;
import net.zestyblaze.sorcerycraft.registry.RendererInit;
import net.zestyblaze.sorcerycraft.registry.ScreenInit;

@Environment(EnvType.CLIENT)
public class SCClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AnnotationInit.registerAnnotations();
        ScreenInit.registerScreens();
        RendererInit.registerRenders();
        NetworkingInit.registerClientNetworks();
    }
}
