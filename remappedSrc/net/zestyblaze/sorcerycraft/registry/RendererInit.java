package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.zestyblaze.sorcerycraft.client.entity.renderer.SpellEntityRenderer;

public class RendererInit {
    public static void registerRenders() {
        EntityRendererRegistry.register(EntityInit.SPELL_ENTITY, SpellEntityRenderer::new);
    }
}
