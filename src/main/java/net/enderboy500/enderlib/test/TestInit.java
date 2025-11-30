package net.enderboy500.enderlib.test;

import net.enderboy500.enderlib.helper.RegistryHelper;
import net.minecraft.item.Item;

public class TestInit {

    public static final Item TEST = RegistryHelper.registerItem("test", TestItem::new, new Item.Settings());
    public static void init() {}
}
