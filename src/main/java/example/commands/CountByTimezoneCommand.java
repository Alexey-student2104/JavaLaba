package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;

public class CountByTimezoneCommand implements Command {
    private CityCollection collection;

    public CountByTimezoneCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "count_by_timezone";
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов с заданным часовым поясом";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
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