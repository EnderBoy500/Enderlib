package net.enderboy500.enderlib.registry.template;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

import java.util.function.UnaryOperator;

public class EUnvariedRegistry {
    private final String modId;
    public EUnvariedRegistry(String modId) {
        this.modId = modId;
    }
    
    // TYPES

    public ResourceKey<DamageType> registerDamageType(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(modId, id));
    }
    public SimpleParticleType registerParticleType(String id, SimpleParticleType particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(modId, id), particleType);
    }
    public <T> DataComponentType<T> registerDataComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(modId, id),
                builderOperator.apply(DataComponentType.builder()).build());
    }
    public <T> DataComponentType<T> registerDataComponent(String id, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(modId, id),
                DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build());
    }

    // BE-RELATED

    public RecipeSerializer<? extends Recipe<?>> registerRecipeSerializer(String id, RecipeSerializer recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(modId, id), recipeSerializer);
    }
    public RecipeType<? extends Recipe<?>> registerRecipeType(String id, RecipeType recipeType) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(modId, id), recipeType);
    }
    public MenuType<?> registerScreenHandler(String id, ExtendedScreenHandlerType extendedScreenHandlerType) {
        return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(modId, id),
                extendedScreenHandlerType);
    }

    // EFFECTS & POTIONS

    public Holder<MobEffect> registerEffect(String id, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(modId, id), effect);
    }

    public Holder<Potion> registerPotion(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Identifier.withDefaultNamespace(id), potion);
    }

    // TAGS

    public TagKey<Block> registerBlockTags(String id) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<Item> registerItemTags(String id) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modId, id));
    }

    public TagKey<MobEffect> registerEffectTags(String id) {
        return TagKey.create(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<Biome> registerBiomeTags(String id) {
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<EntityType<?>> registerEntityTags(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<Enchantment> registerEnchantmentTag(String id) {
        return TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<BannerPattern> registerBannerPatternTag(String id) {
        return TagKey.create(Registries.BANNER_PATTERN, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<DamageType> registerDamageTypeTag(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<Fluid> registerFluidTag(String id) {
        return TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<PaintingVariant> registerPaintingVariantTag(String id) {
        return TagKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<PoiType> registerPOITypeTag(String id) {
        return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath(modId, id));
    }
    public TagKey<Structure> registerStructureTag(String id) {
        return TagKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(modId, id));
    }

    // SOUND

    public SoundEvent registerSound(String id) {
        Identifier name = Identifier.fromNamespaceAndPath(modId, id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(name));
    }

    public Holder.Reference<SoundEvent> registerSoundReference(String id) {
        return registerReference(Identifier.fromNamespaceAndPath(modId, id));
    }
    private Holder.Reference<SoundEvent> registerReference(Identifier id) {
        return registerReference(id, id);
    }
    private Holder.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(soundId));
    }

    // Other
    public <T> ResourceKey<Registry<T>> registerRegistryKey(String string) {
        return ResourceKey.createRegistryKey(Identifier.parse(string));
    }

    public <Value> EnvironmentAttribute<Value> registerEnvironmentAttribute(String id, EnvironmentAttribute.Builder<Value> builder) {
        EnvironmentAttribute<Value> environmentAttribute = builder.build();
        Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Identifier.fromNamespaceAndPath(modId, id), environmentAttribute);
        return environmentAttribute;
    }

    private Holder<Attribute> registerAttribute(String id, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Identifier.fromNamespaceAndPath(modId, id), attribute);
    }

    public <T> EntityDataSerializer<T> registerEntityDataSerializer(String id, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
        var handler = EntityDataSerializer.forValueType(codec);
        FabricTrackedDataRegistry.register(Identifier.fromNamespaceAndPath(modId, id), handler);
        return handler;
    }
}
