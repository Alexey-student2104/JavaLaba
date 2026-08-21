package example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import example.collection.CityCollection;
import example.commands.HelpCommand;
import example.input.CityReader;
import example.manager.CommandManager;
import example.manager.CommandRegistry;
import example.model.City;
import example.script.ScriptExecutor;
import example.storage.StorageService;
import example.storage.XmlStorageService;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ошибка: Не указан путь к файлу данных.");
            System.err.println("Использование: java -jar lab5-1.0.jar cities.xml");
            System.exit(1);
        }

        String fileName = args[0];
        CityCollection collection = new CityCollection();
        StorageService storage = new XmlStorageService();
        CityReader reader = new CityReader();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName))) {
            Map<Integer, City> loaded = storage.load(fileName);
            for (Map.Entry<Integer, City> entry : loaded.entrySet()) {
                collection.put(entry.getKey(), entry.getValue());
            }
            collection.setFileName(fileName);
            System.out.println("Коллекция загружена из: " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден. Создана новая коллекция.");
            collection.setFileName(fileName);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки: " + e.getMessage());
            System.err.println("Создана пустая коллекция.");
            collection.setFileName(fileName);
        }

        CommandManager commandManager = new CommandManager();
        ScriptExecutor scriptExecutor = new ScriptExecutor(commandManager, collection);
        CommandRegistry registry = new CommandRegistry(commandManager, collection, reader, scriptExecutor, storage);
        registry.registerAll();

        Scanner scanner = new Scanner(System.in);
        HelpCommand help = new HelpCommand();
        help.execute(null, scanner, commandManager);

        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.print("> ");

            if (!scanner.hasNextLine()) {
                System.out.println("\nЗавершение работы");
                break;
            }

            String input = scanner.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Введите команду");
                continue;
            }

            shouldExit = commandManager.execute(input, scanner, collection, commandManager);
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }
}