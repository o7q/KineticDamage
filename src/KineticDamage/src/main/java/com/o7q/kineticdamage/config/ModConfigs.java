package com.o7q.kineticdamage.config;

import com.mojang.datafixers.util.Pair;
import com.o7q.kineticdamage.KineticDamage;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int DAMAGE_MULTIPLIER_VERTICAL;
    public static int DAMAGE_MULTIPLIER_HORIZONTAL;

    public static int DAMAGE_MAX_VERTICAL;
    public static int DAMAGE_MAX_HORIZONTAL;

    public static int KNOCKBACK_MULTIPLIER_X;
    public static int KNOCKBACK_MULTIPLIER_Y;
    public static int KNOCKBACK_MULTIPLIER_Z;

    public static boolean DEBUG_LOG;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(KineticDamage.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("damage.multiplier.vertical", 1), "int");
        configs.addKeyValuePair(new Pair<>("damage.multiplier.horizontal", 1), "int");

        configs.addKeyValuePair(new Pair<>("damage.max.vertical", 100), "int");
        configs.addKeyValuePair(new Pair<>("damage.max.horizontal", -1), "int");

        configs.addKeyValuePair(new Pair<>("knockback.multiplier.x", 1), "int");
        configs.addKeyValuePair(new Pair<>("knockback.multiplier.y", 1), "int");
        configs.addKeyValuePair(new Pair<>("knockback.multiplier.z", 1), "int");

        configs.addKeyValuePair(new Pair<>("debug.log", true), "boolean");
    }

    private static void assignConfigs() {
        DAMAGE_MULTIPLIER_VERTICAL = CONFIG.getOrDefault("damage.multiplier.vertical", 1);
        DAMAGE_MULTIPLIER_HORIZONTAL = CONFIG.getOrDefault("damage.multiplier.horizontal", 1);

        DAMAGE_MAX_VERTICAL = CONFIG.getOrDefault("damage.multiplier.vertical", 100);
        DAMAGE_MAX_HORIZONTAL = CONFIG.getOrDefault("damage.multiplier.horizontal", -1);

        KNOCKBACK_MULTIPLIER_X = CONFIG.getOrDefault("knockback.multiplier.x", 1);
        KNOCKBACK_MULTIPLIER_Y = CONFIG.getOrDefault("knockback.multiplier.y", 1);
        KNOCKBACK_MULTIPLIER_Z = CONFIG.getOrDefault("knockback.multiplier.z", 1);

        DEBUG_LOG = CONFIG.getOrDefault("debug.log", true);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
