package pl.coderslab.model;

public class RecipePlan {

    private String dayName;
    private String mealName;
    private String recipeName;
    private String recipeDescription;
    private int recipePlanId;

    public RecipePlan(String dayName, String mealName, String recipeName, String recipeDescription, int recipePlanId) {
        this.dayName = dayName;
        this.mealName = mealName;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipePlanId = recipePlanId;

    }

    public RecipePlan(){}

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public int getRecipePlanId() {
        return recipePlanId;
    }

    public void setRecipePlanId(int recipePlanId) {
        this.recipePlanId = recipePlanId;
    }

    @Override
    public String toString() {
        return "RecipePlan{" +
                "dayName='" + dayName + '\'' +
                ", mealName='" + mealName + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", recipePlanId=" + recipePlanId +
                '}';
    }
}
