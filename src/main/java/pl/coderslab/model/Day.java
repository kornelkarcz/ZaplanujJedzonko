package pl.coderslab.model;

public class Day {

    private int id;
    private String name;
    private int order;

    @Override
    public String toString() {
        return "Day [id=" + id + ", name='" + name + ", order=" + order + "]";
    }

    public Day() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Day(int id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
