package com.o7q.kineticdamage.config;

import java.io.*;

import static com.o7q.kineticdamage.config.ConfigValues.*;

public class ConfigManager
{
    public static void ConfigInit()
    {
        File configFile = new File("config\\kineticdamage.properties");

        if (!configFile.exists())
            CreateDefaultConfig();

        ReadConfig();
    }

    private static void CreateDefaultConfig()
    {
        try
        {
            String defaultConfig =
                    """
                    # KineticDamage config

                    # Damage multipliers
                    # These values will be multiplied with the calculated damage values
                    damage-multiplier-vertical=1.0
                    damage-multiplier-horizontal=1.0
                    
                    # Specify the max amount of damage that can be done
                    # Negative numbers will uncap the damage
                    damage-max-vertical=100
                    damage-max-horizontal=-1.0

                    # Knockback multipliers
                    # Scales the knockback with attacker's incoming velocity
                    knockback-multiplier-x=1.0
                    knockback-multiplier-y=1.0
                    knockback-multiplier-z=1.0
                    
                    # Action multipliers
                    # Scales damage and knockback depending on the attacker's action
                    action-sprinting-multiplier=1.0
                    action-swimming-multiplier=1.0
                    action-sneaking-multiplier=1.0
                    action-crawling-multiplier=1.0
                    
                    # Use player fall distance for damage
                    # The farther the player falls the more damage they will do
                    # Also, this damage will be added on top of the damage already done by the player's velocity
                    player-use-fall-distance=true
                    
                    # Use player head rotation vector for math
                    # This will use the player's head rotation instead of body velocity to calculate values
                    # If enabled, the server will always use the players head rotation vector to calculate knockback vectors instead of the velocity vector of the player itself
                    # In other words, the knockback will always occur in the direction the player is looking, this is not as realistic but it can be very fun
                    player-use-head-rotation=true

                    # Debug
                    # Logs debug messages to the chat (per player)
                    debug-chat-log=false
                    """;

            FileWriter configFileWriter = new FileWriter("config\\kineticdamage.properties");
            configFileWriter.write(defaultConfig);
            configFileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Unable to create config!");
        }
    }

    private static void ReadConfig()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("config\\kineticdamage.properties")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty() && line.charAt(0) != '#')
                {
                    String[] configPair = line.split("=", -1);

                    switch (configPair[0])
                    {
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
                        case "player-use-head-rotation":
                            USE_PLAYER_HEAD_ROTATION_FOR_MATH = Boolean.parseBoolean(configPair[1]);
                            break;

                        case "debug-chat-log":
                            DEBUG_CHAT_LOG = Boolean.parseBoolean(configPair[1]);
                            break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Unable to read config!");
        }
    }
}