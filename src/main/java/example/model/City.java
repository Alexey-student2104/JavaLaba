package example.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class City implements Comparable<City> {
    private int id;
    private String name;
    private Coordinates coordinates;

    private LocalDateTime creationDate;

    private long area;
    private Integer population;
    private Long metersAboveSeaLevel;
    private Date establishmentDate;
    private Double timezone;
    private StandardOfLiving standardOfLiving;
    private Human governor;

    public City() {}

    public City(String name, Coordinates coordinates, long area, Integer population,
                Long metersAboveSeaLevel, Date establishmentDate, Double timezone,
                StandardOfLiving standardOfLiving, Human governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.establishmentDate = establishmentDate;
        this.timezone = timezone;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.id = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        this.creationDate = LocalDateTime.now();
    }

    public City(int id, String name, Coordinates coordinates, LocalDateTime creationDate,
                long area, Integer population, Long metersAboveSeaLevel, Date establishmentDate,
                Double timezone, StandardOfLiving standardOfLiving, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.establishmentDate = establishmentDate;
        this.timezone = timezone;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    @XmlElement
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    @XmlTransient
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    @XmlElement
    public long getArea() { return area; }
    public void setArea(long area) { this.area = area; }

    @XmlElement
    public Integer getPopulation() { return population; }
    public void setPopulation(Integer population) { this.population = population; }

    @XmlElement
    public Long getMetersAboveSeaLevel() { return metersAboveSeaLevel; }
    public void setMetersAboveSeaLevel(Long metersAboveSeaLevel) { this.metersAboveSeaLevel = metersAboveSeaLevel; }

    @XmlElement
    public Date getEstablishmentDate() { return establishmentDate; }
    public void setEstablishmentDate(Date establishmentDate) { this.establishmentDate = establishmentDate; }

    @XmlElement
    public Double getTimezone() { return timezone; }
    public void setTimezone(Double timezone) { this.timezone = timezone; }

    @XmlElement
    public StandardOfLiving getStandardOfLiving() { return standardOfLiving; }
    public void setStandardOfLiving(StandardOfLiving standardOfLiving) { this.standardOfLiving = standardOfLiving; }

    @XmlElement
    public Human getGovernor() { return governor; }
    public void setGovernor(Human governor) { this.governor = governor; }

    @Override
    public int compareTo(City other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
               Objects.equals(name, city.name) &&
               Objects.equals(coordinates, city.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates);
    }

    @Override
    public String toString() {
        return "City{id=" + id + ", name='" + name + "'}";
    }
}