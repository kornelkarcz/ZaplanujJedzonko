package pl.coderslab.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class Plan {
    private int id;
    private String name;
    private String description;
    private Date created;
    private Admins adminId;


    public Plan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan(String name, String description, Admins adminId) {
        this.name = name;
        this.description = description;
        this.adminId = adminId;
    }

    public Plan(String name, String description) {
        this.name = name;
        this.description = description;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Admins getAdminId() {
        return adminId;
    }

    public void setAdminId(Admins adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", adminId=" + adminId +
                '}';
    }
}
