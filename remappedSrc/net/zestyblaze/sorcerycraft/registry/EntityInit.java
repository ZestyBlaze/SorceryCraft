package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.entity.SpellEntity;

public class EntityInit {
    public static EntityType<SpellEntity> SPELL_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<SpellEntity>)SpellEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build();

    public static void registerEntities() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(SorceryCraft.MODID, "spell"), SPELL_ENTITY);
    }
}
