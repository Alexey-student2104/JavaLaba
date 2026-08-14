package example.commands;

import java.util.Scanner;

/**
 * Команда удаления элементов с ключом больше заданного.
 */
public class RemoveGreaterKeyCommand extends BaseCommand {

    @Override
    public String getName() {
        return "remove_greater_key";
    }

    @Override
    public String getDescription() {
        return "удалить элементы с ключом больше заданного";
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

        int count = collection.removeGreaterKey(key);
        System.out.println("Удалено элементов: " + count);
    }
}