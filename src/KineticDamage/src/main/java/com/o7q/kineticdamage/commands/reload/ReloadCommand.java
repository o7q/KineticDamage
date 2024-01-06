package com.o7q.kineticdamage.commands.reload;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.o7q.kineticdamage.KineticDamage.MOD_ID;
import static com.o7q.kineticdamage.config.ConfigManager.*;
import static net.minecraft.server.command.CommandManager.literal;

public class ReloadCommand {
    protected static final String SUCCESS = "[KineticDamage] Reload successful!";
    protected static final String WARNING = "[KineticDamage] Detected log/mod version inconsistency! Regenerating config...";
    protected static final String ERROR = "[KineticDamage] Reload failed!";

    public static void register() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(literal(MOD_ID)
                .then(literal("reload")
                        .executes(context -> {
                            int status = readConfig();
                            switch (status) {
                                case 1:
                                    context.getSource().sendFeedback(() -> Text.literal(SUCCESS), true);
                                    break;
                                case 0:
                                    context.getSource().sendFeedback(() -> Text.literal(WARNING), true);
                                    createDefaultConfig();
                                    int status2 = readConfig();
                                    switch (status2)
                                    {
                                        case 1:
                                            context.getSource().sendFeedback(() -> Text.literal(SUCCESS), true);
                                            break;
                                        case 0:
                                        case -1:
                                            context.getSource().sendFeedback(() -> Text.literal(ERROR), true);
                                            break;
                                    }
                                    break;
                                case -1:
                                    context.getSource().sendFeedback(() -> Text.literal(ERROR), true);
                                    break;
                            }

                            return status;
                        })
                )
        )));
    }
}