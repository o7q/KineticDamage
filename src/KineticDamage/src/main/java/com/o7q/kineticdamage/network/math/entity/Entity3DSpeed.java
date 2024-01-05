package com.o7q.kineticdamage.network.math.entity;

import net.minecraft.util.math.Vec3d;

public class Entity3DSpeed {
    public static double calculateEntity3DSpeed(Vec3d entitySpeed) {
        return Math.sqrt(
                Math.pow(entitySpeed.x, 2) +
                Math.pow(entitySpeed.y, 2) +
                Math.pow(entitySpeed.z, 2)
        );
    }
}
