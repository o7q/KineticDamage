package com.o7q.kineticdamage;

import com.o7q.kineticdamage.network.NetworkMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;

public class KineticDamageClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        NetworkMessages.registerS2CPackets();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            if (world.isClient())
            {
                // calculate each entity axis velocity, and get its ID
                int entityId_buf = entity.getId();
                double entityVelocityX_buf = entity.getX() - entity.prevX;
                double entityVelocityY_buf = entity.getY() - entity.prevY;
                double entityVelocityZ_buf = entity.getZ() - entity.prevZ;

                // calculate each player axis velocity
                double playerVelocityX_buf = player.getX() - player.prevX;
                double playerVelocityY_buf = player.getY() - player.prevY;
                double playerVelocityZ_buf = player.getZ() - player.prevZ;

                // WRITE BUF (entityId -> entityVelocity -> playerVelocity)
                PacketByteBuf buf = PacketByteBufs.create();

                buf.writeInt(entityId_buf);
                buf.writeDouble(entityVelocityX_buf);
                buf.writeDouble(entityVelocityY_buf);
                buf.writeDouble(entityVelocityZ_buf);

                buf.writeDouble(playerVelocityX_buf);
                buf.writeDouble(playerVelocityY_buf);
                buf.writeDouble(playerVelocityZ_buf);

                ClientPlayNetworking.send(NetworkMessages.ATTACK_ID, buf);
            }

            return ActionResult.PASS;
        });
    }
}