package net.enderboy500.enderlib.helper;

import com.google.common.collect.ImmutableSet;
import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagerHelper {

    public static VillagerProfession registerProfession(String id, RegistryKey<PointOfInterestType> type,String translationKey, SoundEvent soundEvent) {
        return Registry.register(Registries.VILLAGER_PROFESSION, EnderLib.customId(id), new VillagerProfession(Text.translatable(translationKey), entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                ImmutableSet.of(), ImmutableSet.of(), soundEvent));
    }

    public static VillagerProfession registerProfession(String id, RegistryKey<PointOfInterestType> type,Text text, SoundEvent soundEvent) {
        return Registry.register(Registries.VILLAGER_PROFESSION, EnderLib.customId(id), new VillagerProfession(text, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                ImmutableSet.of(), ImmutableSet.of(), soundEvent));
    }

    public static VillagerProfession registerProfession(String id, RegistryKey<PointOfInterestType> type, SoundEvent soundEvent) {
        return Registry.register(Registries.VILLAGER_PROFESSION, EnderLib.customId(id), new VillagerProfession(Text.translatable("entity." + EnderLib.currentModId +".villager." + id), entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                ImmutableSet.of(), ImmutableSet.of(), soundEvent));
    }

    public static PointOfInterestType registerPOI(String id, Block block) {
        return PointOfInterestHelper.register(EnderLib.customId(id), 1, 1, block);
    }

    public static RegistryKey<PointOfInterestType> registerPOIKey(String id) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, EnderLib.customId(id));
    }
}
