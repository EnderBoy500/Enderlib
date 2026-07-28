package net.enderboy500.enderlib.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class PaintingVariantProvider extends FabricCodecDataProvider<PaintingVariant> {

    public PaintingVariantProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, "painting_variant", PaintingVariant.DIRECT_CODEC);
    }

    protected abstract void generate(Output output);

    @Override
    protected final void configure(BiConsumer<Identifier, PaintingVariant> provider, HolderLookup.Provider registryLookup) {
        this.generate((identifier, path, width, height, title, author) ->
                provider.accept(identifier, new PaintingVariant(width, height, Identifier.fromNamespaceAndPath(identifier.getNamespace(), path),title, author)));
    }

    @FunctionalInterface
    public interface Output {
        void accept(Identifier identifier, String path, int width, int height, Optional<Component> title, Optional<Component> author);

        default void accept(Identifier identifier, String path, int width, int height) {
            this.accept(identifier, path, width, height, Optional.empty(), Optional.empty());
        }
    }
}
