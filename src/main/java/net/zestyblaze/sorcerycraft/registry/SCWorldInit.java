package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zestyblaze.sorcerycraft.SorceryCraft;

import java.util.Arrays;

public class SCWorldInit {
    private static final ConfiguredFeature<?, ?> CRYSTAL_ORE = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, SCBlockInit.CRYSTAL_ORE.defaultBlockState(), 5));
    private static final PlacedFeature CRYSTAL_ORE_PLACE = new PlacedFeature(Holder.direct(CRYSTAL_ORE), Arrays.asList(CountPlacement.of(15), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(23), VerticalAnchor.belowTop(72))));

    public static void registerOres() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(SorceryCraft.MODID, "crystal_ore"), CRYSTAL_ORE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(SorceryCraft.MODID, "crystal_ore"), CRYSTAL_ORE_PLACE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(SorceryCraft.MODID, "crystal_ore")));
    }
}
