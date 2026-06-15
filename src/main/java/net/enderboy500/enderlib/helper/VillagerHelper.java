package net.enderboy500.enderlib.helper;

import net.enderboy500.enderlib.ELib;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import com.google.common.collect.ImmutableSet;

public class VillagerHelper {

    public static VillagerProfession registerProfession(String id, ResourceKey<PoiType> type,String translationKey, SoundEvent soundEvent) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, ELib.customId(id), new VillagerProfession(Component.translatable(translationKey), entry -> entry.is(type), entry -> entry.is(type),
                ImmutableSet.of(), ImmutableSet.of(), soundEvent));
    }

    public static VillagerProfession registerProfession(String id, ResourceKey<PoiType> type,Component text, SoundEvent soundEvent) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, ELib.customId(id), new VillagerProfession(text, entry -> entry.is(type), entry -> entry.is(type),
                ImmutableSet.of(), ImmutableSet.of(), soundEvent));
    }

    public static VillagerProfession registerProfession(String id, ResourceKey<PoiType> type, SoundEvent soundEvent) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, ELib.customId(id), new VillagerProfession(Component.translatable("entity." + ELib.currentModId +".villager." + id), entry -> entry.is(type), entry -> entry.is(type),
                ImmutableSet.of(), ImmutableSet.of(), soundEvent));
    }

    public static PoiType registerPOI(String id, Block block) {
        return PointOfInterestHelper.register(ELib.customId(id), 1, 1, block);
    }

    public static ResourceKey<PoiType> registerPOIKey(String id) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ELib.customId(id));
    }

    public static void createVillagerTrade(ResourceKey<VillagerProfession> profession, TraderLevel traderLevel, Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxTradeCount, int experienceGained, float priceMultiplier) {
        TradeOfferHelper.registerVillagerOffers(profession, traderLevel.getLevel(), factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxTradeCount, experienceGained, priceMultiplier));
        });
    }

    public static void createVillagerTrade(ResourceKey<VillagerProfession> profession, int traderLevel, Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxTradeCount, int experienceGained, float priceMultiplier) {
        TradeOfferHelper.registerVillagerOffers(profession, traderLevel, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxTradeCount, experienceGained, priceMultiplier));
        });
    }

    public static void createWanderingTraderTrade(Identifier identifier, Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxUses) {
        TradeOfferHelper.registerWanderingTraderOffers(factories -> {
            factories.addAll(identifier, (world, entity, random) -> new MerchantOffer(
                    new ItemCost(payment, paymentCount),
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
