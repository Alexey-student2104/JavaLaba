package example.commands;

import java.io.FileOutputStream;
import java.util.Scanner;

import example.model.City;

/**
 * Команда сохранения коллекции в XML файл.
 */
public class SaveCommand extends BaseCommand {

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        if (collection.getFileName() == null) {
            System.err.println("Ошибка: не указан файл для сохранения");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(collection.getFileName())) {
            String xml = toXML();
            fos.write(xml.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            System.out.println("Коллекция сохранена в: " + collection.getFileName());
        } catch (Exception e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    private String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<cities>\n");

        for (var entry : collection.getCollection().entrySet()) {
            xml.append("  <city key=\"").append(entry.getKey()).append("\">\n");
            City c = entry.getValue();
            
            // ID
            xml.append("    <id>").append(c.getId()).append("</id>\n");
            
            // Название
            xml.append("    <name>").append(escapeXML(c.getName())).append("</name>\n");
            
            // Координаты (ОБЯЗАТЕЛЬНО!)
            xml.append("    <coordinates>\n");
            xml.append("      <x>").append(c.getCoordinates().getX()).append("</x>\n");
            xml.append("      <y>").append(c.getCoordinates().getY()).append("</y>\n");
            xml.append("    </coordinates>\n");
            
            // Площадь
            xml.append("    <area>").append(c.getArea()).append("</area>\n");
            
            // Население
            xml.append("    <population>").append(c.getPopulation()).append("</population>\n");
            
            // Высота над уровнем моря (может быть null)
            xml.append("    <metersAboveSeaLevel>");
            if (c.getMetersAboveSeaLevel() != null) {
                xml.append(c.getMetersAboveSeaLevel());
            }
            xml.append("</metersAboveSeaLevel>\n");
            
            // Дата основания (может быть null)
            xml.append("    <establishmentDate>");
            if (c.getEstablishmentDate() != null) {
                xml.append(c.getEstablishmentDate().getTime());
            }
            xml.append("</establishmentDate>\n");
            
            // Часовой пояс
            xml.append("    <timezone>").append(c.getTimezone()).append("</timezone>\n");
            
            // Уровень жизни
            xml.append("    <standardOfLiving>").append(c.getStandardOfLiving()).append("</standardOfLiving>\n");
            
            // Правитель (может быть null) (ОБЯЗАТЕЛЬНО!)
            xml.append("    <governor>\n");
            if (c.getGovernor() != null) {
                xml.append("      <age>").append(c.getGovernor().getAge()).append("</age>\n");
            }
            xml.append("    </governor>\n");
            
            xml.append("  </city>\n");
        }

        xml.append("</cities>");
        return xml.toString();
    }

    private String escapeXML(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;");
    }
}