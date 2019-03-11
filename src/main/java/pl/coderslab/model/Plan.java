package pl.coderslab.model;

import java.sql.Timestamp;

public class Plan {

    private int id;
    private String name;
    private String description;
    private Timestamp created;
    private Admins admins;

    public Plan(String name, String description, Admins admins) {
        this.name = name;
        this.description = description;
        this.admins = admins;
    }

    public Plan(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Admins getAdmins() {
        return admins;
    }

    public void setAdmins(Admins admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", admins=" + admins +
                '}';
    }
}
