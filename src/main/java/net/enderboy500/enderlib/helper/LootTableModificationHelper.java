package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootTableModificationHelper {

    public static void addLootTableModification(ResourceKey<LootTable> lootTableRegistryKey, Item item, int weight, float minPerChest, float maxPerChest) {
        LootTableEvents.MODIFY.register(((registryKey, builder, lootTableSource, wrapperLookup) -> {
            if (lootTableSource.isBuiltin() && lootTableRegistryKey.equals(registryKey)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(item).setWeight(weight))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minPerChest, maxPerChest)).build());
                builder.withPool(poolBuilder);
            }
        }));
    }
}
