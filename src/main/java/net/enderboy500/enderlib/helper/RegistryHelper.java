package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.block.AbstractBlock.Settings;

import java.util.function.Function;


public class RegistryHelper {

    public static Item registerItem(String modId, String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modId, id));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static BlockEntityType<?> registerBlockEntity(String modId, String id, FabricBlockEntityTypeBuilder.Factory<?> blockEntityFunction, Block... block) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modId, id),
                FabricBlockEntityTypeBuilder.create(blockEntityFunction, block).build(null));
    }

    public static <B extends Block> B registerBlock(String modId, String id, Function<Settings, B> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modId, id));
        B block = factory.apply(settings.registryKey(key));

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static <I extends Item> I registerBlockItem(String modId,String id, Function<Item.Settings, I> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modId, id));
        I item = factory.apply(settings.registryKey(key));

        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, blockItem);
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    public static BlockItem registerBlockItem(String modId, String id, Block block) {
        return registerBlockItem(modId,id , settings -> new BlockItem(block, settings), new Item.Settings().useBlockPrefixedTranslationKey());
    }

    public static RegistryKey<DamageType> registerDamageType(Identifier identifier) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, identifier);
    }

    public static <T extends Entity> EntityType<T> registerEntity(String modId ,String path, EntityType.Builder<T> entityTypeBuilder) {
        Identifier id = Identifier.of(modId, path);
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
        return Registry.register(Registries.ENTITY_TYPE, key, entityTypeBuilder.build(key));
    }

    public static SimpleParticleType registerParticleType(String modId ,String id, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(modId, id), particleType);
    }

    public static RegistryEntry<Potion> registerPotion(String id, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.ofVanilla(id), potion);
    }

    public static RecipeSerializer<?> registerRecipeSerializer(Identifier identifier, RecipeSerializer recipeSerializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, identifier, recipeSerializer);
    }

    public static RecipeType<?> registerRecipeType(Identifier identifier, RecipeType recipeType) {
        return Registry.register(Registries.RECIPE_TYPE, identifier, recipeType);
    }

    public static ScreenHandlerType<?> registerScreenHandler(Identifier identifier, ExtendedScreenHandlerType extendedScreenHandlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, identifier,
                extendedScreenHandlerType);
    }

    public static RegistryEntry<StatusEffect> registerEffect(String modId,String id, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(modId, id), effect);
    }
}
