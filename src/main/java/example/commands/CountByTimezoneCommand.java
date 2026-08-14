package example.commands;

import java.util.Scanner;

/**
 * Команда подсчёта количества элементов с заданным часовым поясом.
 */
public class CountByTimezoneCommand extends BaseCommand {

    @Override
    public String getName() {
        return "count_by_timezone";
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов с заданным часовым поясом";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        if (argument == null) {
            System.err.println("Ошибка: укажите часовой пояс");
            return;
        }

        Double timezone;
        try {
            timezone = Double.parseDouble(argument);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: часовой пояс должен быть числом");
            return;
        }

        long count = collection.countByTimezone(timezone);
        System.out.println("Количество элементов с timezone " + timezone + ": " + count);
    }
}