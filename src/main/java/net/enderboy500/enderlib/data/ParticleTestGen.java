package net.enderboy500.enderlib.data;

import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ParticleTestGen extends PaintingVariantProvider{

    public ParticleTestGen(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    protected void generate(Output output) {
    }

    @Override
    public String getName() {
        return "";
    }
}
