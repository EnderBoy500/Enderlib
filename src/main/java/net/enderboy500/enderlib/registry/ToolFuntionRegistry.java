package net.enderboy500.enderlib.registry;

import net.enderboy500.enderlib.registry.impl.ToolFunctionRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public final class ToolFuntionRegistry {

    private ToolFuntionRegistry() {
    }

    public static void creatingShearFunction(Block block, Block result) {
        ToolFunctionRegistryImpl.creatingShearFunction(block, result);}
    public static void creatingShearStateModification(Block block, BlockState result) {
        ToolFunctionRegistryImpl.creatingShearStateModification(block, result);}

    public static void creatingShovellingFunction(Block block, Block path) {
        ToolFunctionRegistryImpl.creatingShovellingFunction(block, path);}
    public static void creatingShovellingStateModification(Block block, BlockState path) {
        ToolFunctionRegistryImpl.creatingShovellingStateModification(block, path);}

    public static void creatingTillingFunction(Block block, Block result) {
        ToolFunctionRegistryImpl.creatingTillingFunction(block, result);}
    public static void creatingTillingStateModification(Block block, BlockState result) {
        ToolFunctionRegistryImpl.creatingTillingStateModification(block, result);}

    public static void creatingSwordFunction(Block block, Block result) {
        ToolFunctionRegistryImpl.creatingSwordFunction(block, result);}
    public static void creatingSwordStateModification(Block block, BlockState result) {
        ToolFunctionRegistryImpl.creatingSwordStateModification(block, result);}

}
