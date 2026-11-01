package net.enderboy500.enderlib.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class MobEffectTagProvider extends FabricTagProvider.FabricValueLookupTagProvider<MobEffect> {

    public MobEffectTagProvider(FabricDataOutput output,  CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.MOB_EFFECT, registriesFuture, mobEffect -> BuiltInRegistries.MOB_EFFECT.getResourceKey(mobEffect).get());
    }
}
