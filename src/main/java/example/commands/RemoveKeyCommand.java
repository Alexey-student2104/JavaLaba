package example.commands;

import java.util.Scanner;

/**
 * Команда удаления элемента по ключу.
 */
public class RemoveKeyCommand extends BaseCommand {

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "удалить элемент по ключу";
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

        if (collection.remove(key) != null) {
            System.out.println("Элемент с ключом " + key + " удалён");
        } else {
            System.err.println("Элемент с ключом " + key + " не найден");
        }
    }
}