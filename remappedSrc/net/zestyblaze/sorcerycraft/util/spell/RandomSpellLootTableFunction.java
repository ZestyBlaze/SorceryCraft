package net.zestyblaze.sorcerycraft.util.spell;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RandomSpellLootTableFunction extends ConditionalLootFunction {
    public RandomSpellLootTableFunction(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack process(@NotNull ItemStack stack, LootContext context) {
        double chance = 1.0d;
        while (!(context.getRandom().nextDouble() > chance)) {
            Spell[] spells = SpellRegistry.getSpells();
            int index = context.getRandom().nextInt(spells.length);
            Map<Identifier, Integer> spell = SpellHelper.getSpells(stack);
            spell.put(spells[index].getID(), spells[index].getLevel());
            SpellHelper.setSpells(stack, spell);
            chance = chance * 0.25d;
        }
        return stack;
    }

    @Override
    public LootFunctionType getType() {
        return Registry.LOOT_FUNCTION_TYPE.get(new Identifier(SorceryCraft.MODID, "random_spell"));
    }

    public static class Factory extends ConditionalLootFunction.Serializer<RandomSpellLootTableFunction> {
        public Factory() {}

        @Override
        public RandomSpellLootTableFunction fromJson(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditions) {
            return (RandomSpellLootTableFunction)new net.zestyblaze.sorcerycraft.util.spell.RandomSpellLootTableFunction.Builder().build();
        }
    }

    public static class Builder extends ConditionalLootFunction.Builder<RandomSpellLootTableFunction.Builder> {
        @Override
        protected net.zestyblaze.sorcerycraft.util.spell.RandomSpellLootTableFunction.Builder getThisBuilder() {
            return this;
        }

        @Override
        public LootFunction build() {
            return new RandomSpellLootTableFunction(getConditions());
        }
    }
}
