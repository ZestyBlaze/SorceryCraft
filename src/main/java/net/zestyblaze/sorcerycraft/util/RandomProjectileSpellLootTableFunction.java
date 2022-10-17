package net.zestyblaze.sorcerycraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import net.zestyblaze.sorcerycraft.api.spell.Spell;
import net.zestyblaze.sorcerycraft.api.spell.SpellType;
import net.zestyblaze.sorcerycraft.api.util.MagicHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RandomProjectileSpellLootTableFunction extends LootItemConditionalFunction {
    public RandomProjectileSpellLootTableFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack run(@NotNull ItemStack stack, LootContext context) {
        while (true) {
            context.getRandom().nextDouble();
            Spell[] spells = SpellRegistry.getSpells();
            int index = context.getRandom().nextInt(spells.length);
            Map<ResourceLocation, Integer> spell = MagicHelper.getSpells(stack);
            if(spells[index].getSpellType() == SpellType.PROJECTILE) {
                spell.put(spells[index].getID(), spells[index].getLevel());
                MagicHelper.setSpells(stack, spell);
                return stack;
            }
        }
    }

    @Override
    public LootItemFunctionType getType() {
        return Registry.LOOT_FUNCTION_TYPE.get(new ResourceLocation(SorceryCraft.MODID, "random_projectile_spell"));
    }

    public static class Factory extends LootItemConditionalFunction.Serializer<RandomProjectileSpellLootTableFunction> {
        public Factory() {}

        @Override
        public RandomProjectileSpellLootTableFunction deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext deserializationContext, LootItemCondition @NotNull [] conditions) {
            return (RandomProjectileSpellLootTableFunction)new Builder().build();
        }
    }

    public static class Builder extends LootItemConditionalFunction.Builder<RandomProjectileSpellLootTableFunction.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public LootItemFunction build() {
            return new RandomProjectileSpellLootTableFunction(getConditions());
        }
    }
}