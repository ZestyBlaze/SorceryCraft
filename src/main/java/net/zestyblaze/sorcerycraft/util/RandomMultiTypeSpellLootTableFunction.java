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
import net.zestyblaze.sorcerycraft.api.util.SpellHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RandomMultiTypeSpellLootTableFunction extends LootItemConditionalFunction {
    public RandomMultiTypeSpellLootTableFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack run(@NotNull ItemStack stack, LootContext context) {
        while (true) {
            context.getRandom().nextDouble();
            Spell[] spells = SpellRegistry.getSpells();
            int index = context.getRandom().nextInt(spells.length);
            Map<ResourceLocation, Integer> spell = SpellHelper.getSpells(stack);
            if(spells[index].getSpellType() == SpellType.BOTH) {
                spell.put(spells[index].getID(), spells[index].getLevel());
                SpellHelper.setSpells(stack, spell);
                return stack;
            }
        }
    }

    @Override
    public LootItemFunctionType getType() {
        return Registry.LOOT_FUNCTION_TYPE.get(new ResourceLocation(SorceryCraft.MODID, "random_multi_type_spell"));
    }

    public static class Factory extends LootItemConditionalFunction.Serializer<RandomMultiTypeSpellLootTableFunction> {
        public Factory() {}

        @Override
        public RandomMultiTypeSpellLootTableFunction deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext deserializationContext, LootItemCondition @NotNull [] conditions) {
            return (RandomMultiTypeSpellLootTableFunction)new Builder().build();
        }
    }

    public static class Builder extends LootItemConditionalFunction.Builder<RandomMultiTypeSpellLootTableFunction.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public LootItemFunction build() {
            return new RandomMultiTypeSpellLootTableFunction(getConditions());
        }
    }
}
