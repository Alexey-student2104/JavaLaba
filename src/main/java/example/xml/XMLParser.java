package example.xml;

import java.io.BufferedInputStream;
import java.time.LocalDateTime;
import java.util.Date;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import example.collection.CityCollection;
import example.model.City;
import example.model.Coordinates;
import example.model.Human;
import example.model.StandardOfLiving;

/**
 * Парсер XML для загрузки коллекции из файла.
 * При ошибках в данных конкретного города — он пропускается,
 * остальные города загружаются.
 */
public class XMLParser {

    public CityCollection loadCollection(BufferedInputStream bis) throws Exception {
        CityCollection collection = new CityCollection();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(bis);

        City currentCity = null;
        Integer currentKey = null;
        
        // Временные переменные для одного города
        int currentId = 0;
        String currentName = null;
        Coordinates currentCoords = null;
        Double currentX = null;
        Double currentY = null;
        LocalDateTime currentCreationDate = null;
        long currentArea = 0;
        Integer currentPopulation = null;
        Long currentMeters = null;
        Date currentEstDate = null;
        Double currentTimezone = null;
        StandardOfLiving currentSol = null;
        Human currentGovernor = null;
        Long governorAge = null;

        String currentValue = "";
        boolean hasError = false;
        String errorMessage = "";

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    currentValue = "";
                    String tag = reader.getLocalName();

                    if (tag.equals("city")) {
                        // Сброс всех переменных для нового города
                        hasError = false;
                        errorMessage = "";
                        currentKey = null;
                        currentId = 0;
                        currentName = null;
                        currentCoords = null;
                        currentX = null;
                        currentY = null;
                        currentCreationDate = null;
                        currentArea = 0;
                        currentPopulation = null;
                        currentMeters = null;
                        currentEstDate = null;
                        currentTimezone = null;
                        currentSol = null;
                        governorAge = null;
                        currentGovernor = null;

                        try {
                            String keyAttr = reader.getAttributeValue(null, "key");
                            if (keyAttr == null) {
                                hasError = true;
                                errorMessage = "Отсутствует атрибут key";
                            } else {
                                currentKey = Integer.parseInt(keyAttr);
                            }
                        } catch (NumberFormatException e) {
                            hasError = true;
                            errorMessage = "Неверный формат ключа";
                        }
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    currentValue += reader.getText();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String tagName = reader.getLocalName();
                    String value = currentValue.trim();

                    if (hasError) break;

                    try {
                        switch (tagName) {
                            case "id":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "id не может быть пустым";
                                } else {
                                    currentId = Integer.parseInt(value);
                                    if (currentId <= 0) {
                                        hasError = true;
                                        errorMessage = "id должен быть > 0";
                                    }
                                }
                                break;
                                
                            case "name":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "name не может быть пустым";
                                } else {
                                    currentName = value;
                                }
                                break;
                                
                            case "x":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "x не может быть пустым";
                                } else {
                                    try {
                                        currentX = Double.parseDouble(value.replace(',', '.'));
                                    } catch (NumberFormatException e) {
                                        hasError = true;
                                        errorMessage = "x должен быть числом";
                                    }
                                }
                                break;
                                
                            case "y":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "y не может быть пустым";
                                } else {
                                    try {
                                        currentY = Double.parseDouble(value.replace(',', '.'));
                                    } catch (NumberFormatException e) {
                                        hasError = true;
                                        errorMessage = "y должен быть числом";
                                    }
                                }
                                break;
                                
                            case "coordinates":
                                if (currentX != null && currentY != null) {
                                    currentCoords = new Coordinates(currentX, currentY);
                                } else {
                                    hasError = true;
                                    errorMessage = "Отсутствуют координаты (x или y)";
                                }
                                break;
                                
                            case "creationDate":
                                currentCreationDate = value.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(value);
                                break;
                                
                            case "area":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "area не может быть пустым";
                                } else {
                                    currentArea = Long.parseLong(value);
                                    if (currentArea <= 0) {
                                        hasError = true;
                                        errorMessage = "area должен быть > 0";
                                    }
                                }
                                break;
                                
                            case "population":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "population не может быть пустым";
                                } else {
                                    currentPopulation = Integer.parseInt(value);
                                    if (currentPopulation <= 0) {
                                        hasError = true;
                                        errorMessage = "population должен быть > 0";
                                    }
                                }
                                break;
                                
                            case "metersAboveSeaLevel":
                                currentMeters = value.isEmpty() ? null : Long.parseLong(value);
                                break;
                                
                            case "establishmentDate":
                                currentEstDate = value.isEmpty() ? null : new Date(Long.parseLong(value));
                                break;
                                
                            case "timezone":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "timezone не может быть пустым";
                                } else {
                                    currentTimezone = Double.parseDouble(value.replace(',', '.'));
                                    if (currentTimezone <= -13 || currentTimezone > 15) {
                                        hasError = true;
                                        errorMessage = "timezone должен быть в диапазоне (-13, 15]";
                                    }
                                }
                                break;
                                
                            case "standardOfLiving":
                                if (value.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "standardOfLiving не может быть пустым";
                                } else {
                                    currentSol = StandardOfLiving.valueOf(value);
                                }
                                break;
                                
                            case "age":
                                governorAge = value.isEmpty() ? null : Long.parseLong(value);
                                if (governorAge != null && governorAge <= 0) {
                                    hasError = true;
                                    errorMessage = "age должен быть > 0";
                                }
                                break;
                                
                            case "governor":
                                currentGovernor = (governorAge != null && governorAge > 0) ? new Human(governorAge) : null;
                                break;
                                
                            case "city":
                                // Проверяем обязательные поля
                                if (currentKey == null) {
                                    hasError = true;
                                    errorMessage = "Отсутствует ключ";
                                } else if (currentName == null || currentName.isEmpty()) {
                                    hasError = true;
                                    errorMessage = "Отсутствует name";
                                } else if (currentCoords == null) {
                                    hasError = true;
                                    errorMessage = "Отсутствуют coordinates";
                                } else if (currentPopulation == null) {
                                    hasError = true;
                                    errorMessage = "Отсутствует population";
                                } else if (currentTimezone == null) {
                                    hasError = true;
                                    errorMessage = "Отсутствует timezone";
                                } else if (currentSol == null) {
                                    hasError = true;
                                    errorMessage = "Отсутствует standardOfLiving";
                                }

                                if (hasError) {
                                    System.err.println("⚠ Пропущен город с ключом " + currentKey + ": " + errorMessage);
                                } else {
                                    City city = new City(
                                        currentId, currentName, currentCoords, currentCreationDate,
                                        currentArea, currentPopulation, currentMeters, currentEstDate,
                                        currentTimezone, currentSol, currentGovernor
                                    );
                                    collection.put(currentKey, city);
                                }
                                break;
                        }
                    } catch (Exception e) {
                        hasError = true;
                        errorMessage = tagName + ": " + e.getMessage();
                    }
                    break;
            }
        }

        reader.close();
        return collection;
    }
}