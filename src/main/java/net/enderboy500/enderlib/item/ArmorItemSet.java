package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ArmorItemSet(Item helmet, Item chestplate, Item leggings, Item boots) {

    public static ArmorItemSet create(String prefix, ArmorMaterial armorMaterial) {
        List<Item> items = new ArrayList<>();
        List<String> suffixes = new ArrayList<>();
        suffixes.add("helmet");
        suffixes.add("chestplate");
        suffixes.add("leggings");
        suffixes.add("boots");
        List<EquipmentType> equipmentTypes = new ArrayList<>();
        equipmentTypes.add(EquipmentType.HELMET);
        equipmentTypes.add(EquipmentType.CHESTPLATE);
        equipmentTypes.add(EquipmentType.LEGGINGS);
        equipmentTypes.add(EquipmentType.BOOTS);
            for (int i = 0; i < suffixes.size(); i++) {
                Item item = armorItem(prefix + "_" + suffixes.get(i), equipmentTypes.get(i), armorMaterial);
                items.add(item);
            }
        return compile(items);
    }

    private static ArmorItemSet compile(List<Item> items) {
        return new ArmorItemSet(
                items.get(0),
                items.get(1),
                items.get(2),
                items.get(3)
        );
    }

    public void forEach(Consumer<Item> action) {
        Stream.of(this.helmet, this.chestplate, this.leggings, this.boots).forEach(action);
    }

    private static Item armorItem(String id, EquipmentType equipmentType, ArmorMaterial armorMaterial) {
        return rawRegister(id, new Item(settings(id).armor(armorMaterial, equipmentType)));
    }

    private static Item rawRegister(String name, Item item) {
        return Registry.register(Registries.ITEM, EnderLib.customId(name), item);
    }

    private static Item.Settings settings(String name) {
        Item.Settings settings = new Item.Settings();
        settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, EnderLib.customId(name)));
        return settings;
    }
}
