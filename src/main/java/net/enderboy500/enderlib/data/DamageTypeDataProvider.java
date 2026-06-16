package net.enderboy500.enderlib.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class DamageTypeDataProvider extends FabricCodecDataProvider<DamageType> {

    public DamageTypeDataProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, "damage_type", DamageType.DIRECT_CODEC);
    }

    protected abstract void generate(Output output);

    @Override
    protected final void configure(BiConsumer<Identifier, DamageType> provider, HolderLookup.Provider registryLookup) {
        this.generate((id, scaling, exhaustion, effects, deathMessageType) -> provider.accept(id, new DamageType(id.getPath(), scaling, exhaustion, effects, deathMessageType)));
    }

    @FunctionalInterface
    public interface Output {
        void accept(Identifier identifier, DamageScaling scaling, float exhaustion, DamageEffects effects, DeathMessageType deathMessageType);

        default void accept(Identifier identifier , DamageScaling scaling, float exhaustion) {
            this.accept(identifier, scaling, exhaustion, DamageEffects.HURT, DeathMessageType.DEFAULT);
        }
    }
}
