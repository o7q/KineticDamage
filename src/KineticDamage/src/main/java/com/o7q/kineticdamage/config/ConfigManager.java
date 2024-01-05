package com.o7q.kineticdamage.config;

import java.io.*;

import static com.o7q.kineticdamage.config.ConfigValues.*;
import static com.o7q.kineticdamage.KineticDamage.LOGGER;

public class ConfigManager
{
    public static void configInit()
    {
        File configFolder = new File("config");
        if (!configFolder.exists())
        {
            if (configFolder.mkdirs())
                LOGGER.info("(configInit) Created root config folder successfully!");
            else
                LOGGER.error("(configInit) Unable to create root config folder!");
        }

        File configFile = new File("config\\kineticdamage.properties");

        if (!configFile.exists())
            createDefaultConfig();

        readConfig();
    }

    private static void createDefaultConfig() {
        try {
            LOGGER.info("Attempting to create a default config...");

            String defaultConfig =
                    """
                    # KineticDamage config

                    # Damage multipliers
                    # These values will be multiplied with the calculated damage values
                    damage-multiplier-vertical=1.0
                    damage-multiplier-horizontal=1.0
                    
                    # Specify the max amount of damage that can be done
                    # Negative numbers will uncap the damage
                    damage-max-vertical=50.0
                    damage-max-horizontal=-1.0

                    # Knockback multipliers
                    # Scales the knockback with attacker's incoming velocity
                    knockback-multiplier-x=1.0
                    knockback-multiplier-y=1.0
                    knockback-multiplier-z=1.0
                    
                    # Action multipliers
                    # Scales damage and knockback depending on the attacker's action
                    action-sprinting-multiplier=1.5
                    action-swimming-multiplier=0.75
                    action-sneaking-multiplier=0.5
                    action-crawling-multiplier=0.25
                    
                    # Use player fall distance for damage
                    # The farther the player falls the more damage they will do
                    # Also, this damage will be added on top of the damage already done by the player's velocity
                    player-use-fall-distance=true
                    
                    # Use player head rotation vector for math
                    # This will use the player's head rotation instead of body velocity to calculate values
                    # If enabled, the server will always use the players head rotation vector to calculate knockback vectors instead of the velocity vector of the player itself
                    # In other words, the knockback will always occur in the direction the player is looking, this is not as realistic but it can be very fun
                    player-use-head-rotation=false
                    
                    # Use the direct attack register rather than the standard attack callback
                    # This is calculated client-side using a mixin
                    # I only recommend you enable this if you are having mod compatibility issue (ex. BetterCombat, or other combat based mods that may interfere)
                    player-use-direct-hit-register=false
                    
                    # Should the entity's velocity by completely overwritten by the new calculated velocity?
                    # By default, the new calculated velocity is added to the entities original velocity, this is much more realistic
                    # If enabled, all original entity velocity will be ignored and overwritten by the new calculated velocity
                    entity-ignore-original-velocity=false
                    
                    # ADVANCED OPTIONS
                    # Damping function for player fall distance
                    # This option will make the fall distance damage and knockback scale more slowly the longer the player falls
                    # This is to make it so a player can't just jump off of a mountain and 1 tap any mob
                    # I've also included some options to increase the damage the longer you fall (inverse-damping options)
                    #
                    # If you don't want to use any damping functions specify: none
                    # DAMPING OPTIONS (by increasing strength):               linear, sqrt, log_e, log, tanh
                    # INVERSE-DAMPING OPTIONS (by increasing strength):       quadratic, cubic
                    # Note: 'none' is the same as 'linear', except that linear will use the coefficient and constant values in it's calculation
                    player-fall-distance-damping-function=sqrt
                    # This value is multiplied to the fall distance before going into the damping function, it will be ignored if 'none' is set for the damping function
                    player-fall-distance-damping-coefficient=10.0
                    # Horizontal shift factor, example: f(x) = FUNC(AX + K1 <-) + K2
                    player-fall-distance-damping-constant-1=0.0
                    # Vertical shift factor, example: f(x) = FUNC(AX + K1) + K2 <-
                    player-fall-distance-damping-constant-2=0.0
                    # Auto clamp square root function
                    # This ensures that the y of f(x) = sqrt(ax) is never bigger than the y of f(x) = ax
                    # Turning this off can produce strange values when a player crits a mob after falling a small distance
                    # This value is ONLY used when 'sqrt' is used for the damping function
                    # When enabled, the 'constant-1' and 'constant-2' values will be ignored
                    player-fall-distance-damping-sqrt-auto-clamp=true
                    # This value is multiplied to the fall distance that's used to calculate downwards knockback
                    # Lower values means lower downwards knockback
                    player-fall-distance-downwards-knockback-coefficient=0.05

                    # Debug
                    # Logs debug messages to the chat (per player)
                    debug-chat-log=false
                    """;

            FileWriter configFileWriter = new FileWriter("config\\kineticdamage.properties");
            configFileWriter.write(defaultConfig);
            configFileWriter.close();
        } catch (IOException e) {
            LOGGER.error("Unable to create config!");
        }
    }

    private static void readConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader("config\\kineticdamage.properties"))) {
            LOGGER.info("(readConfig) Attempting to read the config...");

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty() && line.charAt(0) != '#') {
                    String[] configPair = line.split("=", -1);

                    switch (configPair[0]) {
                        case "damage-multiplier-vertical":
                            DAMAGE_MULTIPLIER_VERTICAL = Float.parseFloat(configPair[1]);
                            break;
                        case "damage-multiplier-horizontal":
                            DAMAGE_MULTIPLIER_HORIZONTAL = Float.parseFloat(configPair[1]);
                            break;

                        case "damage-max-vertical":
                            DAMAGE_MAX_VERTICAL = Float.parseFloat(configPair[1]);
                            break;
                        case "damage-max-horizontal":
                            DAMAGE_MAX_HORIZONTAL = Float.parseFloat(configPair[1]);
                            break;

                        case "knockback-multiplier-x":
                            KNOCKBACK_MULTIPLIER_X = Float.parseFloat(configPair[1]);
                            break;
                        case "knockback-multiplier-y":
                            KNOCKBACK_MULTIPLIER_Y = Float.parseFloat(configPair[1]);
                            break;
                        case "knockback-multiplier-z":
                            KNOCKBACK_MULTIPLIER_Z = Float.parseFloat(configPair[1]);
                            break;

                        case "action-sprinting-multiplier":
                            ACTION_SPRINTING_MULTIPLIER = Float.parseFloat(configPair[1]);
                            break;
                        case "action-swimming-multiplier":
                            ACTION_SWIMMING_MULTIPLIER = Float.parseFloat(configPair[1]);
                            break;
                        case "action-sneaking-multiplier":
                            ACTION_SNEAKING_MULTIPLIER = Float.parseFloat(configPair[1]);
                            break;
                        case "action-crawling-multiplier":
                            ACTION_CRAWLING_MULTIPLIER = Float.parseFloat(configPair[1]);
                            break;

                        case "player-use-fall-distance":
                            USE_PLAYER_FALL_DISTANCE_FOR_MATH = Boolean.parseBoolean(configPair[1]);
                            break;

                        case "player-use-head-rotation":
                            USE_PLAYER_HEAD_ROTATION_FOR_MATH = Boolean.parseBoolean(configPair[1]);
                            break;

                        case "player-use-direct-hit-registration":
                            USE_PLAYER_DIRECT_HIT_REGISTRATION = Boolean.parseBoolean(configPair[1]);
                            break;

                        case "entity-ignore-original-velocity":
                            ENTITY_IGNORE_ORIGINAL_VELOCITY = Boolean.parseBoolean(configPair[1]);
                            break;

                        case "player-fall-distance-damping-function":
                            PLAYER_FALL_DISTANCE_DAMPING_FUNCTION = configPair[1];
                            break;
                        case "player-fall-distance-damping-coefficient":
                            PLAYER_FALL_DISTANCE_DAMPING_COEFFICIENT = Float.parseFloat(configPair[1]);
                            break;
                        case "player-fall-distance-damping-constant-1":
                            PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_1 = Float.parseFloat(configPair[1]);
                            break;
                        case "player-fall-distance-damping-constant-2":
                            PLAYER_FALL_DISTANCE_DAMPING_CONSTANT_2 = Float.parseFloat(configPair[1]);
                            break;
                        case "player-fall-distance-damping-sqrt-auto-clamp":
                            PLAYER_FALL_DISTANCE_DAMPING_SQRT_AUTO_CLAMP = Boolean.parseBoolean(configPair[1]);
                            break;
                        case "player-fall-distance-downwards-knockback-coefficient":
                            PLAYER_FALL_DISTANCE_DOWNWARDS_KNOCKBACK_COEFFICIENT = Float.parseFloat(configPair[1]);
                            break;

                        case "debug-chat-log":
                            DEBUG_CHAT_LOG = Boolean.parseBoolean(configPair[1]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("(readConfig) Unable to read config!");
        }
    }
}