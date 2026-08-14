package example.collection;

import example.model.City;
import example.model.Human;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для управления коллекцией городов.
 * Использует LinkedHashMap для хранения с сохранением порядка вставки.
 */
public class CityCollection {
    private final LinkedHashMap<Integer, City> collection;
    private final LocalDateTime initializationDate;
    private String fileName;

    public CityCollection() {
        this.collection = new LinkedHashMap<>();
        this.initializationDate = LocalDateTime.now();
    }

    // ========== Геттеры/сеттеры ==========

    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileName() { return fileName; }
    public String getType() { return "LinkedHashMap<Integer, City>"; }
    public LocalDateTime getInitDate() { return initializationDate; }
    public LinkedHashMap<Integer, City> getCollection() { return collection; }

    // ========== Базовые операции ==========

    public void put(Integer key, City city) { collection.put(key, city); }
    public City get(Integer key) { return collection.get(key); }
    public City remove(Integer key) { return collection.remove(key); }
    public boolean containsKey(Integer key) { return collection.containsKey(key); }
    public void clear() { collection.clear(); }
    public int size() { return collection.size(); }
    public List<City> getAll() { return new ArrayList<>(collection.values()); }

    // ========== Специальные методы ==========

    /**
     * Обновляет город по id (используется в UpdateCommand).
     * Сохраняет ключ, заменяет значение.
     */
    public boolean updateById(int id, City newCity) {
        for (Map.Entry<Integer, City> entry : collection.entrySet()) {
            if (entry.getValue().getId() == id) {
                collection.put(entry.getKey(), newCity);
                return true;
            }
        }
        return false;
    }

    public boolean containsId(int id) {
        return collection.values().stream().anyMatch(c -> c.getId() == id);
    }

    /**
     * Заменяет город, если новый меньше старого по natural order.
     */
    public boolean replaceIfLower(Integer key, City newCity) {
        City old = collection.get(key);
        if (old != null && newCity.compareTo(old) < 0) {
            collection.put(key, newCity);
            return true;
        }
        return false;
    }

    /**
     * Удаляет все элементы с ключом больше заданного.
     */
    public int removeGreaterKey(Integer key) {
        List<Integer> toRemove = collection.keySet().stream()
            .filter(k -> k > key)
            .collect(Collectors.toList());
        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    /**
     * Удаляет все элементы с ключом меньше заданного.
     */
    public int removeLowerKey(Integer key) {
        List<Integer> toRemove = collection.keySet().stream()
            .filter(k -> k < key)
            .collect(Collectors.toList());
        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    /**
     * Находит город с самым молодым правителем.
     */
    public City minByGovernor() {
        return collection.values().stream()
            .filter(c -> c.getGovernor() != null)
            .min(Comparator.comparingLong(c -> c.getGovernor().getAge()))
            .orElse(null);
    }

    /**
     * Подсчитывает количество городов с заданным часовым поясом.
     */
    public long countByTimezone(Double timezone) {
        return collection.values().stream()
            .filter(c -> c.getTimezone() != null && c.getTimezone().equals(timezone))
            .count();
    }

    /**
     * Возвращает все часовые пояса в порядке возрастания.
     */
    public List<Double> getAllTimezonesSorted() {
        return collection.values().stream()
            .map(City::getTimezone)
            .filter(Objects::nonNull)
            .sorted()
            .collect(Collectors.toList());
    }
}