package net.enderboy500.enderlib.helper;

import net.enderboy500.enderlib.ELib;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public class RegistryHelper {
    public static Item registerItem(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ELib.customId(id));
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }
    public static Item registerItem(String id, Item.Properties settings) {
        return registerItem(id, Item::new, settings);
    }
    public static Item registerItem(String id) {
        return registerItem(id, Item::new, new Item.Properties());
    }
    public static <B extends BlockEntity> BlockEntityType<? extends B> registerBlockEntity(String id, FabricBlockEntityTypeBuilder.Factory<B> blockEntityFunction, Block... block) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ELib.customId(id),
                FabricBlockEntityTypeBuilder.create(blockEntityFunction, block).build(null));
    }
    public static <B extends Block> B registerBlock(String id, Function<Properties, B> factory, BlockBehaviour.Properties settings) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, ELib.customId(id));
        B block = factory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
    public static Block registerBlockAsCopyOf(String id, Block block) {
        return registerBlock(id, Block::new, Properties.ofFullCopy(block));
    }
    public static <B extends Block> B registerBlockWithItem(String id, Function<Properties, B> factory, BlockBehaviour.Properties settings) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, ELib.customId(id));
        B block = factory.apply(settings.setId(key));
        registerBlockItem(id, block);
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
    public static Block registerBlockAsACopyOfBlockWithItem(String id, Block block) {
        return registerBlockWithItem(id, Block::new, Properties.ofFullCopy(block));
    }
    private static <I extends Item> I registerBlockItem(String id, Function<Item.Properties, I> factory, Item.Properties settings) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ELib.customId(id));
        I item = factory.apply(settings.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
    public static BlockItem registerBlockItem(String id, Block block) {
        return registerBlockItem(id , settings -> new BlockItem(block, settings), new Item.Properties().useBlockDescriptionPrefix());
    }

    public static ResourceKey<DamageType> registerDamageType(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ELib.customId(id));
    }

    public static SimpleParticleType registerParticleType(String id, SimpleParticleType particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, ELib.customId(id), particleType);
    }
    public static Holder<Potion> registerPotion(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Identifier.withDefaultNamespace(id), potion);
    }
    public static RecipeSerializer<? extends Recipe<?>> registerRecipeSerializer(String id, RecipeSerializer recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ELib.customId(id), recipeSerializer);
    }
    public static RecipeType<? extends Recipe<?>> registerRecipeType(String id, RecipeType recipeType) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, ELib.customId(id), recipeType);
    }
    public static MenuType<?> registerScreenHandler(String id, ExtendedScreenHandlerType extendedScreenHandlerType) {
        return Registry.register(BuiltInRegistries.MENU, ELib.customId(id),
                extendedScreenHandlerType);
    }
    public static Holder<MobEffect> registerEffect(String id, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ELib.customId(id), effect);
    }
    public static TagKey<Block> registerBlockTags(String id) {
        return TagKey.create(Registries.BLOCK, ELib.customId(id));
    }
    public static TagKey<Item> registerItemTags(String id) {
        return TagKey.create(Registries.ITEM, ELib.customId(id));
    }
    public static TagKey<MobEffect> registerEffectTags(String id) {
        return TagKey.create(Registries.MOB_EFFECT, ELib.customId(id));
    }
    public static TagKey<Biome> registerBiomeTags(String id) {
        return TagKey.create(Registries.BIOME, ELib.customId(id));
    }
    public static TagKey<EntityType<?>> registerEntityTags(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, ELib.customId(id));
    }
    public static SoundEvent registerSound(String id) {
        Identifier name = ELib.customId(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(name));
    }

    public static Holder.Reference<SoundEvent> registerSoundReference(String id) {
        return registerReference(ELib.customId(id));
    }
    private static Holder.Reference<SoundEvent> registerReference(Identifier id) {
        return registerReference(id, id);
    }
    private static Holder.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(soundId));
    }
    public static <T>DataComponentType<T> registerDataComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ELib.customId(id),
                builderOperator.apply(DataComponentType.builder()).build());
    }
    public static ResourceKey<LootTable> registerVanillaLootTable(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace(path));
    }
    public static ResourceKey<LootTable> registerCustomLootTable(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, ELib.customId(path));
    }
    public static <T extends Fluid> T registerFluid(String id, T value) {
        return (Registry.register(BuiltInRegistries.FLUID, id, value));
    }
    public static ResourceKey<Biome> registerBiome(String id) {
        return ResourceKey.create(Registries.BIOME, ELib.customId(id));
    }
    public static ResourceKey<LevelStem> registerDimensionOptions(String id) {
        return ResourceKey.create(Registries.LEVEL_STEM, ELib.customId(id));
    }
    public static ResourceKey<Level> registerWorld(String id) {
        return ResourceKey.create(Registries.DIMENSION, ELib.customId(id));
    }
    public static ResourceKey<DimensionType> registerDimensionType(String id) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, ELib.customId(id));
    }
}