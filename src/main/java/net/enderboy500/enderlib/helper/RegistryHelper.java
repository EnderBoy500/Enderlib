package net.enderboy500.enderlib.helper;

import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;

import java.util.function.Function;
import java.util.function.UnaryOperator;


public class RegistryHelper {
    public static Item registerItem(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, EnderLib.customId(id));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }
    public static BlockEntityType<?> registerBlockEntity(String id, FabricBlockEntityTypeBuilder.Factory<?> blockEntityFunction, Block... block) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, EnderLib.customId(id),
                FabricBlockEntityTypeBuilder.create(blockEntityFunction, block).build(null));
    }
    public static <B extends Block> B registerBlock(String id, Function<Settings, B> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, EnderLib.customId(id));
        B block = factory.apply(settings.registryKey(key));

        return Registry.register(Registries.BLOCK, key, block);
    }
    private static <I extends Item> I registerBlockItem(String id, Function<Item.Settings, I> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, EnderLib.customId(id));
        I item = factory.apply(settings.registryKey(key));

        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, blockItem);
        }

        return Registry.register(Registries.ITEM, key, item);
    }
    public static BlockItem registerBlockItem(String id, Block block) {
        return registerBlockItem(id , settings -> new BlockItem(block, settings), new Item.Settings().useBlockPrefixedTranslationKey());
    }
    public static RegistryKey<DamageType> registerDamageType(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, EnderLib.customId(id));
    }
    public static <T extends Entity> EntityType<T> registerEntity(String id, EntityType.Builder<T> entityTypeBuilder) {
        Identifier path = EnderLib.customId(id);
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, path);
        return Registry.register(Registries.ENTITY_TYPE, key, entityTypeBuilder.build(key));
    }
    public static SimpleParticleType registerParticleType(String id, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, EnderLib.customId(id), particleType);
    }
    public static RegistryEntry<Potion> registerPotion(String id, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.ofVanilla(id), potion);
    }
    public static RecipeSerializer<?> registerRecipeSerializer(String id, RecipeSerializer recipeSerializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, EnderLib.customId(id), recipeSerializer);
    }
    public static RecipeType<?> registerRecipeType(String id, RecipeType recipeType) {
        return Registry.register(Registries.RECIPE_TYPE, EnderLib.customId(id), recipeType);
    }
    public static ScreenHandlerType<?> registerScreenHandler(String id, ExtendedScreenHandlerType extendedScreenHandlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, EnderLib.customId(id),
                extendedScreenHandlerType);
    }
    public static RegistryEntry<StatusEffect> registerEffect(String id, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, EnderLib.customId(id), effect);
    }
    public static TagKey<Block> registerBlockTags(String id) {
        return TagKey.of(RegistryKeys.BLOCK, EnderLib.customId(id));
    }
    public static TagKey<Item> registerItemTags(String id) {
        return TagKey.of(RegistryKeys.ITEM, EnderLib.customId(id));
    }
    public static TagKey<Biome> registerBiomeTags(String id) {
        return TagKey.of(RegistryKeys.BIOME, EnderLib.customId(id));
    }
    public static TagKey<EntityType<?>> registerEntityTags(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, EnderLib.customId(id));
    }
    public static SoundEvent registerSound(String id) {
        Identifier name = EnderLib.customId(id);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(name));
    }

    public static RegistryEntry.Reference<SoundEvent> registerSoundReference(String id) {
        return registerReference(EnderLib.customId(id));
    }
    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
        return registerReference(id, id);
    }
    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }
    public static <T>ComponentType<T> registerDataComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, EnderLib.customId(id),
                builderOperator.apply(ComponentType.builder()).build());
    }
    public static RegistryKey<LootTable> registerVanillaLootTable(String path) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla(path));
    }
    public static RegistryKey<LootTable> registerCustomLootTable(String path) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, EnderLib.customId(path));
    }
    public static <T extends Fluid> T registerFluid(String id, T value) {
        return (Registry.register(Registries.FLUID, id, value));
    }
    public static RegistryKey<Biome> registerBiome(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, EnderLib.customId(id));
    }
    public static RegistryKey<DimensionOptions> registerDimensionOptions(String id) {
        return RegistryKey.of(RegistryKeys.DIMENSION, EnderLib.customId(id));
    }
    public static RegistryKey<World> registerWorld(String id) {
        return RegistryKey.of(RegistryKeys.WORLD, EnderLib.customId(id));
    }
    public static RegistryKey<DamageType> registerDimensionType(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, EnderLib.customId(id));
    }
}