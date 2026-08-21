package example.commands;

import java.util.Scanner;

import example.collection.CityCollection;

public class ClearCommand implements Command {
    private CityCollection collection;

    public ClearCommand(CityCollection collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }
}