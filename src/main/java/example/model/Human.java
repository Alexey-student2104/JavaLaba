package example.model;

/**
 * Класс, представляющий правителя города.
 */
public class Human {
    private long age;   // >0

    public Human(long age) {
        this.age = age;
    }

    public long getAge() { return age; }

    @Override
    public String toString() {
        return "Human{age=" + age + "}";
    }
}