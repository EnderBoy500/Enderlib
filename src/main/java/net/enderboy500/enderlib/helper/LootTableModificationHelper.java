package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;

public class LootTableModificationHelper {

    public static void addLootTableModification(RegistryKey<LootTable> lootTableRegistryKey, Item item, int weight, float minPerChest, float maxPerChest) {
        LootTableEvents.MODIFY.register(((registryKey, builder, lootTableSource, wrapperLookup) -> {
            if (lootTableSource.isBuiltin() && lootTableRegistryKey.equals(registryKey)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(item).weight(weight))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minPerChest, maxPerChest)).build());
                builder.pool(poolBuilder);
            }
        }));
    }
}
