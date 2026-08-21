package example.manager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import example.commands.Command;
import example.commands.ExitCommand;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public void registerAll(Command... commandList) {
        for (Command cmd : commandList) {
            register(cmd);
        }
    }

    public boolean execute(String input, Scanner scanner, Object... dependencies) {
        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;

        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(argument, scanner, dependencies);
            return command instanceof ExitCommand;
        } else {
            System.err.println("Неизвестная команда: " + commandName);
            return false;
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}