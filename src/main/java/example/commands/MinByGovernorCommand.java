package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;
import example.model.City;

public class MinByGovernorCommand implements Command {
    private CityCollection collection;

    public MinByGovernorCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "min_by_governor";
    }

    @Override
    public String getDescription() {
        return "вывести элемент с минимальным значением governor";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        City city = collection.minByGovernor();
        if (city != null) {
            System.out.println(city);
        } else {
            System.out.println("Нет элементов с правителем");
        }
    }
}