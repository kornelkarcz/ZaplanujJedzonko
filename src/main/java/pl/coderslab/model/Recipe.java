package pl.coderslab.model;

import java.sql.Timestamp;

public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String description;
    private Timestamp created;
    private Timestamp updated;
    private int preparationTime;
    private String preparation;
    private Admins admins;

    public Recipe() {
    }

    public Recipe(int id, String name, String ingredients, String description, int preparationTime, String preparation, Admins admins) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.admins = admins;
    }

    public Recipe(String name, String ingredients, String description, int preparationTime, String preparation, Admins admins) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.admins = admins;
    }

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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
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
        return admins;
    }

    public void setAdmins(Admins admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", preparationTime=" + preparationTime +
                ", preparation='" + preparation + '\'' +
                ", admins=" + admins +
                '}';
    }
}
