package example.commands;

import example.manager.CommandManager;
import java.util.Scanner;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        CommandManager manager = null;
        for (Object dep : dependencies) {
            if (dep instanceof CommandManager) {
                manager = (CommandManager) dep;
                break;
            }
        }
        if (manager == null) {
            System.err.println("Ошибка: менеджер команд недоступен");
            return;
        }

        System.out.println();
        for (Command cmd : manager.getCommands().values()) {
            System.out.printf("%-20s - %s%n", cmd.getName(), cmd.getDescription());
        }
        System.out.println();
    }
}