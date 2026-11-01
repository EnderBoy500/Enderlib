package net.enderboy500.enderlib.registry.template;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class ELevelRegistry {
    private final String modId;
    public ELevelRegistry(String modId) {
        this.modId = modId;
    }
    public ResourceKey<Biome> registerBiome(String id) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(modId,id));
    }
    public ResourceKey<LevelStem> registerLevelStem(String id) {
        return ResourceKey.create(Registries.LEVEL_STEM, Identifier.fromNamespaceAndPath(modId,id));
    }
    public ResourceKey<Level> registerDimension(String id) {
        return ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(modId,id));
    }
    public ResourceKey<DimensionType> registerDimensionType(String id) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(modId,id));
    }
}
