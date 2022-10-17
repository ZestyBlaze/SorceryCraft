package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.zestyblaze.sorcerycraft.client.entity.renderer.SpellEntityRenderer;

public class SCRendererInit {
    public static void registerEntityRenders() {
        EntityRendererRegistry.register(SCEntityInit.SPELL_ENTITY, SpellEntityRenderer::new);
    }

    public static void registerBlockRenders() {
        BlockRenderLayerMap.INSTANCE.putBlock(SCBlockInit.SHIELD_BLOCK, RenderType.translucent());
    }
}
