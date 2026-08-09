package net.enderboy500.enderlib.blocks;

import net.enderboy500.enderlib.ELib;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.color.item.Dye;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ColorMapColorUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.ImmutableBiMap;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public record ColoredBlockFamily(String string, Block white, Block lightGray, Block gray, Block black, Block brown, Block red, Block orange, Block yellow, Block lime, Block green,
                                 Block cyan, Block lightBlue, Block blue, Block purple, Block magenta, Block pink, boolean hasItem) {
    static List<DyeColor> colors = List.of(DyeColor.WHITE, DyeColor.LIGHT_GRAY,DyeColor.GRAY,DyeColor.BLACK, DyeColor.BROWN,DyeColor.RED,DyeColor.ORANGE,DyeColor.YELLOW,DyeColor.LIME,
            DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK);

    public static void addToColoredInventory(Item afterItem, ColoredBlockFamily coloredBlockFamily) {
        if (coloredBlockFamily.hasItem) {
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
                entries.addAfter(afterItem, coloredBlockFamily.white.asItem(), coloredBlockFamily.lightGray.asItem(), coloredBlockFamily.gray.asItem(), coloredBlockFamily.black.asItem(),
                        coloredBlockFamily.brown.asItem(), coloredBlockFamily.red.asItem(), coloredBlockFamily.orange.asItem(), coloredBlockFamily.yellow.asItem(), coloredBlockFamily.lime.asItem(),
                        coloredBlockFamily.green.asItem(), coloredBlockFamily.cyan.asItem(), coloredBlockFamily.lightBlue.asItem(), coloredBlockFamily.blue.asItem(),
                        coloredBlockFamily.purple.asItem(), coloredBlockFamily.magenta.asItem(), coloredBlockFamily.pink.asItem());
            });
        } else System.out.println(coloredBlockFamily.string + " has no items");
    }

    public static void addToColoredInventory(Block afterItem, ColoredBlockFamily coloredBlockFamily) {
        if (coloredBlockFamily.hasItem) {
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
                entries.addAfter(afterItem.asItem(), coloredBlockFamily.white.asItem(), coloredBlockFamily.lightGray.asItem(), coloredBlockFamily.gray.asItem(), coloredBlockFamily.black.asItem(),
                        coloredBlockFamily.brown.asItem(), coloredBlockFamily.red.asItem(), coloredBlockFamily.orange.asItem(), coloredBlockFamily.yellow.asItem(), coloredBlockFamily.lime.asItem(),
                        coloredBlockFamily.green.asItem(), coloredBlockFamily.cyan.asItem(), coloredBlockFamily.lightBlue.asItem(), coloredBlockFamily.blue.asItem(),
                        coloredBlockFamily.purple.asItem(), coloredBlockFamily.magenta.asItem(), coloredBlockFamily.pink.asItem());
            });
        } else System.out.println(coloredBlockFamily.string + " has no items");
    }

    public static ColoredBlockFamily create(String suffix, Block copy, boolean withItem) {
        return create(suffix,Block::new, copy, withItem,null);
    }

    public static ColoredBlockFamily create(String suffix, Function<BlockBehaviour.Properties, Block> factory, Block copy, boolean withItem) {
        return create(suffix,factory, copy,withItem, null);
    }

    public static ColoredBlockFamily create(String suffix, Function<BlockBehaviour.Properties, Block> factory, Block copy, boolean withItem, @Nullable BlockEntityType<?> blockEntity) {
        List<Block> blocks = new ArrayList<>();
        for (DyeColor color : colors) {
            String name = color.getName() + "_" + suffix;
            Block block;
            if (withItem) block = RegistryHelper.registerBlockWithItem(name, factory, coloredSettings(color, name, copy));
            else block = RegistryHelper.registerBlock(name, factory, coloredSettings(color, name, copy));
            blocks.add(block);

            if (blockEntity != null) blockEntity.addSupportedBlock(block);
        }
        return new ColoredBlockFamily(
                suffix,
                blocks.get(0),
                blocks.get(1),
                blocks.get(2),
                blocks.get(3),
                blocks.get(4),
                blocks.get(5),
                blocks.get(6),
                blocks.get(7),
                blocks.get(8),
                blocks.get(9),
                blocks.get(10),
                blocks.get(11),
                blocks.get(12),
                blocks.get(13),
                blocks.get(14),
                blocks.get(15),
                withItem
        );
    }

    public static List<Block> getBlocks(ColoredBlockFamily coloredBlockFamily) {
        return List.of(coloredBlockFamily.white, coloredBlockFamily.lightGray, coloredBlockFamily.gray, coloredBlockFamily.black,
                coloredBlockFamily.brown, coloredBlockFamily.red, coloredBlockFamily.orange, coloredBlockFamily.yellow, coloredBlockFamily.lime,
                coloredBlockFamily.green, coloredBlockFamily.cyan, coloredBlockFamily.lightBlue, coloredBlockFamily.blue,
                coloredBlockFamily.purple, coloredBlockFamily.magenta, coloredBlockFamily.pink);
    }

    public static BlockBehaviour.Properties coloredSettings(DyeColor color, String name, Block copiedBlock) {
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.ofFullCopy(copiedBlock);
        settings.setId(ResourceKey.create(Registries.BLOCK, ELib.customId(name))).mapColor(color);
        settings.overrideLootTable(Optional.of(ResourceKey.create(Registries.LOOT_TABLE, ELib.customId("blocks/" + name.replace("_wall", "")))));

        return settings;
    }
}
