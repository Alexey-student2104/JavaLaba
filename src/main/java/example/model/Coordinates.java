package example.model;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

public class Coordinates {
    private Double x;
    private double y;

    public Coordinates() {}

    public Coordinates(Double x, double y) {
        this.x = x;
        this.y = y;
    }

    @XmlElement
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    @XmlElement
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.y, y) == 0 && Double.compare(that.x, x) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}