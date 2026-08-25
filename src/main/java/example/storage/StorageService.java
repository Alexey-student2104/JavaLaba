package example.storage;

import java.util.Map;

import example.model.City;

public interface StorageService {
    void save(Map<Integer, City> collection, String fileName) throws Exception;
    Map<Integer, City> load(String fileName) throws Exception;
}