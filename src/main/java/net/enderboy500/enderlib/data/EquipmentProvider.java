package net.enderboy500.enderlib.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        void accept(ResourceKey<EquipmentAsset> key, EquipmentClientInfo info);

        default void accept(ResourceKey<EquipmentAsset> key, EquipmentClientInfo.Builder infoBuilder) {
            this.accept(key, infoBuilder.build());
        }
    }
}
