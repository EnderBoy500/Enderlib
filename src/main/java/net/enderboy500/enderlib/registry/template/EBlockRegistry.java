package net.enderboy500.enderlib.registry.template;

import net.enderboy500.enderlib.util.AutoLang;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EBlockRegistry {
    public static Map<String, List<Block>> registeryBlockMap = new HashMap<>();
    private static List<Block> blocks = new ArrayList<>();
    private final String modId;
    public EBlockRegistry(String modId) {
        this.modId = modId;
    }

    public Block registerBlock(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean autoLang) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(modId, id));
        Block block = factory.apply(settings.setId(key));
        if (autoLang) AutoLang.blockList.add(block);

        blocks.add(block);
        registeryBlockMap.put(modId, blocks);

        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public Block registerBlockAsCopyOf(String id, Block block, boolean autoLang) {
        return registerBlock(id, Block::new, BlockBehaviour.Properties.ofFullCopy(block), autoLang);
    }
    public Block registerBlockWithItem(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean autoLang, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, factory, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerBlockAsACopyOfBlockWithItem(String id, Block block, boolean autoLang, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        return registerBlockWithItem(id, Block::new, BlockBehaviour.Properties.ofFullCopy(block), autoLang, creativeModeTabResourceKey);
    }
    public Block registerBlockAsACopyOfBlockWithItem(String id, Block block, boolean autoLang) {
        return registerBlockWithItem(id, Block::new, BlockBehaviour.Properties.ofFullCopy(block), autoLang, null);
    }
    public Block registerBlockWithItem(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean autoLang) {
        return registerBlockWithItem(id, factory, settings, autoLang, null);
    }
    public Block registerStairs(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, SlabBlock::new, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerStairs(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem) {
        return registerStairs(id, settings, autoLang, createItem, null);
    }
    public Block registerSlab(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, SlabBlock::new, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerSlab(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem) {
        return registerSlab(id, settings, autoLang, createItem, null);
    }
    public Block registerWall(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, WallBlock::new, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerWall(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem) {
        return registerWall(id, settings, autoLang, createItem, null);
    }
    public Block registerFence(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, FenceBlock::new, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerFence(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem) {
        return registerFence(id, settings, autoLang, createItem, null);
    }
    public  Block registerFenceGate(String id, BlockBehaviour.Properties settings, WoodType woodType, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, properties -> new FenceGateBlock(woodType, properties), settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerFenceGate(String id, BlockBehaviour.Properties settings, WoodType woodType, boolean autoLang, boolean createItem) {
        return registerFenceGate(id, settings, woodType, autoLang, createItem, null);
    }
    public Block registerDoor(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, properties -> new DoorBlock(blockSetType, properties), settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerDoor(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, boolean autoLang, boolean createItem) {
        return registerDoor(id, settings, blockSetType, autoLang, createItem, null);
    }
    public Block registerTrapDoor(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, properties -> new TrapDoorBlock(blockSetType, properties), settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerTrapDoor(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, boolean autoLang, boolean createItem) {
        return registerTrapDoor(id, settings, blockSetType, autoLang, createItem, null);
    }
    public Block registerButton(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, int pressTicks, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, properties -> new ButtonBlock(blockSetType, pressTicks, properties), settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerButton(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, int pressTicks, boolean autoLang, boolean createItem) {
        return registerButton(id, settings, blockSetType, pressTicks, autoLang, createItem, null);
    }
    public Block registerPressurePlate(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, properties -> new PressurePlateBlock(blockSetType, properties), settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerPressurePlate(String id, BlockBehaviour.Properties settings, BlockSetType blockSetType, boolean autoLang, boolean createItem) {
        return registerPressurePlate(id, settings, blockSetType, autoLang, createItem, null);
    }
    public Block registerPane(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, IronBarsBlock::new, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        return block;
    }
    public Block registerPane(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem) {
        return registerPane(id, settings, autoLang, createItem, null);
    }
    public Block registerShelf(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        Block block = registerBlock(id, ShelfBlock::new, settings, autoLang);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        if (createItem) eItemRegistry.registerBlockItem(id, block, creativeModeTabResourceKey);
        BlockEntityType.SHELF.addSupportedBlock(block);
        return block;
    }
    public Block registerShelf(String id, BlockBehaviour.Properties settings, boolean autoLang, boolean createItem) {
        return registerShelf(id, settings, autoLang, createItem, null);
    }
    public <T extends Fluid> T registerFluid(String id, T value) {
        return (Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath(modId, id), value));
    }
}
