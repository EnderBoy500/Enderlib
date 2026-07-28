package net.enderboy500.enderlib;

import eu.midnightdust.lib.config.MidnightConfig;
import net.enderboy500.enderlib.client.config.EnderLibConfig;
import net.enderboy500.enderlib.commands.EnderlibCommands;
import net.enderboy500.enderlib.item.CustomCrossbowItemModel;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.EnderlibTags;
import net.enderboy500.enderlib.util.ItemUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderLib implements ModInitializer {
	public static final String MOD_ID = "enderlib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String string) {
		return Identifier.fromNamespaceAndPath(MOD_ID, string);
	}
	public static final EntityDataAccessor<Float> SCREENSHAKE_INTENSITY = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Integer> SCREENSHAKE_DURATION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

	@Override
	public void onInitialize() {
		ELib.addModId(MOD_ID);

		EnderLibComponents.load();
		EnderlibTags.loadTags();
		EnderlibCommands.loadCommands();

		CustomCrossbowItemModel.addNewModel(EnderLibTest.TEST, Identifier.withDefaultNamespace("diamond_sword"));

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) EnderLibTest.load();

		MidnightConfig.init(MOD_ID, EnderLibConfig.class);
	}

	public static boolean canRightClickToCycle() {
		return EnderLibConfig.equipmentStateCycleKeys.get() == ClickType.CLONE;
	}
}