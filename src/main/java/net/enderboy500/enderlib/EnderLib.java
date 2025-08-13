package net.enderboy500.enderlib;

import net.enderboy500.enderlib.item.IntStupid;
import net.enderboy500.enderlib.registry.ToolFuntionRegistry;
import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderLib implements ModInitializer {
	public static final String MOD_ID = "enderlib";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		IntStupid.loadItems();
		ToolFuntionRegistry.creatingShovellingFunction(Blocks.PODZOL, Blocks.DIRT);
	}

}