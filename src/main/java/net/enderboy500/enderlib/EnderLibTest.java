package net.enderboy500.enderlib;

import net.enderboy500.enderlib.helper.RegistryHelper;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class EnderLibTest {
    public static final Item TEST = RegistryHelper.registerItem("test", TestItem::new, new Item.Properties());
    public static void load() {
    }
}
