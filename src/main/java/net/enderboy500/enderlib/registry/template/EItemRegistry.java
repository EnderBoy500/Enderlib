package net.enderboy500.enderlib.registry.template;

import net.enderboy500.enderlib.util.AutoLang;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EItemRegistry {
    public static Map<String, List<Item>> registeryItemMap = new HashMap<>();
    private static List<Item> items = new ArrayList<>();

    private final String modId;
    public EItemRegistry(String modId) {
        this.modId = modId;
    }

    public Item registerItem(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings, boolean autoLang, @Nullable ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modId, id));
        Item item = itemFactory.apply(settings.setId(itemKey));
        if (autoLang) AutoLang.itemList.add(item);

        if (creativeModeTabResourceKey != null) ItemGroupEvents.modifyEntriesEvent(creativeModeTabResourceKey).register(entries -> {
            entries.accept(item);
        });

        items.add(item);
        registeryItemMap.put(modId, items);
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }
    public Item registerItem(String id, Item.Properties settings, boolean autoLang, @Nullable ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        return registerItem(id, Item::new, settings, autoLang, creativeModeTabResourceKey);
    }
    public Item registerItem(String id, boolean autoLang, @Nullable ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        return registerItem(id, Item::new, new Item.Properties(), autoLang, creativeModeTabResourceKey);
    }
    public Item registerItem(String id, Item.Properties settings, boolean autoLang) {
        return registerItem(id, Item::new, settings, autoLang, null);
    }
    public Item registerItem(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings, boolean autoLang) {
        return registerItem(id, Item::new, settings, autoLang, null);
    }
    public Item registerItem(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        return registerItem(id, Item::new, settings, false, null);
    }
    public Item registerItem(String id, boolean autoLang) {
        return registerItem(id, Item::new, new Item.Properties(), autoLang, null);
    }
    public Item registerBlockItem(String id, Block block, ResourceKey<CreativeModeTab> creativeModeTabResourceKey) {
        return registerItem(id , settings -> new BlockItem(block, settings), new Item.Properties().useBlockDescriptionPrefix(), false, creativeModeTabResourceKey);
    }
    public Item registerBlockItem(String id, Block block) {
        return registerItem(id , settings -> new BlockItem(block, settings), new Item.Properties().useBlockDescriptionPrefix(), false, null);
    }
}
