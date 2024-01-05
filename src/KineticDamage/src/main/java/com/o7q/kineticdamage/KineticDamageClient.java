package com.o7q.kineticdamage;

import com.o7q.kineticdamage.network.NetworkMessages;
import com.o7q.kineticdamage.network.events.Attack.AttackDirectHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;

import static com.o7q.kineticdamage.KineticDamage.LOGGER;
import static com.o7q.kineticdamage.config.ConfigValues.*;
import static com.o7q.kineticdamage.network.events.Attack.AttackCallbackHandler.registerAttackCallbackHandler;
import static com.o7q.kineticdamage.network.events.Attack.AttackDirectHandler.registerAttackDirectHandler;

public class KineticDamageClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        AttackDirectHandler.register();
        NetworkMessages.registerS2CPackets();

        if (USE_PLAYER_DIRECT_HIT_REGISTRATION)
            registerAttackDirectHandler();
        else
            registerAttackCallbackHandler();

        LOGGER.info("KineticDamageClient is online!");
    }
}