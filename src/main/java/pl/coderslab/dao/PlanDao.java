package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;
import pl.coderslab.model.DayMealRecipe;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    // Zapytania SQL
    private static final String CREATE_PLAN_QUERY = " INSERT INTO plan(name,description,created,adminId) VALUES( ?, ?, now(), ?) ";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan WHERE id = ? ";
    private static final String FIND_ALL_PLAN_QUERY = "SELECT * FROM plan";
    private static final String FIND_ALL_PLAN_BY_USER_ID_QUERY = "SELECT * FROM plan where admin_id = ?";
    private static final String READ_PLAN_QUERY = "SELECT * FROM plan WHERE id = ? ";
    private static final String UPDATE_PLAN_QUERY = "UPDATE plan SET name = ? , description = ? WHERE id = ? ";
    //Wyświetlenie liczby planów danego użytkownika
    private static final String DISPLAY_PLANS_QUERY = "SELECT COUNT(*) FROM plan WHERE admin_id = ?";
    //Wyswietlenia ostatnio dodanego plany danego uzytkownika
    private static final String DISPLAY_LAST_ADDED_PLAN_QUERY = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "day_name_id =  (SELECT MAX(id) from plan WHERE admin_id = ? " +
            "ORDER by day_name.order, recipe_plan.order)";

    //Wyswietlenia szczegołow planu (dayName, mealName, recipeName
    private static final String DISPLAY_PLAN_DETAILS = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE plan_id = ?\n" +
            "ORDER by day_name.order, recipe_plan.order";

    // Create plan line
    public Plan createPlan(Plan plan) {

        try (Connection connection = DbUtil.getConnection()) {

            PreparedStatement preStmt = connection.prepareStatement(CREATE_PLAN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, plan.getName());
            preStmt.setString(2, plan.getDescription());
            preStmt.setInt(3, plan.getAdminId().getId());
            int result = preStmt.executeUpdate();

            if (result != 1) {

                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Delete plan by line id
    public void deletePlan(Integer planId) {
        try (Connection connection = DbUtil.getConnection()) {

            PreparedStatement preStmt = connection.prepareStatement(DELETE_PLAN_QUERY);
            preStmt.setInt(1, planId);
            preStmt.executeUpdate();

            boolean deleted = preStmt.execute();
            if (!deleted) {
                throw new NotFoundException("Line not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Return all plans
    public List<Plan> findAllPlans() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preStmt = connection.prepareStatement(FIND_ALL_PLAN_QUERY);
            ResultSet resultSet = preStmt.executeQuery();

            while (resultSet.next()) {
                Plan planAdd = new Plan();
                planAdd.setId(resultSet.getInt("id"));
                planAdd.setName(resultSet.getString("name"));
                planAdd.setDescription(resultSet.getString("description"));
                planAdd.setCreated(resultSet.getDate("created"));
                planList.add(planAdd);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    //Przeciążona metoda findAllPlans
    public List<Plan> findAllPlan(Integer adminId) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preStmt = connection.prepareStatement(FIND_ALL_PLAN_BY_USER_ID_QUERY);
            preStmt.setInt(1, adminId);
            ResultSet resultSet = preStmt.executeQuery();

            while (resultSet.next()) {
                Plan planAdd = new Plan();
                planAdd.setId(resultSet.getInt("id"));
                planAdd.setName(resultSet.getString("name"));
                planAdd.setDescription(resultSet.getString("description"));
                planAdd.setCreated(resultSet.getDate("created"));
                planList.add(planAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }


    //Get plan by Id
    public Plan readPlan(Integer planId) {
        Plan planRead = new Plan();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preStmt = connection.prepareStatement(READ_PLAN_QUERY);
            preStmt.setInt(1, planId);

            try (ResultSet resultSet = preStmt.executeQuery()) {
                while (resultSet.next()) {
                    planRead.setId(resultSet.getInt("id"));
                    planRead.setName(resultSet.getString("name"));
                    planRead.setDescription(resultSet.getString("description"));
                    planRead.setCreated(resultSet.getDate("created"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planRead;
    }

    //Update plan
    public void updatePLan(Plan plan) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preStmt = connection.prepareStatement(UPDATE_PLAN_QUERY);
            preStmt.setString(1, plan.getName());
            preStmt.setString(1, plan.getDescription());
            preStmt.setInt(3, plan.getId());
            preStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get plans count
     */

    public int getPlanCount(Integer adminId) {

        int count = 0;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DISPLAY_PLANS_QUERY);) {
            statement.setInt(1, adminId);


            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    count++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Get last added plan by adminId
     */

    public List<List<String>> getLastAddedPlan(Integer adminId) {
        List<String> list = new ArrayList<>();

        List<List<String>> list1 = new ArrayList<>();


        if (adminId <= 0) {
            System.out.println("Niepoprawne id użytkownika");
        }

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DISPLAY_LAST_ADDED_PLAN_QUERY)) {

            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            StringBuilder sb = new StringBuilder();

            while (resultSet.next()) {

                for (int i = 1; i < columnCount; i++) {
                    if (i > 1) {
                        list.add(sb.append(resultSet.getString(i)).append(" ").toString());
                    }
                    list1.add(list);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list1;
    }

    //metoda wyswietlajaca szczegoly planu
    public List<DayMealRecipe> getPlanDetails(int planId) {
        List<DayMealRecipe> list = new ArrayList<>();

        if (planId <= 0) {
            System.out.println("Niepoprawne id planu");
        } else {
            try (Connection connection = DbUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DISPLAY_PLAN_DETAILS)) {
                preparedStatement.setInt(1, planId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    DayMealRecipe dayMealRecipe = new DayMealRecipe();
                    dayMealRecipe.setDayName(resultSet.getString(1));
                    dayMealRecipe.setMealName(resultSet.getString(2));
                    dayMealRecipe.setRecipeName(resultSet.getString(3));
                    dayMealRecipe.setRecipeDescription(resultSet.getString(4));

                    list.add(dayMealRecipe);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return list;
    }


}
    
