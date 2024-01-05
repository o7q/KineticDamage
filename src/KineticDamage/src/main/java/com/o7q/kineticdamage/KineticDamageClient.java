package com.o7q.kineticdamage;

import com.o7q.kineticdamage.network.NetworkMessages;
import net.fabricmc.api.ClientModInitializer;

import static com.o7q.kineticdamage.KineticDamage.LOGGER;
import static com.o7q.kineticdamage.config.ConfigValues.*;
import static com.o7q.kineticdamage.network.events.Attack.AttackCallbackHandler.registerAttackCallbackHandler;
import static com.o7q.kineticdamage.network.events.Attack.AttackDirectHandler.registerAttackDirectHandler;

public class KineticDamageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkMessages.registerS2CPackets();

        if (USE_PLAYER_DIRECT_HIT_REGISTRATION)
            registerAttackDirectHandler();
        else
            registerAttackCallbackHandler();

        LOGGER.info("(onInitializeClient) KineticDamage is online!");
    }
}