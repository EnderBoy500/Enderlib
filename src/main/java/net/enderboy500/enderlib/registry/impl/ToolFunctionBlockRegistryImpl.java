package net.enderboy500.enderlib.registry.impl;

import com.mojang.datafixers.util.Pair;
import net.enderboy500.enderlib.helper.ToolHelper;
import net.enderboy500.enderlib.item.ToolFunctionItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShovelItem;

import java.util.Objects;

public final class ToolFunctionBlockRegistryImpl {

    private ToolFunctionBlockRegistryImpl() {
    }

    public static void creatingTillingFunction(Block base, Block result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        HoeItem.TILLING_ACTIONS.put(base, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(result.getDefaultState())));
    }

    public static void creatingTillingStateModification(Block base, BlockState result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        HoeItem.TILLING_ACTIONS.put(base, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(result)));
    }

    public static void creatingShovellingFunction(Block base, Block path) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(path, "Path block cannot be null!");
        ShovelItem.PATH_STATES.put(base, path.getDefaultState());
    }

    public static void creatingShovellingStateModification(Block base, BlockState path) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(path, "Path block cannot be null!");
        ShovelItem.PATH_STATES.put(base, path);
    }

    public static void creatingShearFunction(Block base, Block result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolHelper.SHEAR.put(base, result.getDefaultState());
    }

    public static void creatingShearStateModification(Block base, BlockState result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolHelper.SHEAR.put(base, result);
    }

    public static void creatingSwordFunction(Block base, Block result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolHelper.SWORD.put(base, result.getDefaultState());
    }

    public static void creatingSwordStateModification(Block base, BlockState result) {
        Objects.requireNonNull(base, "Base block cannot be null!");
        Objects.requireNonNull(result, "Result block cannot be null!");
        ToolHelper.SWORD.put(base, result);
    }
}
