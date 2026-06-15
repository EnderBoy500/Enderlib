package net.enderboy500.enderlib.item;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class CustomCrossbowItemModel {
    private static final Map<Item, Identifier> modelApplier = new HashMap<>();

    public static void addNewModel(Item item, Identifier name) {
        modelApplier.put(item, name);
    }

    public static boolean getModelApplier(Item item) {
        return modelApplier.containsKey(item);
    }


    public static Identifier getId(Item item) {
        return modelApplier.get(item);
    }
}
