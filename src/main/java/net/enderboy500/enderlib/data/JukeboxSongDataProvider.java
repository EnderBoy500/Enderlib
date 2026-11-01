package net.enderboy500.enderlib.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class JukeboxSongDataProvider extends FabricCodecDataProvider<JukeboxSong> {


    protected JukeboxSongDataProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, "jukebox_song", JukeboxSong.DIRECT_CODEC);
    }

    protected abstract void generate(Output output);

    @Override
    protected void configure(BiConsumer<Identifier, JukeboxSong> provider, HolderLookup.Provider lookup) {
        this.generate((resourceKey, song) -> provider.accept(resourceKey.identifier(), song));
    }

    public interface Output {
        void add(ResourceKey<JukeboxSong> jukeboxSongResourceKey, JukeboxSong song);

        default void add(ResourceKey<JukeboxSong> jukeboxSongResourceKey, Holder<SoundEvent> soundEvent, Component component, float lengthInSeconds, int comparatorOutput) {
            add(jukeboxSongResourceKey, new JukeboxSong(soundEvent, component, lengthInSeconds, comparatorOutput));
        }
        default void add(ResourceKey<JukeboxSong> jukeboxSongResourceKey, Holder<SoundEvent> soundEvent, String translationKey, float lengthInSeconds, int comparatorOutput) {
            add(jukeboxSongResourceKey, new JukeboxSong(soundEvent, Component.translatable(translationKey), lengthInSeconds, comparatorOutput));
        }
    }
}
