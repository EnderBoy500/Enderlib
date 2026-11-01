package net.enderboy500.enderlib.item.skin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSkinRegistry {
    private static final Map<Item, List<ItemSkin>> map = new HashMap<>();

    public static void register(List<Item> items, List<ItemSkin> itemSkins) {
        for (Item item : items) {
            List<ItemSkin> itemSkinList = new ArrayList<>();
            itemSkinList.addFirst(new ItemSkin(item.getDescriptionId(), item.getDefaultInstance().get(DataComponents.ITEM_MODEL)));
            itemSkinList.addAll(itemSkins);
            map.put(item, itemSkinList);
        }
    }
    public static void register(Item item, List<ItemSkin> itemSkins) {
        register(List.of(item), itemSkins);
    }
    public static void register(List<Item> items, ItemSkin itemSkin) {
        register(items, List.of(itemSkin));
    }
    public static void register(Item item, ItemSkin itemSkin) {
        register(List.of(item), List.of(itemSkin));
    }

    public static Map<Item, List<ItemSkin>> getMap() {
        return map;
    }

}
