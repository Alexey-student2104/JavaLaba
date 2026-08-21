package example.commands;

import java.util.List;
import java.util.Scanner;

import example.collection.CityCollection;

public class PrintFieldAscendingTimezoneCommand implements Command {
    private CityCollection collection;

    public PrintFieldAscendingTimezoneCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "print_field_ascending_timezone";
    }

    @Override
    public String getDescription() {
        return "вывести значения timezone в порядке возрастания";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        List<Double> timezones = collection.getAllTimezonesSorted();
        if (timezones.isEmpty()) {
            System.out.println("Нет элементов с timezone");
        } else {
            for (Double tz : timezones) {
                System.out.println(tz);
            }
        }
    }
}