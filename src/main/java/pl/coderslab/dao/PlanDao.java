package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecentPlan;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan (name, description,created,admin_id) VALUES (?,?,NOW(),?)";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan";
    private static final String FIND_BY_ADMIN_QUERY = "SELECT * from plan where admin_id = ?";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ? WHERE id = ?";
    private static final String SHOW_PLAN_NUMBERS = "select count(*) from plan where admin_id=?;";


    private static final String SHOW_RECENT_PLAN = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description, plan.name as plan_name, recipe_plan.id\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id\n" +
            "JOIN plan on plan.id = recipe_plan.plan_id\n" +
            "WHERE plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.order, recipe_plan.order";

    private static final String DELETE_RECIPE_FROM_PLAN = "DELETE FROM recipe_plan where id=?";

    private static final String FIND_RECIPES_BY_PLAN_ID = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description, recipe_plan.id as recipe_plan_id\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE plan_id=? \n" +
            "ORDER by day_name.order, recipe_plan.order";

    public static Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setInt(3, plan.getAdmins().getId());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Plan readById(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getTimestamp("created"));
                    Admins admin = AdminDao.read(resultSet.getInt("admin_id"));
                    plan.setAdmins(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    public static void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(3, plan.getId());
            statement.setString(2, plan.getDescription());
            statement.setString(1, plan.getName());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Plan> findAll() {

        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getTimestamp("created"));
                Admins admin = AdminDao.read(resultSet.getInt("admin_id"));
                planToAdd.setAdmins(admin);
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    public static List<Plan> findByAdminId(Integer adminId) {

        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ADMIN_QUERY)
        ) {

            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getTimestamp("created"));
                Admins admin = AdminDao.read(resultSet.getInt("admin_id"));
                planToAdd.setAdmins(admin);
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    public static int showPlanNumbers(int adminId) {
        int planNumbers = 0;
        if (adminId == 0 || adminId < 0) {
            System.out.println("Niepoprawne id użytkownika");
        } else {
            try (Connection connection = DbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SHOW_PLAN_NUMBERS)) {
                statement.setInt(1, adminId);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    planNumbers = set.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return planNumbers;
    }


    public static List<RecentPlan> showRecentPlan(int adminId) {
        List<RecentPlan> list = new ArrayList<>();

        if (adminId == 0 || adminId < 0) {
            System.out.println("Niepoprawne id użytkownika");
        } else {
            try (Connection connection = DbUtil.getConnection();

                 PreparedStatement statement = connection.prepareStatement(SHOW_RECENT_PLAN)) {
                statement.setInt(1, adminId);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    RecentPlan plan = new RecentPlan();
                    plan.setDayName(set.getString(1));
                    plan.setMealName(set.getString(2));
                    plan.setRecipeName(set.getString(3));
                    plan.setRecipeDescription(set.getString(4));
                    plan.setPlanName(set.getString(5));
                    plan.setRecipePlanId(set.getInt(6));

                    list.add(plan);
                }


            } catch (SQLException e) {
                System.out.println("Problem z bazą danych");

            }


        }

        return list;
    }

    public static void deleteRecipeFromPlan(int recipePlanId) {
        try (Connection connection = DbUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_FROM_PLAN)) {
            statement.setInt(1, recipePlanId);
            statement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<RecipePlan> findRecipesByPlanId(Integer planId) {

        List<RecipePlan> list = new ArrayList<>();

        if (planId == 0 || planId < 0) {
            System.out.println("Niepoprawne id planu");
        } else {
            try (Connection connection = DbUtil.getConnection();

                 PreparedStatement statement = connection.prepareStatement(FIND_RECIPES_BY_PLAN_ID)) {
                statement.setInt(1, planId);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    RecipePlan plan = new RecipePlan();
                    plan.setDayName(set.getString(1));
                    plan.setMealName(set.getString(2));
                    plan.setRecipeName(set.getString(3));
                    plan.setRecipeDescription(set.getString(4));
                    plan.setRecipePlanId(set.getInt(5));

                    list.add(plan);
                }

            } catch (SQLException e) {
                System.out.println("Problem z bazą danych");

            }
        } return list;
    }
}
