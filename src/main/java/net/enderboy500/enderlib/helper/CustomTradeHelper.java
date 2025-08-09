package net.enderboy500.enderlib.helper;


import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public final class CustomTradeHelper {
    private CustomTradeHelper() {
    }
    public static void createVillagerTrade(RegistryKey<VillagerProfession> profession, TraderLevel traderLevel,Item payment, int paymentCount, Item soldItem, int soldItemCount, int maxTradeCount, int experienceGained, float priceMultiplier) {
        TradeOfferHelper.registerVillagerOffers(profession, traderLevel.getLevel(), factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxTradeCount, experienceGained, priceMultiplier));
        });
    }

    public static void createWanderingTraderTrade(Identifier identifier, Item payment, int paymentCount,Item soldItem, int soldItemCount,int maxUses) {
        TradeOfferHelper.registerWanderingTraderOffers(factories -> {
            factories.addAll(identifier, (entity, random) -> new TradeOffer(
                    new TradedItem(payment, paymentCount),
                    new ItemStack(soldItem, soldItemCount), maxUses, 7, 0.04f));
        });
    }

}
