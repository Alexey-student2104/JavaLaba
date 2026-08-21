package example.commands;

import example.collection.CityCollection;
import example.input.CityReader;
import example.model.City;
import java.util.Scanner;

public class ReplaceIfLowerCommand implements Command {
    private CityCollection collection;
    private CityReader reader;

    public ReplaceIfLowerCommand(CityCollection collection, CityReader reader) {
        this.collection = collection;
        this.reader = reader;
    }

    @Override
    public String getName() {
        return "replace_if_lower";
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        if (argument == null || argument.equals("null")) {
            System.err.println("Ошибка: ключ не может быть null");
            return;
        }

        Integer key;
        try {
            key = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: ключ должен быть целым числом");
            return;
        }

        if (!collection.containsKey(key)) {
            System.err.println("Элемент с ключом " + key + " не найден");
            return;
        }

        City oldCity = collection.get(key);
        City newCity = reader.readCity(scanner);

        if (newCity != null && newCity.compareTo(oldCity) < 0) {
            collection.replaceIfLower(key, newCity);
            System.out.println("Элемент заменён");
        } else if (newCity != null) {
            System.out.println("Новое значение не меньше старого");
        }
    }
}