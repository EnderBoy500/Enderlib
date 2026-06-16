package net.enderboy500.enderlib.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ibm.icu.util.Output;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.impl.resource.pack.FabricPack;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class ParticleResourceProvider implements DataProvider {

    private final PackOutput.PathProvider pathResolver;

    protected ParticleResourceProvider(FabricDataOutput output) {
        this.pathResolver = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "particles");
    }

    protected abstract void generate(Output output);

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        Map<Identifier, List<Identifier>> map = new Object2ObjectOpenHashMap<>();
        this.generate((particleType, textures) ->
                map.put(BuiltInRegistries.PARTICLE_TYPE.getKey(particleType), textures)
        );
        map.forEach((identifier, identifiers) -> {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            identifiers.forEach(texture -> jsonArray.add(texture.toString()));
            jsonObject.add("textures", jsonArray);
            futures.add(DataProvider.saveStable(cachedOutput, jsonObject, this.pathResolver.json(identifier)));
        });
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }


    public interface Output {

        void accept(ParticleType<?> particleType, List<Identifier> textures);

        default void accept(ParticleType<?> particleType, Identifier... textures) {
            this.accept(particleType, List.of(textures));
        }

        default void accept(ParticleType<?> particleType) {
            this.accept(particleType, BuiltInRegistries.PARTICLE_TYPE.getKey(particleType));
        }

    }
}
