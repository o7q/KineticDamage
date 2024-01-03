package com.o7q.kineticdamage.network.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.nio.ByteBuffer;

import static com.o7q.kineticdamage.config.ConfigValues.*;
import static com.o7q.kineticdamage.network.entity.EntityMath.*;

public class AttackC2SPacket
{
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender)
    {
        // FIX THIS
        // trying to figure out how to recieve multiple doubles from the client

        int entityId = buf.readInt();
        double playerSpeedX = buf.readDouble();



        player.sendMessage(Text.literal(String.valueOf(test2)));

/*        float playerSpeedXZMultiplier = 1.0f;
        if (player.isSprinting())
            playerSpeedXZMultiplier *= ACTION_SPRINTING_MULTIPLIER;
        if (player.isSwimming())
            playerSpeedXZMultiplier *= ACTION_SWIMMING_MULTIPLIER;
        if (player.isSneaking())
            playerSpeedXZMultiplier *= ACTION_SNEAKING_MULTIPLIER;
        if (player.isCrawling())
            playerSpeedXZMultiplier *= ACTION_CRAWLING_MULTIPLIER;

        ByteBuffer playerSpeedByteBuffer = ByteBuffer.wrap(buf.readByteArray());
        double playerSpeedX = playerSpeedByteBuffer.getDouble() * playerSpeedXZMultiplier;
        double playerSpeedY = playerSpeedByteBuffer.getDouble() * playerSpeedXZMultiplier;
        double playerSpeedZ = playerSpeedByteBuffer.getDouble() * playerSpeedXZMultiplier;

        ServerWorld world = player.getServerWorld();

        Vec3d playerSpeed = new Vec3d(playerSpeedX, playerSpeedY, playerSpeedZ);
        Vec3d playerVelocity = player.getVelocity();
        double playerHeadYaw = player.getYaw();
        double playerFallDistance = player.fallDistance;

        double playerSpeed3D = CalculateEntity3DSpeed(playerSpeed);

        int entityId = buf.readVarInt();
        Entity entity = world.getEntityById(entityId);
        if (entity == null) return;

        Vec3d entityVelocity = entity.getVelocity();

        Vec3d knockbackAmount = CalculateEntityKnockback(entityVelocity, playerSpeed, playerVelocity, playerHeadYaw);
        double damageAmount = CalculateEntityDamage(playerSpeed, playerFallDistance);

        DamageSource entityDamageSource = world.getDamageSources().playerAttack(player);
        entity.damage(entityDamageSource, (float)damageAmount);
        entity.setVelocity(knockbackAmount);

        if (DEBUG_CHAT_LOG)
            player.sendMessage(Text.literal(
                    "\n" +
                    "Speed:\n" +
                    "  X: " + playerSpeed.x + "\n" +
                    "  Y: " + playerSpeed.y + "\n" +
                    "  Z: " + playerSpeed.z + "\n" +
                    "  3D: " + playerSpeed3D + "\n" +
                    "Knockback:\n" +
                    "  X: " + knockbackAmount.x + "\n" +
                    "  Y: " + knockbackAmount.y + "\n" +
                    "  Z: " + knockbackAmount.z + "\n" +
                    "Entity:\n" +
                    "  ID: " + entity.getId() + " (" + entity.getUuid() + ")\n" +
                    "  Name: " + entity.getName() + "\n" +
                    "Damage: " + damageAmount + "\n" +
                    "Yaw: " + playerHeadYaw +
                    "\n"
            ));*/
    }
}