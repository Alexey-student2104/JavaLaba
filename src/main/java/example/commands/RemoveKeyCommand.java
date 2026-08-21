package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;

public class RemoveKeyCommand implements Command {
    private CityCollection collection;

    public RemoveKeyCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "удалить элемент по ключу";
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

        if (collection.remove(key) != null) {
            System.out.println("Элемент с ключом " + key + " удалён");
        } else {
            System.err.println("Элемент с ключом " + key + " не найден");
        }
    }
}