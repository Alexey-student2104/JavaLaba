package example.commands;

import example.collection.CityCollection;
import example.input.CityReader;
import example.model.City;
import java.util.Scanner;

public class InsertCommand implements Command {
    private CityCollection collection;
    private CityReader reader;

    public InsertCommand(CityCollection collection, CityReader reader) {
        this.collection = collection;
        this.reader = reader;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "добавить элемент с заданным ключом";
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

        if (collection.containsKey(key)) {
            System.err.println("Ошибка: элемент с ключом " + key + " уже существует");
            return;
        }

        City city = reader.readCity(scanner);
        if (city != null) {
            collection.put(key, city);
            System.out.println("Элемент добавлен (ключ: " + key + ")");
        }
    }
}