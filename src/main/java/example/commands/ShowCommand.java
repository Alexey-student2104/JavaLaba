package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;
import example.model.City;

public class ShowCommand implements Command {
    private CityCollection collection;

    public ShowCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "показать все элементы коллекции";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        if (collection.size() == 0) {
            System.out.println("Коллекция пуста\n");
            return;
        }

        System.out.println();
        System.out.printf("%-6s | %-20s | %-15s | %-10s | %-10s%n",
            "ID", "НАЗВАНИЕ", "НАСЕЛЕНИЕ", "ЧАС.ПОЯС", "ПРАВИТЕЛЬ");
        System.out.println("--------+----------------------+-----------------+------------+------------");

        for (City city : collection.getAll()) {
            System.out.printf("%-6d | %-20s | %-,15d | %-8.1f   | %-10s%n",
                city.getId(),
                truncate(city.getName(), 20),
                city.getPopulation(),
                city.getTimezone(),
                city.getGovernor() != null ? city.getGovernor().getAge() + " лет" : "—");
        }

        System.out.printf("Всего: %d город(ов)%n%n", collection.size());
    }

    private String truncate(String text, int length) {
        if (text == null) return "null";
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }
}