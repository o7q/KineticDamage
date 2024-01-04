package com.o7q.kineticdamage;

import com.o7q.kineticdamage.network.NetworkMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.o7q.kineticdamage.config.ConfigManager.ConfigInit;

public class KineticDamage implements ModInitializer {
	public static final String MOD_ID = "kineticdamage";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ConfigInit();
		NetworkMessages.registerC2SPackets();

		LOGGER.info("KineticDamage is online!");
	}
}