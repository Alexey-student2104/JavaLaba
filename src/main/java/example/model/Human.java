package example.model;

import javax.xml.bind.annotation.XmlElement;

public class Human {
    private long age;

    public Human() {}

    public Human(long age) {
        this.age = age;
    }

    @XmlElement
    public long getAge() { return age; }
    public void setAge(long age) { this.age = age; }

    @Override
    public String toString() {
        return "Human{age=" + age + "}";
    }
}