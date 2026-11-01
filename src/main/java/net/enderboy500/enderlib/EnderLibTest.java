package net.enderboy500.enderlib;

import net.enderboy500.enderlib.item.TestItem;
import net.enderboy500.enderlib.registry.template.EItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;

public class EnderLibTest {
    static EItemRegistry eItemRegistry = new EItemRegistry(EnderLib.MOD_ID);

    public static final Item ARMOR = eItemRegistry.registerItem("armor", TestItem::new, new Item.Properties().humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.HELMET), true, null);

    public static void load() {}
}
