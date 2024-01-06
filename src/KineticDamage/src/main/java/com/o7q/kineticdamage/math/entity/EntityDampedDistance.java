package com.o7q.kineticdamage.math.entity;

import static com.o7q.kineticdamage.config.ConfigValues.*;

public class EntityDampedDistance {
    public static double calculateEntityDampedDistance(double entityFallDistance) {
        double entityFallDistanceDamped;

        switch (PLAYER_FALL_DISTANCE_DAMPING_FUNCTION) {
            case "linear":
                entityFallDistanceDamped =
                        entityFallDistance *
                                PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1 +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;
            case "sqrt":
                if (PLAYER_FALL_DISTANCE_DAMPING_SQRT_AUTO_CLAMP)
                    entityFallDistanceDamped = Math.sqrt(
                            entityFallDistance *
                                    PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT
                    ) - PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT / 4;
                else
                    entityFallDistanceDamped = Math.sqrt(
                            entityFallDistance *
                                    PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                    PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1
                    ) + PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;
            case "log_e":
                entityFallDistanceDamped = Math.log1p(
                        entityFallDistance *
                                PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1
                ) + PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;
            case "log":
                entityFallDistanceDamped = Math.log10(
                        entityFallDistance *
                                PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                1 +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1
                ) + PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;
            case "tanh":
                entityFallDistanceDamped = Math.tanh(
                        entityFallDistance *
                                PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1
                ) + PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;

            case "quadratic":
                entityFallDistanceDamped = Math.pow(
                        entityFallDistance *
                                PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1
                        , 2
                ) + PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;
            case "cubic":
                entityFallDistanceDamped = Math.pow(
                        entityFallDistance *
                                PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT +
                                PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1, 3
                ) + PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2;
                break;

            default:
                entityFallDistanceDamped = entityFallDistance;
                break;
        }

        return (entityFallDistanceDamped < 0 ? 0 : entityFallDistanceDamped);
    }
}