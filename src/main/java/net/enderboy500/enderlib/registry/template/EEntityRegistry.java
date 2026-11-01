package net.enderboy500.enderlib.registry.template;

import net.enderboy500.enderlib.util.AutoLang;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EEntityRegistry {
    public static Map<String, List<EntityType<?>>> registeryEntityMap = new HashMap<>();
    private static List<EntityType<?>> entityTypes = new ArrayList<>();

    private final String modId;
    public EEntityRegistry(String modId) {
        this.modId = modId;
    }

    public <T extends Entity> EntityType<T> registerEntity(String id, EntityType.Builder<T> builder, boolean autoLang) {
        ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(modId,id));
        EntityType<T> entityType = Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, builder.build(resourceKey));
        if (autoLang) AutoLang.entityTypeList.add(entityType);

        entityTypes.add(entityType);
        registeryEntityMap.put(modId, entityTypes);

        return entityType;
    }

    public <T extends Entity> EntityType<T> registerProjectile(String id, EntityType.EntityFactory<T> entityFactory, boolean autoLang) {
        return registerEntity(id, EntityType.Builder.of(entityFactory, MobCategory.MISC).noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), autoLang);
    }

    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, FabricBlockEntityTypeBuilder.Factory<T> blockEntityFunction, Block... block) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(modId,id),
                FabricBlockEntityTypeBuilder.create(blockEntityFunction, block).build(null));
    }

}
