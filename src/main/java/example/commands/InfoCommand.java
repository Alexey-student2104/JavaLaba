package example.commands;

import java.util.Scanner;

/**
 * Команда вывода информации о коллекции.
 */
public class InfoCommand extends BaseCommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "информация о коллекции";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        System.out.printf("Тип: %s%n", collection.getType());
        System.out.printf("Дата инициализации: %s%n", collection.getInitDate());
        System.out.printf("Количество элементов: %d%n", collection.size());
        System.out.println();
    }
}