package com.o7q.kineticdamage.network.packets.AttackC2SPacket;

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

import static com.o7q.kineticdamage.config.ConfigValues.*;

import static com.o7q.kineticdamage.KineticDamage.LOGGER;
import static com.o7q.kineticdamage.math.entity.Entity3DSpeed.calculateEntity3DSpeed;
import static com.o7q.kineticdamage.math.entity.EntityDamage.calculateEntityDamage;
import static com.o7q.kineticdamage.math.entity.EntityDampedDistance.calculateEntityDampedDistance;
import static com.o7q.kineticdamage.math.entity.EntityKnockback.calculateEntityKnockback;

public class AttackServer {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        float playerVelocityXZMultiplier = 1.0f;
        if (player.isSprinting())
            playerVelocityXZMultiplier *= ACTION_SPRINTING_MULTIPLIER;
        if (player.isSwimming())
            playerVelocityXZMultiplier *= ACTION_SWIMMING_MULTIPLIER;
        if (player.isSneaking())
            playerVelocityXZMultiplier *= ACTION_SNEAKING_MULTIPLIER;
        if (player.isCrawling())
            playerVelocityXZMultiplier *= ACTION_CRAWLING_MULTIPLIER;

        // READ BUF (entityId -> entityVelocity -> playerVelocity)
        int entityId = buf.readInt();
        double entityVelocityX = buf.readDouble();
        double entityVelocityY = buf.readDouble();
        double entityVelocityZ = buf.readDouble();
        Vec3d entityVelocity = new Vec3d(entityVelocityX, entityVelocityY, entityVelocityZ);

        double playerVelocityX = buf.readDouble() * playerVelocityXZMultiplier;
        double playerVelocityY = buf.readDouble() * playerVelocityXZMultiplier;
        double playerVelocityZ = buf.readDouble() * playerVelocityXZMultiplier;
        Vec3d playerVelocity = new Vec3d(playerVelocityX, playerVelocityY, playerVelocityZ);

        double playerSpeed3D = calculateEntity3DSpeed(playerVelocity);

        double playerHeadYaw = player.getYaw();
        double playerHeadPitch = player.getPitch();
        double playerFallDistance = player.fallDistance;
        double playerFallDistanceDamped = calculateEntityDampedDistance(playerFallDistance);

        ServerWorld world = player.getServerWorld();

        Entity entity = world.getEntityById(entityId);
        if (entity == null) {
            LOGGER.error("(AttackServer.recieve) " + player.getName() + ": Entity is null!");
            return;
        }

        Vec3d knockbackAmount = calculateEntityKnockback(entityVelocity, playerVelocity, playerHeadYaw, playerHeadPitch, playerFallDistanceDamped);
        double damageAmount = calculateEntityDamage(playerVelocity, playerFallDistanceDamped);

        DamageSource entityDamageSource = world.getDamageSources().playerAttack(player);
        entity.damage(entityDamageSource, (float)damageAmount);
        entity.setVelocity(knockbackAmount);

        if (DEBUG_CHAT_LOG) {
            String debugMessage =
                    "\n" +
                    "\n" +
                    "Direct/Mixin Hit: " + USE_PLAYER_DIRECT_HIT_REGISTRATION + "\n" +
                    "Velocity:\n" +
                    "  X: " + playerVelocity.x + "\n" +
                    "  Y: " + playerVelocity.y + "\n" +
                    "  Z: " + playerVelocity.z + "\n" +
                    "  Speed3D: " + playerSpeed3D + "\n" +
                    "  Fall Distance: " + playerFallDistance + "\n" +
                    "  Fall Distance (damped): " + playerFallDistanceDamped + "\n" +
                    "Yaw: " + playerHeadYaw + "\n" +
                    "Pitch: " + playerHeadPitch + "\n" +
                    "Knockback:\n" +
                    "  X: " + knockbackAmount.x + "\n" +
                    "  Y: " + knockbackAmount.y + "\n" +
                    "  Z: " + knockbackAmount.z + "\n" +
                    "Entity:\n" +
                    "  ID: " + entity.getId() + " (" + entity.getUuid() + ")\n" +
                    "  Name: " + entity.getName() + "\n" +
                    "  Damage: " + damageAmount;

            player.sendMessage(Text.literal(debugMessage));
        }
    }
}