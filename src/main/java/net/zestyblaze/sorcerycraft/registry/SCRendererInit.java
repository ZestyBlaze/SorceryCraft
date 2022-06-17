package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.zestyblaze.sorcerycraft.client.entity.renderer.SpellEntityRenderer;

public class SCRendererInit {
    public static void registerRenders() {
        EntityRendererRegistry.register(SCEntityInit.SPELL_ENTITY, SpellEntityRenderer::new);
    }
}
