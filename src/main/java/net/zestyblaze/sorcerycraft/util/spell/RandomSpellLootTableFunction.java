package net.zestyblaze.sorcerycraft.util.spell;

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
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.api.registry.SpellRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RandomSpellLootTableFunction extends LootItemConditionalFunction {
    public RandomSpellLootTableFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack run(@NotNull ItemStack stack, LootContext context) {
        double chance = 1.0d;
        while (!(context.getRandom().nextDouble() > chance)) {
            Spell[] spells = SpellRegistry.getSpells();
            int index = context.getRandom().nextInt(spells.length);
            Map<ResourceLocation, Integer> spell = SpellHelper.getSpells(stack);
            spell.put(spells[index].getID(), spells[index].getLevel());
            SpellHelper.setSpells(stack, spell);
            chance = chance * 0.25d;
        }
        return stack;
    }

    @Override
    public LootItemFunctionType getType() {
        return Registry.LOOT_FUNCTION_TYPE.get(new ResourceLocation(SorceryCraft.MODID, "random_spell"));
    }

    public static class Factory extends LootItemConditionalFunction.Serializer<RandomSpellLootTableFunction> {
        public Factory() {}

        @Override
        public RandomSpellLootTableFunction deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext deserializationContext, LootItemCondition @NotNull [] conditions) {
            return (RandomSpellLootTableFunction)new Builder().build();
        }
    }

    public static class Builder extends LootItemConditionalFunction.Builder<RandomSpellLootTableFunction.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public LootItemFunction build() {
            return new RandomSpellLootTableFunction(getConditions());
        }
    }
}
