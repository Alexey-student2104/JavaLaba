package example.commands;

import example.collection.CityCollection;
import example.storage.StorageService;
import java.util.Scanner;

public class SaveCommand implements Command {
    private CityCollection collection;
    private StorageService storage;

    public SaveCommand(CityCollection collection, StorageService storage) {
        this.collection = collection;
        this.storage = storage;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    @Override
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        if (collection.getFileName() == null) {
            System.err.println("Ошибка: не указан файл для сохранения");
            return;
        }

        try {
            storage.save(collection.getCollection(), collection.getFileName());
            System.out.println("Коллекция сохранена в: " + collection.getFileName());
        } catch (Exception e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}