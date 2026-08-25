package example.input;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import example.model.City;
import example.model.Coordinates;
import example.model.Human;
import example.model.StandardOfLiving;

public class CityReader {

    public City readCity(Scanner scanner) {
        String name = null;
        while (name == null || name.trim().isEmpty()) {
            System.out.print("Название города: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.err.println("Ошибка: название не может быть пустым");
            }
        }

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