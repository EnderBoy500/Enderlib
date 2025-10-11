package net.enderboy500.enderlib.test;

import net.enderboy500.enderlib.helper.RegistryHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;

public class TestInit {
    public static final Item TEST = RegistryHelper.registerItem("test", TestArmorItem::new, new Item.Settings().armor(ArmorMaterials.DIAMOND, EquipmentType.CHESTPLATE));
    public static void init() {}
}
