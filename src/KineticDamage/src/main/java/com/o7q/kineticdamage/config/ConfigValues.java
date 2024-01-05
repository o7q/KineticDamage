package com.o7q.kineticdamage.config;

public class ConfigValues {
    public static float DAMAGE_MULTIPLIER_VERTICAL = 1.0f;
    public static float DAMAGE_MULTIPLIER_HORIZONTAL = 1.0f;

    public static float DAMAGE_MAX_VERTICAL = 50.0f;
    public static float DAMAGE_MAX_HORIZONTAL = -1.0f;

    public static float KNOCKBACK_MULTIPLIER_X = 1.0f;
    public static float KNOCKBACK_MULTIPLIER_Y = 1.0f;
    public static float KNOCKBACK_MULTIPLIER_Z = 1.0f;

    public static float ACTION_SPRINTING_MULTIPLIER = 1.5f;
    public static float ACTION_SWIMMING_MULTIPLIER = 0.75f;
    public static float ACTION_SNEAKING_MULTIPLIER = 0.5f;
    public static float ACTION_CRAWLING_MULTIPLIER = 0.25f;

    public static boolean USE_PLAYER_FALL_DISTANCE_FOR_MATH = true;

    public static boolean USE_PLAYER_HEAD_ROTATION_FOR_MATH = false;

    public static boolean USE_PLAYER_MIXIN_HIT_REGISTRATION = false;

    public static boolean ENTITY_IGNORE_ORIGINAL_VELOCITY = false;

    public static String PLAYER_FALL_DISTANCE_DAMPING_FUNCTION = "sqrt";
    public static float PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT = 8.0f;
    public static float PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1 = 0.0f;
    public static float PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2 = 0.0f;
    public static boolean PLAYER_FALL_DISTANCE_DAMPING_SQRT_AUTO_CLAMP = true;
    public static float PLAYER_FALL_DISTANCE_DOWNWARDS_KNOCKBACK_COEFFICIENT = 0.05f;

    public static boolean DEBUG_CHAT_LOG = false;
}