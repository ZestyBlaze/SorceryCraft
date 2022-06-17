package net.zestyblaze.sorcerycraft.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ClientPlayerEntity.class)
public class LocalPlayerMixin extends PlayerMixin {
    @Shadow @Final protected MinecraftClient minecraft;

    @Override
    public void setDiscoveredSpells(Map<Identifier, Integer> spells) {
        super.setDiscoveredSpells(spells);
        if (minecraft.currentScreen instanceof CastingTableScreen) {
            ((CastingTableScreen)minecraft.currentScreen).resetIndex();
        }
    }
}
