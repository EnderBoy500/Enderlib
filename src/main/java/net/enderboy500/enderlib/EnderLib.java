package net.enderboy500.enderlib;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;

public class EnderLib implements ModInitializer {
	public static final String MOD_ID = "enderlib";

	public static String currentModId = "";

	public static Identifier customId(String path) {
		return Identifier.of(currentModId, path);
	}

	@Override
	public void onInitialize() {
	}

}