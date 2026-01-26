package net.enderboy500.enderlib.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CustomCrossbowItemModel {
    private static Map<Item, Identifier> ammo = new HashMap<>();

    public static void addNewModel(Item item, Identifier name) {
        ammo.put(item, name);
    }

    public static boolean getAmmo(Item item) {
        return ammo.containsKey(item);
    }


    public static Identifier getId(Item item) {
        return ammo.get(item);
    }
}
