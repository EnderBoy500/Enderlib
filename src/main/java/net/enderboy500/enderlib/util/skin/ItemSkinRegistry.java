package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSkinRegistry {
    private static final Map<Item, List<ItemSkin>> MAP = new HashMap<>();

    public static void registerSkins(Item item, List<ItemSkin> skinSet) {
        for (ItemSkin skin : skinSet) {
            MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
        }
        item.getDefaultInstance().set(EnderLibComponents.SKIN_ID, 0);
    }

    public static void registerSkin(List<Item> items, List<ItemSkin> skinSet) {
        for (Item item : items) {
            for (ItemSkin skin : skinSet) {
                MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
            }
            item.getDefaultInstance().set(EnderLibComponents.SKIN_ID, 0);
        }
    }

    public static void registerSkin(Item item, ItemSkin skin) {
        MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
        item.getDefaultInstance().set(EnderLibComponents.SKIN_ID, 0);
    }

    public static void registerSkin(List<Item> items, ItemSkin skin) {
        for (Item item : items) {
            MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
            item.getDefaultInstance().set(EnderLibComponents.SKIN_ID, 0);
        }
    }

    public static Map<Item, List<ItemSkin>> getMap() {
        return MAP;
    }

}
