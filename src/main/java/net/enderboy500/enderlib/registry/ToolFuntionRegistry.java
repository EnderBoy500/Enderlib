package net.enderboy500.enderlib.registry;

import net.enderboy500.enderlib.registry.impl.ToolFunctionBlockRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public final class ToolFuntionRegistry {

    private ToolFuntionRegistry() {
    }

    public static void creatingShearFunction(Block block, Block result) {
        ToolFunctionBlockRegistryImpl.creatingShearFunction(block, result);}
    public static void creatingShearStateModification(Block block, BlockState result) {
        ToolFunctionBlockRegistryImpl.creatingShearStateModification(block, result);}

    public static void creatingShovellingFunction(Block block, Block path) {
        ToolFunctionBlockRegistryImpl.creatingShovellingFunction(block, path);}
    public static void creatingShovellingStateModification(Block block, BlockState path) {
        ToolFunctionBlockRegistryImpl.creatingShovellingStateModification(block, path);}

    public static void creatingTillingFunction(Block block, Block result) {
        ToolFunctionBlockRegistryImpl.creatingTillingFunction(block, result);}
    public static void creatingTillingStateModification(Block block, BlockState result) {
        ToolFunctionBlockRegistryImpl.creatingTillingStateModification(block, result);}

    public static void creatingSwordFunction(Block block, Block result) {
        ToolFunctionBlockRegistryImpl.creatingSwordFunction(block, result);}
    public static void creatingSwordStateModification(Block block, BlockState result) {
        ToolFunctionBlockRegistryImpl.creatingSwordStateModification(block, result);}

}
