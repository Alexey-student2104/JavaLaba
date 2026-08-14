package example.commands;

import java.util.Scanner;

/**
 * Команда очистки коллекции.
 */
public class ClearCommand extends BaseCommand {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }
}