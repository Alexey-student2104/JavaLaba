package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;

public class RemoveLowerKeyCommand implements Command {
    private CityCollection collection;

    public RemoveLowerKeyCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "remove_lower_key";
    }

    @Override
    public String getDescription() {
        return "удалить элементы с ключом меньше заданного";
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

        int count = collection.removeLowerKey(key);
        System.out.println("Удалено элементов: " + count);
    }
}