package net.enderboy500.enderlib.helper;

import it.unimi.dsi.fastutil.AbstractStack;
import net.enderboy500.enderlib.EnderLib;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class BlockCreator {

    public static Block defaultBlock(String id, Block block) {
        return register(id, new Block(settings(id, block)), true);
    }
    public static Block defaultBlock(String id, AbstractBlock.Settings settings) {
        return register(id, new Block(settings), true);
    }
    public static Block defaultBlock(String id, AbstractBlock.Settings settings, boolean withItem) {
        return register(id, new Block(settings), withItem);
    }
    public static Block defaultBlock(String id, Block block, boolean withItem) {
        return register(id, new Block(settings(id, block)), withItem);
    }
    public static Block stairs(String id, Block block) {
        return register(id, new StairsBlock(block.getDefaultState(), settings(id, block)), true);
    }
    public static Block slab(String id, Block block) {
        return register(id, new SlabBlock(settings(id, block)), true);
    }
    public static Block wall(String id, Block block) {
        return register(id, new WallBlock(settings(id, block)), true);
    }

    public static Block register(String name, Block block, boolean b) {

        Item.Settings settings = new Item.Settings();
        settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, EnderLib.customId(name)));
        settings.useBlockPrefixedTranslationKey();
        if (b) RegistryHelper.registerBlockItem(name, block);

        return rawRegister(name, block);
    }

    public static Block rawRegister(String name, Block block) {
        return Registry.register(Registries.BLOCK, EnderLib.customId(name), block);
    }

    public static AbstractBlock.Settings settings(String name, Block copiedBlock) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(copiedBlock);
        settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, EnderLib.customId(name)));
        return settings;
    }
}
