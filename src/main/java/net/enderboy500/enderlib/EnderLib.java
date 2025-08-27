package net.enderboy500.enderlib;

import net.enderboy500.enderlib.helper.LootTableModificationHelper;
import net.enderboy500.enderlib.misc.VanillaChestLootTableList;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
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