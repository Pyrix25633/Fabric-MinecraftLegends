package net.rupyber_studios.minecraft_legends;

import net.fabricmc.api.ModInitializer;
import net.rupyber_studios.minecraft_legends.item.ModItems;
import net.rupyber_studios.minecraft_legends.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class MinecraftLegends implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("minecraft_legends");

	public static final String MOD_ID = "minecraft_legends";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();

		GeckoLib.initialize();

		ModRegistries.registerAttributes();

		LOGGER.info("Hello Fabric world!");
	}
}