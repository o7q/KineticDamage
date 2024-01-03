package com.o7q.kineticdamage;

import com.o7q.kineticdamage.network.NetworkMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.nio.ByteBuffer;

public class KineticDamageClient implements ClientModInitializer
{

    public static final long PACKET_PRECISION = 1000;

    @Override
    public void onInitializeClient()
    {
        NetworkMessages.registerS2CPackets();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            if (world.isClient())
            {
                double playerSpeedX = player.getX() - player.prevX;
                double playerSpeedY = player.getY() - player.prevY;
                double playerSpeedZ = player.getZ() - player.prevZ;

                // FIX THIS
                // figure out how to send multiple doubles to the server for each playerSpeed axis

                /*long[] playerSpeedPacked_buf = {(long)(playerSpeedX * PACKET_PRECISION), (long)(playerSpeedY * PACKET_PRECISION), (long)(playerSpeedZ * PACKET_PRECISION)};*/

                int entityId_buf = entity.getId();

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeVarInt(entityId_buf);
                buf.writeDouble(playerSpeedX);
                buf.writeDouble(playerSpeedY);
                buf.writeDouble(playerSpeedZ);

                ClientPlayNetworking.send(NetworkMessages.ATTACK_ID, buf);
            }

            return ActionResult.PASS;
        });
    }
}