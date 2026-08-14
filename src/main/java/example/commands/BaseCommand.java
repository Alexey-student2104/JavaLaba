package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;

/**
 * Абстрактный базовый класс для команд, работающих с коллекцией.
 * Содержит общую логику получения коллекции.
 */
public abstract class BaseCommand implements Command {
    protected CityCollection collection;

    public void setCollection(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        // Получаем коллекцию из зависимостей, если ещё не установлена
        if (collection == null && dependencies.length > 0 && dependencies[0] instanceof CityCollection) {
            this.collection = (CityCollection) dependencies[0];
        }
        doExecute(argument, scanner);
    }

    /**
     * Реализация логики команды.
     * @param argument аргумент команды
     * @param scanner для ввода данных
     */
    protected abstract void doExecute(String argument, Scanner scanner);
}