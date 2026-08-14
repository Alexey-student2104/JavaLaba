package example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import example.collection.CityCollection;
import example.commands.CommandManager;
import example.commands.CommandRegistrar;
import example.commands.HelpCommand;
import example.script.ScriptExecutor;
import example.xml.XMLParser;

/**
 * Главный класс программы.
 * Точка входа. Инициализирует коллекцию, команды и запускает цикл ввода.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Проверка аргументов командной строки
        if (args.length == 0) {
            System.err.println("Ошибка: Не указан путь к файлу данных.");
            System.err.println("Использование: java -jar lab5-1.0.jar cities.xml");
            System.exit(1);
        }

        String fileName = args[0];
        CityCollection collection = new CityCollection();
        XMLParser parser = new XMLParser();

        // 2. Загрузка коллекции из XML файла
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName))) {
            collection = parser.loadCollection(bis);
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

        // 3. Создание и регистрация команд (всё вынесено в CommandRegistrar)
        CommandManager commandManager = new CommandManager();
        ScriptExecutor scriptExecutor = new ScriptExecutor(commandManager, collection);
        CommandRegistrar registrar = new CommandRegistrar(commandManager, collection, scriptExecutor);
        registrar.registerAll();

        // 4. Запуск интерактивного режима
        Scanner scanner = new Scanner(System.in);
        HelpCommand help = new HelpCommand();
        help.execute(null, scanner, commandManager);

        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.print("> ");

            // Обработка Ctrl+D
            if (!scanner.hasNextLine()) {
                System.out.println("\nЗавершение работы");
                break;
            }

            String input = scanner.nextLine();

            // Обработка пустой строки
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