package com.o7q.kineticdamage.network.math.entity;

import net.minecraft.util.math.Vec3d;

import static com.o7q.kineticdamage.config.ConfigValues.*;

public class EntityKnockback {
    public static Vec3d calculateEntityKnockback(Vec3d recipientVelocity, Vec3d attackerVelocity, double attackerHeadYaw, double attackerHeadPitch, double attackerFallDistance) {
        Vec3d knockbackVector;

        if (USE_PLAYER_HEAD_ROTATION_FOR_MATH)
            knockbackVector = new Vec3d(
                    Math.sin(Math.toRadians(-attackerHeadYaw)) * Math.abs(attackerVelocity.x),
                    Math.sin(Math.toRadians(-attackerHeadPitch)) * Math.abs(attackerVelocity.y),
                    Math.cos(Math.toRadians(attackerHeadYaw)) * Math.abs(attackerVelocity.z)
            );

        else
            knockbackVector = new Vec3d(
                    attackerVelocity.x,
                    attackerVelocity.y,
                    attackerVelocity.z
            );

        int entityBaseVelocityMultiplier = 1;
        if (ENTITY_IGNORE_ORIGINAL_VELOCITY)
            entityBaseVelocityMultiplier = 0;

        double knockbackY_signum = Math.signum(attackerVelocity.y);

        Vec3d calculatedKnockback = new Vec3d(
                recipientVelocity.x * entityBaseVelocityMultiplier + knockbackVector.x * KNOCKBACK_MULTIPLIER_X,
                recipientVelocity.y * entityBaseVelocityMultiplier + (
                        knockbackVector.y + (
                                USE_PLAYER_FALL_DISTANCE_FOR_MATH ? attackerFallDistance *
                                        knockbackY_signum *
                                        PLAYER_FALL_DISTANCE_DOWNWARDS_KNOCKBACK_COEFFICIENT :
                                        0
                                )
                ) * KNOCKBACK_MULTIPLIER_Y,
                recipientVelocity.z * entityBaseVelocityMultiplier + knockbackVector.z * KNOCKBACK_MULTIPLIER_Z
        );

        return calculatedKnockback;
    }
}
