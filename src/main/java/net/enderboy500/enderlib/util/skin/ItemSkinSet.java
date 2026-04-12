package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSkinSet {
    private final List<ItemSkin> skins;
    private static final Map<Item, List<ItemSkin>> MAP = new HashMap<>();

    public ItemSkinSet(List<ItemSkin> skins) {
        this.skins = skins;
    }

    public static void registerSkinSet(Item item, ItemSkinSet skinSet) {
        for (ItemSkin skin : skinSet.getSkins()) {
            MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
        }
        item.getDefaultStack().set(EnderLibComponents.SKIN_ID, 0);
    }
    public static void registerSkinSet(Item item, List<ItemSkin> skinSet) {
        for (ItemSkin skin : skinSet) {
            MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
        }
        item.getDefaultStack().set(EnderLibComponents.SKIN_ID, 0);
    }
    public static void registerSkinSet(List<Item> items, ItemSkinSet skinSet) {
        for (Item item : items) {
            for (ItemSkin skin : skinSet.getSkins()) {
                MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
            }
            item.getDefaultStack().set(EnderLibComponents.SKIN_ID, 0);
        }
    }
    public static void registerSkinSet(List<Item> items, List<ItemSkin> skinSet) {
        for (Item item : items) {
            for (ItemSkin skin : skinSet) {
                MAP.computeIfAbsent(item, k -> new ArrayList<>()).add(skin);
            }
            item.getDefaultStack().set(EnderLibComponents.SKIN_ID, 0);
        }
    }

    public static Map<Item, List<ItemSkin>> getMap() {
        return MAP;
    }

    public List<ItemSkin> getSkins() {
        return skins;
    }
}
