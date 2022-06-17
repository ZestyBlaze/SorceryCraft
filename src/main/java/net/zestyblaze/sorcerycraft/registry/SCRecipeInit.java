package net.zestyblaze.sorcerycraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.recipe.RitualRecipe;

import java.util.LinkedHashMap;
import java.util.Map;

public class SCRecipeInit {
    private static final Map<RecipeSerializer<?>, ResourceLocation> RECIPE_SERIALIZERS = new LinkedHashMap<>();
    private static final Map<RecipeType<?>, ResourceLocation> RECIPE_TYPES = new LinkedHashMap<>();

    public static final RecipeSerializer<RitualRecipe> RITUAL_RECIPE_SERIALIZER = create("ritual_recipe", new RitualRecipe.Serializer());
    public static final RecipeType<RitualRecipe> RITUAL_RECIPE_TYPE = create("ritual_recipe");

    private static <T extends Recipe<?>> RecipeSerializer<T> create(String name, RecipeSerializer<T> serializer) {
        RECIPE_SERIALIZERS.put(serializer, new ResourceLocation(SorceryCraft.MODID, name));
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> create(String name) {
        RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        RECIPE_TYPES.put(type, new ResourceLocation(SorceryCraft.MODID, name));
        return type;
    }

    public static void registerRecipes() {
        RECIPE_SERIALIZERS.keySet().forEach(recipeSerializer -> Registry.register(Registry.RECIPE_SERIALIZER, RECIPE_SERIALIZERS.get(recipeSerializer), recipeSerializer));
        RECIPE_TYPES.keySet().forEach(recipeType -> Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPES.get(recipeType), recipeType));
    }
}
