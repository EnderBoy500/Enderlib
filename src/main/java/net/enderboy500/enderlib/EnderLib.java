package net.enderboy500.enderlib;

import com.mojang.serialization.Codec;
import eu.midnightdust.lib.config.MidnightConfig;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.enderboy500.enderlib.registry.RegisterCountry;
import net.enderboy500.enderlib.test.TestInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderLib implements ModInitializer {
	public static final String MOD_ID = "enderlib";

	public static String currentModId = "";

	public static Identifier customId(String path) {
		return Identifier.of(currentModId, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ELib.addModId(MOD_ID);
		EnderLibComponents.load();
		if (ELib.getCompat("midnightlib")) {
			MidnightConfig.init(EnderLib.MOD_ID, EnderLibConfig.class);
		}
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			TestInit.init();
		}
	}

	public static SlotActionType slotActionType() {
		if (ELib.isMidnightPresent()) {
			return EnderLibConfig.equipmentStateCycleKey.get();
		} else {
			return SlotActionType.QUICK_MOVE;
		}
	}

	public class EnderLibComponents {
		public static final ComponentType<Boolean> CYCLED_EQUIPMENT_STATE = RegistryHelper.registerDataComponent("cycled_equipment_state", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
		public static final ComponentType<Integer> EQUIPMENT_STATE = RegistryHelper.registerDataComponent("equipment_state", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
		public static final ComponentType<Boolean> EQUIPMENT_VISIBLE = RegistryHelper.registerDataComponent("equipment_visible", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
		static void load() {};
	}

}