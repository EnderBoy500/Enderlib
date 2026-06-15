package net.enderboy500.enderlib.registry.impl;

import com.mojang.datafixers.util.Pair;
import net.enderboy500.enderlib.util.interfaces.ToolMaps;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Objects;

public final class ToolFunctionRegistryImpl {

    private ToolFunctionRegistryImpl() {
    }

    public static void creatingTillingFunction(Block base, Block result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        HoeItem.TILLABLES.put(base, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(result.defaultBlockState())));
    }

    public static void creatingTillingStateModification(Block base, BlockState result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        HoeItem.TILLABLES.put(base, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(result)));
    }

    public static void creatingShovellingFunction(Block base, Block path) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(path, "Path block cannot be null!");
        ShovelItem.FLATTENABLES.put(base, path.defaultBlockState());
    }

    public static void creatingShovellingStateModification(Block base, BlockState path) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(path, "Path block cannot be null!");
        ShovelItem.FLATTENABLES.put(base, path);
    }

    public static void creatingShearFunction(Block base, Block result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolMaps.SHEAR.put(base, result.defaultBlockState());
    }

    public static void creatingShearStateModification(Block base, BlockState result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolMaps.SHEAR.put(base, result);
    }

    public static void creatingSwordFunction(Block base, Block result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolMaps.SWORD.put(base, result.defaultBlockState());
    }

    public static void creatingSwordStateModification(Block base, BlockState result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolMaps.SWORD.put(base, result);
    }
}
