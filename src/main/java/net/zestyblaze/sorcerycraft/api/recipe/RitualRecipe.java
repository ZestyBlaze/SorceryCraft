package net.zestyblaze.sorcerycraft.api.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.zestyblaze.sorcerycraft.registry.SCRecipeInit;

import java.util.ArrayList;
import java.util.List;

public class RitualRecipe implements Recipe<Container> {
    private final ResourceLocation resourceLocation;
    public final NonNullList<Ingredient> cost;
    public final ItemStack output;
    public final int xpCost;

    public RitualRecipe(ResourceLocation resourceLocation, NonNullList<Ingredient> cost, ItemStack output, int xpCost) {
        this.resourceLocation = resourceLocation;
        this.cost = cost;
        this.output = output;
        this.xpCost = xpCost;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matches(container, cost);
    }

    @Override
    public ItemStack assemble(Container container) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return resourceLocation;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SCRecipeInit.RITUAL_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SCRecipeInit.RITUAL_RECIPE_TYPE;
    }

    public static boolean matches(Container inv, NonNullList<Ingredient> input) {
        List<ItemStack> checkList = new ArrayList<>();
        for(int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if(!stack.isEmpty()) {
                checkList.add(stack);
            }
        }
        if(input.size() != checkList.size()) {
            return false;
        }
        for(Ingredient ingredient : input) {
            boolean found = false;
            for(ItemStack stack : checkList) {
                if(ingredient.test(stack)) {
                    found = true;
                    checkList.remove(stack);
                    break;
                }
            }
            if(!found) {
                return false;
            }
        }
        return true;
    }

    public static class Serializer implements RecipeSerializer<RitualRecipe> {
        @Override
        public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            NonNullList<Ingredient> ingredients = getIngredients(GsonHelper.getAsJsonArray(serializedRecipe, "cost"));
            if(ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for ritual");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for ritual");
            }
            return new RitualRecipe(recipeId, ingredients, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result")), GsonHelper.getAsInt(serializedRecipe, "xpCost"));

        }

        @Override
        public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(buffer.readVarInt(), Ingredient.EMPTY);
            for(int i = 0; i < nonNullList.size(); i++) {
                nonNullList.set(i, Ingredient.fromNetwork(buffer));
            }
            return new RitualRecipe(recipeId, nonNullList, buffer.readItem(), buffer.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
            buffer.writeVarInt(recipe.cost.size());
            for(Ingredient ingredient : recipe.cost) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.output);
            buffer.writeInt(recipe.xpCost);
        }

        public static NonNullList<Ingredient> getIngredients(JsonArray json) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for(int i = 0; i < json.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if(!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            return ingredients;
        }
    }
}
