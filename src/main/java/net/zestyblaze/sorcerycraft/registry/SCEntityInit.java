package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;

public class SCEntityInit {
    public static EntityType<SpellEntity> SPELL_ENTITY = FabricEntityTypeBuilder.create(MobCategory.MISC, (EntityType.EntityFactory<SpellEntity>)SpellEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build();

    public static void registerEntities() {
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(SorceryCraft.MODID, "spell"), SPELL_ENTITY);
    }
}
