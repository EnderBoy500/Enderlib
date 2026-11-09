package net.enderboy500.enderlib;

import net.enderboy500.enderlib.commands.EnderlibCommands;
import net.enderboy500.enderlib.config.EModConfigScreenHelper;
import net.enderboy500.enderlib.config.EnderModConfigScreen;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.misc.ChanceSpawnCondition;
import net.enderboy500.enderlib.options.EnderLibOptions;
import net.enderboy500.enderlib.options.EnderLibOptionsScreen;
import net.enderboy500.enderlib.options.EquipmentStateCycleKeys;
import net.enderboy500.enderlib.util.EnderlibTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderLib implements ModInitializer {
	public static EnderLibOptions options;

	public static final String MOD_ID = "enderlib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String string) {
		return Identifier.fromNamespaceAndPath(MOD_ID, string);
	}

	public static final EntityDataAccessor<Float> SCREENSHAKE_INTENSITY = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Integer> SCREENSHAKE_DURATION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

	@Override
	public void onInitialize() {
		options = new EnderLibOptions(Minecraft.getInstance().gameDirectory);
		options.load();

		EnderLibComponents.load();
		EnderlibTags.loadTags();
		EnderlibCommands.loadCommands();
		Registry.register(BuiltInRegistries.SPAWN_CONDITION_TYPE, id("chance"), ChanceSpawnCondition.CODEC);

		EnderModConfigScreen.addButtonToConfigScreen(EnderModConfigScreen.makeButton("options.enderlib.title", "options.enderlib.title",
				150, (var) -> Minecraft.getInstance().setScreenAndShow(new EnderLibOptionsScreen(Minecraft.getInstance().screen))));
	}

	public static boolean canRightClickToCycle() {
		return options.equipmentStateCycleKey.get() == EquipmentStateCycleKeys.RIGHT_CLICK;
	}

	public interface EEnchantment {
		static void register(BootstrapContext<Enchantment> registerable) {

		}
	}
}