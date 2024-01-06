package com.o7q.kineticdamage;

import com.o7q.kineticdamage.network.NetworkMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.o7q.kineticdamage.commands.CommandRegistry.registerCommands;
import static com.o7q.kineticdamage.config.ConfigManager.configInit;

public class KineticDamage implements ModInitializer {
	public static final String MOD_ID = "kineticdamage";
	public static final String MOD_VERSION = "1.0.0_beta1";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		configInit();
		registerCommands();
		NetworkMessages.registerC2SPackets();

		LOGGER.info("(onInitialize) KineticDamage is online!");
	}
}