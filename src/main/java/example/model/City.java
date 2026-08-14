package example.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Класс, представляющий город.
 * Реализует Comparable для сортировки по id (естественный порядок).
 */
public class City implements Comparable<City> {
    private int id;                    // >0, уникальный, генерируется автоматически
    private String name;               // не null, не пустая
    private Coordinates coordinates;   // не null
    private LocalDateTime creationDate; // не null, генерируется автоматически
    private long area;                 // >0
    private Integer population;        // >0, не null
    private Long metersAboveSeaLevel;
    private Date establishmentDate;
    private Double timezone;           // -13 < tz ≤ 15
    private StandardOfLiving standardOfLiving; // не null
    private Human governor;            // может быть null

    /**
     * Конструктор для нового города (id и creationDate генерируются автоматически).
     */
    public City(String name, Coordinates coordinates, long area, Integer population,
                Long metersAboveSeaLevel, Date establishmentDate, Double timezone,
                StandardOfLiving standardOfLiving, Human governor) {
        this.id = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.establishmentDate = establishmentDate;
        this.timezone = timezone;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    /**
     * Конструктор для загрузки из файла (с указанием id и creationDate).
     */
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

    // ========== Геттеры ==========

    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public long getArea() { return area; }
    public Integer getPopulation() { return population; }
    public Long getMetersAboveSeaLevel() { return metersAboveSeaLevel; }
    public Date getEstablishmentDate() { return establishmentDate; }
    public Double getTimezone() { return timezone; }
    public StandardOfLiving getStandardOfLiving() { return standardOfLiving; }
    public Human getGovernor() { return governor; }

    // ========== Сеттеры для полей, которые могут меняться ==========

    public void setId(int id) { this.id = id; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    // ========== Comparable ==========

    /**
     * Сравнение по id для сортировки по умолчанию.
     */
    @Override
    public int compareTo(City other) {
        return Integer.compare(this.id, other.id);
    }

    // ========== Переопределения ==========

    @Override
    public String toString() {
        return String.format("City{id=%d, name='%s', population=%d, timezone=%.1f}",
            id, name, population, timezone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}