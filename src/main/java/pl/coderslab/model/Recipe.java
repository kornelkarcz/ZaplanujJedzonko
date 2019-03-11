package pl.coderslab.model;

import java.sql.Date;

public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String description;
    private java.sql.Date created;
    private java.sql.Date updated;
    private int preparationTime;
    private String preparation;
    private Admins adminId;

    @Override
    public String toString() {
        return "Recipe[id=" + id + ", name='" + name + ", ingredients='" + ingredients + ", description='" + description
                + ", created='" + created + ", updated='" + updated + ", preparationTime=" + preparationTime +
                ", preparation=" + preparation + ", admins=" + adminId + "]";

    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Recipe(int id, String name, String ingredients, String description, java.sql.Date created, java.sql.Date updated, int preparationTime, String preparation, Admins adminId) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public java.sql.Date getCreated() {
        return created;
    }

    public void setCreated(java.sql.Date created) {
        this.created = created;
    }

    public java.sql.Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Admins getAdmins() {
        return adminId;
    }

    public void setAdmins(Admins adminId) {
        this.adminId = adminId;
    }
    public Recipe(int id, String name, String ingredients, String description, int preparationTime, String preparation) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
    }
}
