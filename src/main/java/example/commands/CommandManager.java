package example.commands;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Менеджер команд.
 * Регистрирует команды и выполняет их по имени.
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Регистрирует команду в менеджере.
     */
    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Выполняет команду по строке ввода.
     * @param input строка с именем команды и аргументом
     * @param scanner для ввода данных
     * @param dependencies зависимости (коллекция, менеджер)
     * @return true если была вызвана команда exit
     */
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

    /**
     * Возвращает все зарегистрированные команды.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}