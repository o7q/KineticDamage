package com.o7q.kineticdamage.commands;

import com.o7q.kineticdamage.commands.reload.ReloadCommand;

public class CommandRegistry {
    public static void registerCommands() {
        ReloadCommand.register();
    }
}