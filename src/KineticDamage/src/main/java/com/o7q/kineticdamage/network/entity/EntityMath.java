package com.o7q.kineticdamage.network.entity;

import net.minecraft.util.math.Vec3d;

import static com.o7q.kineticdamage.config.ConfigValues.*;

public class EntityMath
{
    public static Vec3d CalculateEntityKnockback(Vec3d recipientVelocity, Vec3d attackerVelocity, double attackerHeadYaw, double attackerHeadPitch, double attackerFallDistance)
    {
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
                        knockbackVector.y +
                        (USE_PLAYER_FALL_DISTANCE_FOR_MATH ? attackerFallDistance *
                                knockbackY_signum *
                                PLAYER_FALL_DISTANCE_DOWNWARDS_KNOCKBACK_COEFFICIENT :
                                0
                        )
                ) * KNOCKBACK_MULTIPLIER_Y,
                recipientVelocity.z * entityBaseVelocityMultiplier + knockbackVector.z * KNOCKBACK_MULTIPLIER_Z
        );

        return calculatedKnockback;
    }

    public static double CalculateEntityDamage(Vec3d attackerVelocity, double attackerFallDistance)
    {
        Vec3d attackerSpeed = new Vec3d(
                Math.abs(attackerVelocity.x),
                Math.abs(attackerVelocity.y),
                Math.abs(attackerVelocity.z)
        );

        double damageXZ = (attackerSpeed.x + attackerSpeed.z) * DAMAGE_MULTIPLIER_HORIZONTAL;
        double damageY = (
                attackerSpeed.y +
                (USE_PLAYER_FALL_DISTANCE_FOR_MATH ? attackerFallDistance : 0)
        ) * DAMAGE_MULTIPLIER_VERTICAL;

        if (damageXZ > DAMAGE_MAX_HORIZONTAL && DAMAGE_MAX_HORIZONTAL > 0)
            damageXZ = DAMAGE_MAX_HORIZONTAL;

        if (damageY > DAMAGE_MAX_VERTICAL && DAMAGE_MAX_VERTICAL > 0)
            damageY = DAMAGE_MAX_VERTICAL;

        return damageXZ + damageY;
    }

    public static double CalculateEntityDampedDistance(double entityFallDistance)
    {
        double entityFallDistanceDamped = switch (PLAYER_FALL_DISTANCE_DAMPING_FUNCTION) {
            case "sqrt" -> Math.sqrt(entityFallDistance * PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT);
            case "log_e" -> Math.log1p(entityFallDistance * PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT);
            case "log" -> Math.log10(entityFallDistance * PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT + 1);
            case "tanh" -> Math.tanh(entityFallDistance * PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT);

            case "quadratic" -> Math.pow(entityFallDistance * PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT, 2);
            case "cubic" -> Math.pow(entityFallDistance * PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT, 3);

            default -> entityFallDistance;
        };

        return entityFallDistanceDamped;
    }

    public static double CalculateEntity3DSpeed(Vec3d entitySpeed)
    {
        return Math.sqrt(
                Math.pow(entitySpeed.x, 2) +
                Math.pow(entitySpeed.y, 2) +
                Math.pow(entitySpeed.z, 2)
        );
    }
}