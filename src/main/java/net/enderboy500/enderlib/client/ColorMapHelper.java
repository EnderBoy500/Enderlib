package net.enderboy500.enderlib.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;

public interface ColorMapHelper {
    static void assignBlockColor(Block block, int color) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return color;
        }), block);
    }

    static void assignBlockFoliageColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : returnColor;
        }), block);
    }
    static void assignBlockGrassColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : returnColor;
        }), block);
    }
    static void assignBlockWaterColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : returnColor;
        }), block);
    }
    static void assignBlockDryFoliageColor(Block block, int returnColor) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getDryFoliageColor(world, pos) : returnColor;
        }), block);
    }
}
