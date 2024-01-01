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
                    # Negative numbers makes damage unlimited
                    damage-max-vertical=100
                    damage-max-horizontal=-1.0

                    # Knockback multipliers
                    # Scales the knockback with incoming damage velocity
                    knockback-multiplier-x=1.0
                    knockback-multiplier-y=1.0
                    knockback-multiplier-z=1.0
                    
                    # Action multipliers
                    # Scales damage and knockback depending on what the attacker's action is
                    action-sprinting-multiplier=2.0
                    action-swimming-multiplier=0.5
                    action-sneaking-multiplier=0.5
                    action-crawling-multiplier=0.25

                    # Debug
                    # Logs debug messages to the chat
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