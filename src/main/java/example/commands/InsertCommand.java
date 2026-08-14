package example.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import example.model.City;
import example.model.Coordinates;
import example.model.Human;
import example.model.StandardOfLiving;

/**
 * Команда добавления нового элемента в коллекцию.
 * Поддерживает повторный ввод при ошибках.
 */
public class InsertCommand extends BaseCommand {

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "добавить элемент с заданным ключом";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        if (argument == null || argument.equals("null")) {
            System.err.println("Ошибка: ключ не может быть null");
            return;
        }

        Integer key;
        try {
            key = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: ключ должен быть целым числом");
            return;
        }

        if (collection.containsKey(key)) {
            System.err.println("Ошибка: элемент с ключом " + key + " уже существует");
            return;
        }

        City city = readCity(scanner);
        if (city != null) {
            collection.put(key, city);
            System.out.println("Элемент добавлен (ключ: " + key + ")");
        }
    }

    private City readCity(Scanner scanner) {
        // 1. Название
        String name = null;
        while (name == null || name.trim().isEmpty()) {
            System.out.print("Название города: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.err.println("Ошибка: название не может быть пустым");
            }
        }

        // 2. Координата X
        Double x = null;
        while (x == null) {
            System.out.print("Координата x: ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.err.println("Ошибка: x не может быть null");
                    continue;
                }
                x = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: x должен быть числом");
            }
        }

        // 3. Координата Y
        double y = 0;
        while (true) {
            System.out.print("Координата y: ");
            try {
                y = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: y должен быть числом");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        // 4. Площадь
        long area = 0;
        while (area <= 0) {
            System.out.print("Площадь (>0): ");
            try {
                area = Long.parseLong(scanner.nextLine().trim());
                if (area <= 0) {
                    System.err.println("Ошибка: площадь должна быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: площадь должна быть целым числом");
            }
        }

        // 5. Население
        Integer population = null;
        while (population == null || population <= 0) {
            System.out.print("Население (>0): ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.err.println("Ошибка: население не может быть null");
                    continue;
                }
                population = Integer.parseInt(input);
                if (population <= 0) {
                    System.err.println("Ошибка: население должно быть больше 0");
                    population = null;
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: население должно быть целым числом");
            }
        }

        // 6. Высота над уровнем моря
        Long metersAboveSeaLevel = null;
        while (true) {
            System.out.print("Высота над уровнем моря (Enter - null): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                metersAboveSeaLevel = null;
                break;
            }
            try {
                metersAboveSeaLevel = Long.parseLong(input);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: введите число или оставьте пустым");
            }
        }

        // 7. Дата основания
        Date establishmentDate = null;
        while (true) {
            System.out.print("Дата основания (yyyy-MM-dd, Enter - null): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                establishmentDate = null;
                break;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                establishmentDate = sdf.parse(input);
                break;
            } catch (Exception e) {
                System.err.println("Ошибка: неверный формат. Используйте yyyy-MM-dd");
            }
        }

        // 8. Часовой пояс
        Double timezone = null;
        while (timezone == null) {
            System.out.print("Часовой пояс (-13 < tz ≤ 15): ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.err.println("Ошибка: часовой пояс не может быть null");
                    continue;
                }
                timezone = Double.parseDouble(input);
                if (timezone <= -13 || timezone > 15) {
                    System.err.println("Ошибка: часовой пояс должен быть в диапазоне (-13, 15]");
                    timezone = null;
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: часовой пояс должен быть числом");
            }
        }

        // 9. Уровень жизни (enum)
        StandardOfLiving standardOfLiving = null;
        while (standardOfLiving == null) {
            System.out.println("Доступные значения: " + StandardOfLiving.getAvailableValues());
            System.out.print("Уровень жизни: ");
            try {
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.isEmpty()) {
                    System.err.println("Ошибка: уровень жизни не может быть null");
                    continue;
                }
                standardOfLiving = StandardOfLiving.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: неверное значение. Доступны: " + StandardOfLiving.getAvailableValues());
            }
        }

        // 10. Правитель
        Human governor = null;
        while (true) {
            System.out.print("Возраст правителя (Enter - null): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                governor = null;
                break;
            }
            try {
                long age = Long.parseLong(input);
                if (age <= 0) {
                    System.err.println("Ошибка: возраст должен быть больше 0");
                    continue;
                }
                governor = new Human(age);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: возраст должен быть числом");
            }
        }

        return new City(name, coordinates, area, population, metersAboveSeaLevel,
                       establishmentDate, timezone, standardOfLiving, governor);
    }
}