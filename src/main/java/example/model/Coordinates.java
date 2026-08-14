package example.model;

/**
 * Класс, представляющий координаты города.
 */
public class Coordinates {
    private Double x;   // не null
    private double y;

    public Coordinates(Double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() { return x; }
    public double getY() { return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}