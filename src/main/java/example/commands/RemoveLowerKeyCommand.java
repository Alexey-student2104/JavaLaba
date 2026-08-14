package example.commands;

import java.util.Scanner;

/**
 * Команда удаления элементов с ключом меньше заданного.
 */
public class RemoveLowerKeyCommand extends BaseCommand {

    @Override
    public String getName() {
        return "remove_lower_key";
    }

    @Override
    public String getDescription() {
        return "удалить элементы с ключом меньше заданного";
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

        int count = collection.removeLowerKey(key);
        System.out.println("Удалено элементов: " + count);
    }
}