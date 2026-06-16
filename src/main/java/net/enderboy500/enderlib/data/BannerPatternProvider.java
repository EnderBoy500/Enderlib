package net.enderboy500.enderlib.data;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class BannerPatternProvider extends FabricCodecDataProvider<BannerPattern> {


    protected BannerPatternProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, "banner_pattern", BannerPattern.DIRECT_CODEC);
    }

    protected abstract void generate(Output output);

    @Override
    protected void configure(BiConsumer<Identifier, BannerPattern> provider, HolderLookup.Provider lookup) {
        this.generate((identifier, string) -> provider.accept(identifier.id(), new BannerPattern(identifier.id(), string)));
    }

    public interface Output {
        void accept(ClientAsset.ResourceTexture identifier, String string);

        default void accept(Identifier identifier, String translationKey) {
            this.accept(new ClientAsset.ResourceTexture(identifier), translationKey);
        }
    }
}
