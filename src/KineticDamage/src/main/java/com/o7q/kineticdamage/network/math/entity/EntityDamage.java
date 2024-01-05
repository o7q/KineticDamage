package com.o7q.kineticdamage.network.math.entity;

import net.minecraft.util.math.Vec3d;

import static com.o7q.kineticdamage.config.ConfigValues.*;

public class EntityDamage {
    public static double calculateEntityDamage(Vec3d attackerVelocity, double attackerFallDistance) {
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
}
