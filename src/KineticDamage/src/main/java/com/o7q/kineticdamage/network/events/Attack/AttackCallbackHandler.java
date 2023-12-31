package com.o7q.kineticdamage.network.events.Attack;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;

import static com.o7q.kineticdamage.network.packets.AttackC2SPacket.AttackClient.sendAttackC2SPacket;

public class AttackCallbackHandler {
    public static void registerAttackCallbackHandler() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            sendAttackC2SPacket(world, player, entity);
            return ActionResult.PASS;
        });
    }
}