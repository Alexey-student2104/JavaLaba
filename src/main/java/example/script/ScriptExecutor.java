package example.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import example.collection.CityCollection;
import example.input.CityReader;
import example.manager.CommandManager;
import example.model.City;
import example.model.Coordinates;
import example.model.Human;
import example.model.StandardOfLiving;

public class ScriptExecutor {
    private final Set<String> activeScripts = new HashSet<>();
    private final CommandManager commandManager;
    private final CityCollection collection;
    private final CityReader cityReader;

    public ScriptExecutor(CommandManager commandManager, CityCollection collection) {
        this.commandManager = commandManager;
        this.collection = collection;
        this.cityReader = new CityReader();
    }

    public void executeScript(String fileName, Scanner mainScanner) {
        if (fileName == null || fileName.trim().isEmpty()) {
            System.err.println("Ошибка: не указано имя файла");
            return;
        }

        String absolutePath;
        try {
            absolutePath = new File(fileName).getCanonicalPath();
        } catch (IOException e) {
            System.err.println("Ошибка: не удалось получить путь к файлу " + fileName);
            return;
        }

        if (activeScripts.contains(absolutePath)) {
            System.err.println("Ошибка: обнаружена рекурсия! Скрипт " + fileName + " уже выполняется");
            return;
        }

        activeScripts.add(absolutePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                System.out.println("[" + fileName + ":" + lineNum + "] > " + line);

                String[] parts = line.split("\\s+", 2);
                String cmd = parts[0];
                String arg = parts.length > 1 ? parts[1] : null;

                if (cmd.equals("insert") || cmd.equals("update") || cmd.equals("replace_if_lower")) {
                    executeNonInteractive(cmd, arg, reader, lineNum, fileName);
                } else if (cmd.equals("execute_script")) {
                    executeScript(arg, mainScanner);
                } else {
                    commandManager.execute(line, mainScanner, collection, commandManager);
                }
            }

            System.out.println("Скрипт " + fileName + " выполнен");

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл " + fileName + " не найден");
        } catch (Exception e) {
            System.err.println("Ошибка выполнения скрипта: " + e.getMessage());
        } finally {
            activeScripts.remove(absolutePath);
        }
    }

    private void executeNonInteractive(String command, String argument, BufferedReader reader,
                                        int lineNum, String fileName) {
        try {
            switch (command) {
                case "insert":
                    executeInsertFromScript(argument, reader);
                    break;
                case "update":
                    executeUpdateFromScript(argument, reader);
                    break;
                case "replace_if_lower":
                    executeReplaceFromScript(argument, reader);
                    break;
                default:
                    System.err.println("  Неизвестная команда: " + command);
            }
        } catch (Exception e) {
            System.err.println("  Ошибка в команде " + command + ": " + e.getMessage());
        }
    }

    private void executeInsertFromScript(String keyStr, BufferedReader reader) throws Exception {
        if (keyStr == null) {
            throw new IllegalArgumentException("Ключ не указан");
        }

        Integer key;
        try {
            key = Integer.parseInt(keyStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ключ должен быть целым числом");
        }

        if (collection.containsKey(key)) {
            throw new IllegalArgumentException("Ключ уже существует");
        }

        // Читаем данные из файла
        String name = reader.readLine();
        if (name == null) throw new IllegalArgumentException("Недостаточно данных для города");

        Double x = Double.parseDouble(reader.readLine());
        double y = Double.parseDouble(reader.readLine());
        long area = Long.parseLong(reader.readLine());
        Integer population = Integer.parseInt(reader.readLine());
        String metersStr = reader.readLine();
        Long meters = metersStr.isEmpty() ? null : Long.parseLong(metersStr);
        String dateStr = reader.readLine();
        Date date = dateStr.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        Double timezone = Double.parseDouble(reader.readLine());
        StandardOfLiving sol = StandardOfLiving.valueOf(reader.readLine().toUpperCase());
        String ageStr = reader.readLine();
        Human governor = ageStr.isEmpty() ? null : new Human(Long.parseLong(ageStr));

        City city = new City(name, new Coordinates(x, y), area, population,
                            meters, date, timezone, sol, governor);
        collection.put(key, city);
        System.out.println("  Элемент добавлен (ключ: " + key + ")");
    }

    private void executeUpdateFromScript(String idStr, BufferedReader reader) throws Exception {
        if (idStr == null) {
            throw new IllegalArgumentException("ID не указан");
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID должен быть целым числом");
        }

        City oldCity = collection.getAll().stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);

        if (oldCity == null) {
            throw new IllegalArgumentException("Элемент с id " + id + " не найден");
        }

        final int ORIGINAL_ID = oldCity.getId();
        final java.time.LocalDateTime ORIGINAL_CREATION_DATE = oldCity.getCreationDate();

        // Читаем данные из файла
        String name = reader.readLine();
        if (name == null) throw new IllegalArgumentException("Недостаточно данных для города");

        Double x = Double.parseDouble(reader.readLine());
        double y = Double.parseDouble(reader.readLine());
        long area = Long.parseLong(reader.readLine());
        Integer population = Integer.parseInt(reader.readLine());
        String metersStr = reader.readLine();
        Long meters = metersStr.isEmpty() ? null : Long.parseLong(metersStr);
        String dateStr = reader.readLine();
        Date date = dateStr.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        Double timezone = Double.parseDouble(reader.readLine());
        StandardOfLiving sol = StandardOfLiving.valueOf(reader.readLine().toUpperCase());
        String ageStr = reader.readLine();
        Human governor = ageStr.isEmpty() ? null : new Human(Long.parseLong(ageStr));

        City newCity = new City(name, new Coordinates(x, y), area, population,
                               meters, date, timezone, sol, governor);
        newCity.setId(ORIGINAL_ID);
        newCity.setCreationDate(ORIGINAL_CREATION_DATE);

        collection.updateById(id, newCity);
        System.out.println("  Элемент с id " + id + " обновлён");
    }

    private void executeReplaceFromScript(String keyStr, BufferedReader reader) throws Exception {
        if (keyStr == null) {
            throw new IllegalArgumentException("Ключ не указан");
        }

        Integer key;
        try {
            key = Integer.parseInt(keyStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ключ должен быть целым числом");
        }

        if (!collection.containsKey(key)) {
            throw new IllegalArgumentException("Элемент с ключом " + key + " не найден");
        }

        City oldCity = collection.get(key);

        // Читаем данные из файла
        String name = reader.readLine();
        if (name == null) throw new IllegalArgumentException("Недостаточно данных для города");

        Double x = Double.parseDouble(reader.readLine());
        double y = Double.parseDouble(reader.readLine());
        long area = Long.parseLong(reader.readLine());
        Integer population = Integer.parseInt(reader.readLine());
        String metersStr = reader.readLine();
        Long meters = metersStr.isEmpty() ? null : Long.parseLong(metersStr);
        String dateStr = reader.readLine();
        Date date = dateStr.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        Double timezone = Double.parseDouble(reader.readLine());
        StandardOfLiving sol = StandardOfLiving.valueOf(reader.readLine().toUpperCase());
        String ageStr = reader.readLine();
        Human governor = ageStr.isEmpty() ? null : new Human(Long.parseLong(ageStr));

        City newCity = new City(name, new Coordinates(x, y), area, population,
                               meters, date, timezone, sol, governor);

        if (newCity.compareTo(oldCity) < 0) {
            collection.replaceIfLower(key, newCity);
            System.out.println("  Элемент заменён");
        } else {
            System.out.println("  Новое значение не меньше старого");
        }
    }
}