package example.commands;

import java.util.Scanner;

import example.model.City;

/**
 * Команда замены значения по ключу, если новое значение меньше старого.
 */
public class ReplaceIfLowerCommand extends BaseCommand {

    @Override
    public String getName() {
        return "replace_if_lower";   // ← изменено с "replace_if_lowe"
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
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
        City newCity = readCity(scanner);

        if (newCity != null && newCity.compareTo(oldCity) < 0) {
            collection.replaceIfLower(key, newCity);
            System.out.println("Элемент заменён");
        } else if (newCity != null) {
            System.out.println("Новое значение не меньше старого");
        }
    }

    private City readCity(Scanner scanner) {
        // Код чтения города (полностью идентичен InsertCommand)
        // ...
        // Для краткости здесь не повторяем, но в реальном проекте он должен быть
        return null; // заглушка
    }
}