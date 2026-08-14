package example.commands;

import java.util.Scanner;

import example.model.City;

/**
 * Команда вывода элемента с минимальным значением governor.
 */
public class MinByGovernorCommand extends BaseCommand {

    @Override
    public String getName() {
        return "min_by_governor";
    }

    @Override
    public String getDescription() {
        return "вывести элемент с минимальным значением governor";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        City city = collection.minByGovernor();
        if (city != null) {
            System.out.println(city);
        } else {
            System.out.println("Нет элементов с правителем");
        }
    }
}