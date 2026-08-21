package example.storage;

import example.model.City;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

@XmlRootElement(name = "cities")
class CityWrapper {
    private Map<Integer, City> cities = new LinkedHashMap<>();

    @XmlElement(name = "city")
    public Map<Integer, City> getCities() { return cities; }

    public void setCities(Map<Integer, City> cities) { this.cities = cities; }
}

public class XmlStorageService implements StorageService {

    @Override
    public void save(Map<Integer, City> collection, String fileName) throws Exception {
        JAXBContext context = JAXBContext.newInstance(CityWrapper.class, City.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        CityWrapper wrapper = new CityWrapper();
        wrapper.setCities(collection);

        marshaller.marshal(wrapper, new File(fileName));
    }

    @Override
    public Map<Integer, City> load(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            return new LinkedHashMap<>();
        }

        JAXBContext context = JAXBContext.newInstance(CityWrapper.class, City.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CityWrapper wrapper = (CityWrapper) unmarshaller.unmarshal(file);
        return wrapper.getCities();
    }
}