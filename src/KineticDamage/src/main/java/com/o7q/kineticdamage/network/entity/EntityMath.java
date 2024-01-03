package com.o7q.kineticdamage.network.entity;

import net.minecraft.util.math.Vec3d;

import static com.o7q.kineticdamage.config.ConfigValues.*;

public class EntityMath
{
    protected static final double Y_OFFSET = -0.0784000015258789D;

    public static Vec3d CalculateEntityKnockback(Vec3d recipientVelocity, Vec3d attackerSpeed, Vec3d attackerVelocity, double attackerHeadYaw)
    {
        double verticalSignum = Math.signum(attackerVelocity.y + Y_OFFSET);
        verticalSignum = verticalSignum == 0 ? 1 : verticalSignum;

        double knockbackX = recipientVelocity.x + Math.sin(Math.toRadians(-attackerHeadYaw)) * attackerSpeed.x * KNOCKBACK_MULTIPLIER_X;
        double knockbackZ = recipientVelocity.z + Math.cos(Math.toRadians(attackerHeadYaw)) * attackerSpeed.z * KNOCKBACK_MULTIPLIER_Z;
        double knockbackY = recipientVelocity.y + verticalSignum * attackerSpeed.y * KNOCKBACK_MULTIPLIER_Y;

        return new Vec3d(knockbackX, knockbackY, knockbackZ);
    }

    public static double CalculateEntityDamage(Vec3d attackerSpeed, double attackerFallDistance)
    {
        double damageXZ = (attackerSpeed.x + attackerSpeed.z) * DAMAGE_MULTIPLIER_HORIZONTAL;
        double damageY = (attackerSpeed.y + attackerFallDistance) * DAMAGE_MULTIPLIER_VERTICAL;

        if (damageXZ > DAMAGE_MAX_HORIZONTAL && DAMAGE_MAX_HORIZONTAL > 0)
            damageXZ = DAMAGE_MAX_HORIZONTAL;

        if (damageY > DAMAGE_MAX_VERTICAL && DAMAGE_MAX_VERTICAL > 0)
            damageY = DAMAGE_MAX_VERTICAL;

        return damageXZ + damageY;
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