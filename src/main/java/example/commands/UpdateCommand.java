package example.commands;

import example.collection.CityCollection;
import example.input.CityReader;
import example.model.City;
import java.time.LocalDateTime;
import java.util.Scanner;

public class UpdateCommand implements Command {
    private CityCollection collection;
    private CityReader reader;

    public UpdateCommand(CityCollection collection, CityReader reader) {
        this.collection = collection;
        this.reader = reader;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить элемент по id (id и дата создания не меняются)";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        if (argument == null) {
            System.err.println("Ошибка: укажите id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: id должен быть целым числом");
            return;
        }

        if (!collection.containsId(id)) {
            System.err.println("Ошибка: элемент с id " + id + " не найден");
            return;
        }

        City oldCity = collection.getAll().stream()
            .filter(c -> c.getId() == id)
            .findFirst().orElse(null);

        if (oldCity == null) return;

        final int ORIGINAL_ID = oldCity.getId();
        final LocalDateTime ORIGINAL_DATE = oldCity.getCreationDate();

        City newCity = reader.readCity(scanner);
        if (newCity != null) {
            newCity.setId(ORIGINAL_ID);
            newCity.setCreationDate(ORIGINAL_DATE);
            collection.updateById(id, newCity);
            System.out.println("Элемент с id " + id + " обновлён");
        }
    }
}