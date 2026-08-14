package example.commands;

import java.util.Scanner;

/**
 * Команда выхода из программы.
 */
public class ExitCommand implements Command {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "завершить программу";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        // Ничего не выводим — тихое завершение
    }
}