package com.o7q.kineticdamage.network.events.Attack;

import com.o7q.kineticdamage.mixin.KeyBindingAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import static com.o7q.kineticdamage.network.packets.AttackC2SPacket.AttackClient.sendAttackC2SPacket;
import static com.o7q.kineticdamage.KineticDamage.LOGGER;

public class AttackDirectHandler {
    public static void registerAttackDirectHandler() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            PlayerEntity player = client.player;
            if (player != null) {
                int attackKeyTimesPressed = ((KeyBindingAccessor) client.options.attackKey).getTimesPressed();
                if (attackKeyTimesPressed > 0) {
                    if (client.crosshairTarget instanceof EntityHitResult playerCrosshairTarget) {
                        Entity entity = playerCrosshairTarget.getEntity();
                        World world = player.getWorld();

                        if (world == null) {
                            LOGGER.error("(registerAttackDirectHandler) " + player.getName() + ": World is null!");
                            return;
                        }

                        sendAttackC2SPacket(world, player, entity);
                    }
                }
            }
        });
    }
}