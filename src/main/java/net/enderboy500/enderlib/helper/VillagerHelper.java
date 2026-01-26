package net.enderboy500.enderlib.helper;

import com.google.common.collect.ImmutableSet;
import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
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

    public static void createVillagerTrade(RegistryKey<VillagerProfession> profession, TraderLevel traderLevel, Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxTradeCount, int experienceGained, float priceMultiplier) {
        TradeOfferHelper.registerVillagerOffers(profession, traderLevel.getLevel(), factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxTradeCount, experienceGained, priceMultiplier));
        });
    }

    public static void createVillagerTrade(RegistryKey<VillagerProfession> profession, int traderLevel, Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxTradeCount, int experienceGained, float priceMultiplier) {
        TradeOfferHelper.registerVillagerOffers(profession, traderLevel, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxTradeCount, experienceGained, priceMultiplier));
        });
    }

    public static void createWanderingTraderTrade(Identifier identifier, Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxUses) {
        TradeOfferHelper.registerWanderingTraderOffers(factories -> {
            factories.addAll(identifier, (entity, random) -> new TradeOffer(
                    new TradedItem(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxUses, 7, 0.04f));
        });
    }

    public enum TraderLevel {
        NOVICE(1),
        APPRENTICE(2),
        JOURNEYMAN(3),
        EXPERT(4),
        MASTER(5)
        ;

        public final int level;

        TraderLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }
    }

}
