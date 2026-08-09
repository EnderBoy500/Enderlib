package net.enderboy500.enderlib;

import net.enderboy500.enderlib.blocks.ColoredBlockFamily;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class EnderLibTest {
    public static final Item TEST = RegistryHelper.registerItem("test", TestItem::new, new Item.Properties());
    public static final ColoredBlockFamily MORTAR = ColoredBlockFamily.create("mortar", Blocks.MUD, false);
    public static void load() {
        ColoredBlockFamily.addToColoredInventory(Items.PINK_CANDLE, MORTAR);
    }
}
