package example.commands;

import java.util.Scanner;

/**
 * Интерфейс для всех команд.
 * Реализует паттерн "Команда".
 */
public interface Command {
    /** Возвращает имя команды (например, "help"). */
    String getName();

    /** Возвращает описание команды. */
    String getDescription();

    /**
     * Выполняет команду.
     * @param argument аргумент команды (может быть null)
     * @param scanner для ввода данных
     * @param dependencies зависимости (коллекция, менеджер и т.д.)
     */
    void execute(String argument, Scanner scanner, Object... dependencies);
}