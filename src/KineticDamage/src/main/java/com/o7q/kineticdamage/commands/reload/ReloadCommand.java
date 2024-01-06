package com.o7q.kineticdamage.commands.reload;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;

import static com.o7q.kineticdamage.KineticDamage.MOD_ID;
import static com.o7q.kineticdamage.config.ConfigManager.configInit;
import static net.minecraft.server.command.CommandManager.literal;

public class ReloadCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(literal(MOD_ID)
                .then(literal("reload")
                        .executes(context -> {
                            int status = configInit();
                            switch (status) {
                                case 1:
                                    context.getSource().sendFeedback(() -> Text.literal("[KineticDamage] Reload successful!"), true);
                                    break;
                                case -1:
                                    context.getSource().sendFeedback(() -> Text.literal("[KineticDamage] Reload failed!"), true);
                                    break;
                            }

                            return status;
                        })
                )
        )));
    }
}