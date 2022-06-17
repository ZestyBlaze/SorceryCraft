package net.zestyblaze.sorcerycraft.client.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;

public class SpellEntityRenderer extends EntityRenderer<SpellEntity> {
    public SpellEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(SpellEntity entity) {
        return null;
    }
}
