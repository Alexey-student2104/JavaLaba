package example.collection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import example.model.City;

public class CityCollection {
    private Map<Integer, City> collection;
    private LocalDateTime initializationDate;
    private String fileName;

    public CityCollection() {
        this.collection = new LinkedHashMap<>();
        this.initializationDate = LocalDateTime.now();
    }

    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileName() { return fileName; }
    public String getType() { return "LinkedHashMap<Integer, City>"; }
    public LocalDateTime getInitDate() { return initializationDate; }
    public Map<Integer, City> getCollection() { return collection; }

    public void put(Integer key, City city) { collection.put(key, city); }
    public City get(Integer key) { return collection.get(key); }
    public City remove(Integer key) { return collection.remove(key); }
    public boolean containsKey(Integer key) { return collection.containsKey(key); }
    public void clear() { collection.clear(); }
    public int size() { return collection.size(); }
    public List<City> getAll() { return new ArrayList<>(collection.values()); }
    public boolean containsId(int id) {
        return collection.values().stream().anyMatch(c -> c.getId() == id);
    }

    public boolean updateById(int id, City newCity) {
        for (Map.Entry<Integer, City> entry : collection.entrySet()) {
            if (entry.getValue().getId() == id) {
                collection.put(entry.getKey(), newCity);
                return true;
            }
        }
        return false;
    }

    public boolean replaceIfLower(Integer key, City newCity) {
        City old = collection.get(key);
        if (old != null && newCity.compareTo(old) < 0) {
            collection.put(key, newCity);
            return true;
        }
        return false;
    }

    public int removeGreaterKey(Integer key) {
        List<Integer> toRemove = collection.keySet().stream()
            .filter(k -> k > key).collect(Collectors.toList());
        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    public int removeLowerKey(Integer key) {
        List<Integer> toRemove = collection.keySet().stream()
            .filter(k -> k < key).collect(Collectors.toList());
        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    public City minByGovernor() {
        return collection.values().stream()
            .filter(c -> c.getGovernor() != null)
            .min(Comparator.comparingLong(c -> c.getGovernor().getAge()))
            .orElse(null);
    }

    public long countByTimezone(Double timezone) {
        return collection.values().stream()
            .filter(c -> c.getTimezone() != null && c.getTimezone().equals(timezone))
            .count();
    }

    public List<Double> getAllTimezonesSorted() {
        return collection.values().stream()
            .map(City::getTimezone)
            .filter(Objects::nonNull)
            .sorted()
            .collect(Collectors.toList());
    }
}