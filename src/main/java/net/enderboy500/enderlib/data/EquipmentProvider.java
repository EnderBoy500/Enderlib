package net.enderboy500.enderlib.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class EquipmentProvider extends FabricCodecDataProvider<EquipmentClientInfo> {

    public EquipmentProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "equipment", EquipmentClientInfo.CODEC);
    }

    protected abstract void generate(Output output);

    @Override
    protected final void configure(BiConsumer<Identifier, EquipmentClientInfo> provider, HolderLookup.Provider registryLookup) {
        this.generate((key, info) -> provider.accept(key.identifier(), info));
    }

    @FunctionalInterface
    public interface Output {
        void add(ResourceKey<EquipmentAsset> key, EquipmentClientInfo info);

        default void add(ResourceKey<EquipmentAsset> key, EquipmentClientInfo.Builder infoBuilder) {
            this.add(key, infoBuilder.build());
        }
        default void addHumanoid(ResourceKey<EquipmentAsset> key, Identifier identifier) {
            this.add(key, EquipmentClientInfo.builder().addHumanoidLayers(identifier).build());
        }
        default void addHorse(ResourceKey<EquipmentAsset> key, Identifier identifier, boolean dyable) {
            this.add(key, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.HORSE_BODY, EquipmentClientInfo.Layer.leatherDyeable(identifier, dyable)).build());
        }
        default void addNautilus(ResourceKey<EquipmentAsset> key, Identifier identifier, boolean dyable) {
            this.add(key, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.NAUTILUS_BODY, EquipmentClientInfo.Layer.leatherDyeable(identifier, dyable)).build());
        }
        default void addHappyGhast(ResourceKey<EquipmentAsset> key, Identifier identifier, boolean dyable) {
            this.add(key, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.HAPPY_GHAST_BODY, EquipmentClientInfo.Layer.leatherDyeable(identifier, dyable)).build());
        }
    }
}
