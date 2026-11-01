package net.enderboy500.enderlib.data;

import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class EnchantmentDataProvider extends FabricDynamicRegistryProvider {


    public EnchantmentDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
    }

    public static void register(BootstrapContext<Enchantment> registry) {};

    @Override
    public String getName() {
        return "";
    }

    protected static void add(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.EnchantmentDefinition definition, HolderSet<Enchantment> exclusiveSet, DataComponentMap effects) {
        registry.register(key, new Enchantment(Component.translatable("enchantment." + key.identifier().toLanguageKey()), definition, exclusiveSet, effects));
    }
    protected static void add(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, TagKey<Item> compatibleItems, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, HolderSet<Enchantment> exclusiveSet, DataComponentMap effects, EquipmentSlotGroup... slots) {
        add(registry, key, Enchantment.definition(registry.lookup(Registries.ITEM).getOrThrow(compatibleItems), weight,
                maxLevel, minCost, maxCost, anvilCost, slots
        ), exclusiveSet, effects);
    }
    protected static void add(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,  TagKey<Item> compatibleItems, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, TagKey<Enchantment> exclusiveSet, DataComponentMap effects, EquipmentSlotGroup... slots) {
        add(registry, key, compatibleItems, weight,
                maxLevel, minCost, maxCost, anvilCost,registry.lookup(Registries.ENCHANTMENT).getOrThrow(exclusiveSet), effects, slots);
    }
    protected static void add(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, TagKey<Item> compatibleItems, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, DataComponentMap effects, EquipmentSlotGroup... slots) {
        add(registry, key, Enchantment.definition(registry.lookup(Registries.ITEM).getOrThrow(compatibleItems), weight,
                maxLevel, minCost, maxCost, anvilCost, slots
        ), HolderSet.empty(), effects);
    }
}
