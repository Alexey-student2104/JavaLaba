package example.commands;

import java.util.List;
import java.util.Scanner;

/**
 * Команда вывода всех часовых поясов в порядке возрастания.
 */
public class PrintFieldAscendingTimezoneCommand extends BaseCommand {

    @Override
    public String getName() {
        return "print_field_ascending_timezone";
    }

    @Override
    public String getDescription() {
        return "вывести значения timezone в порядке возрастания";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
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