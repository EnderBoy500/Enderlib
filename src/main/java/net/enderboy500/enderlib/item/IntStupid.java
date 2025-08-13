package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class IntStupid {
    public static final Item TEST = register("test", TestToolItem::new, new Item.Settings().maxCount(1));

    public static Item register(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EnderLib.MOD_ID, id));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return register(id, factory, new Item.Settings());
    }

    public static Item register(String id) {
        return register(id, Item::new, new Item.Settings());
    }

    public static void loadItems() {}
}
