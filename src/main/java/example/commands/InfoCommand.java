package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;

public class InfoCommand implements Command {
    private CityCollection collection;

    public InfoCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "информация о коллекции";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        System.out.printf("Тип: %s%n", collection.getType());
        System.out.printf("Дата инициализации: %s%n", collection.getInitDate());
        System.out.printf("Количество элементов: %d%n", collection.size());
        System.out.println();
    }
}