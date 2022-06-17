package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreen;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreenHandler;

public class ScreenInit {
    public static void registerScreens() {
        ScreenProviderRegistry.INSTANCE.<CastingTableScreenHandler>registerFactory(new Identifier(SorceryCraft.MODID, "casting_table"), (container -> {
            assert MinecraftClient.getInstance().player != null;
            return new CastingTableScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText("block." + SorceryCraft.MODID + "casting_table"));
        }));
    }
}
