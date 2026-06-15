package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.block.Block;

public interface ColorMapHelper {
    static void assignBlockColor(Block block, int color) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return color;
        }), block);
    }

    static void assignBlockFoliageColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : returnColor;
        }), block);
    }
    static void assignBlockGrassColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : returnColor;
        }), block);
    }
    static void assignBlockWaterColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : returnColor;
        }), block);
    }
    static void assignBlockDryFoliageColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getAverageDryFoliageColor(world, pos) : returnColor;
        }), block);
    }
}
