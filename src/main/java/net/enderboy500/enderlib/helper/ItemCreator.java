package net.enderboy500.enderlib.helper;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.ArmorItemSet;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ItemCreator {

    public static Item defaultItem(String id) {
        return rawRegister(id, new Item(settings(id)));
    }
    public static Item defaultItem(String id, Item item) {
        return rawRegister(id, item);
    }
    public static Item defaultItem(String id, Item.Settings settings) {
        return rawRegister(id, new Item(settings));
    }
    public static Item armorItem(String id, EquipmentType equipmentType, ArmorMaterial armorMaterial) {
        return rawRegister(id, new Item(settings(id).armor(armorMaterial, equipmentType)));
    }

    public static ArmorItemSet armorItem(String id, ArmorMaterial armorMaterial) {
        return ArmorItemSet.create(id, armorMaterial);
    }

    public static Item rawRegister(String name, Item item) {
        return Registry.register(Registries.ITEM, EnderLib.customId(name), item);
    }

    public static Item.Settings settings(String name) {
        Item.Settings settings = new Item.Settings();
        settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, EnderLib.customId(name)));
        return settings;
    }
}
