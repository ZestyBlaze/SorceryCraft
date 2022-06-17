package net.zestyblaze.sorcerycraft.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;

public class SpellEntityRenderer extends EntityRenderer<SpellEntity> {
    public SpellEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SpellEntity entity) {
        return null;
    }
}
