package pl.coderslab.dao;

import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {

    //ZAPYTANIA SQL
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, updated, preparation_time, preparation, admin_id) values(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe WHERE id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT * FROM recipe ";
    private static final String READ_RECIPE_QUERY = "SELECT * FROM recipe WHERE id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, ingredients = ?, description = ?, updated = ?, preparation_time = ?, preparation = ?, admin_id = ? WHERE id = ?;";
    //Wyświetlenie wszystkich przepisów danego użytkownika
    private static final String DISPLAY_RECIPES_QUERY = "SELECT COUNT(*) FROM recipe WHERE admin_id = ?;";


    /**
     * Get reicpe by id
     *
     * @param recipeId
     * @return
     */

    public Recipe read(Integer recipeId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY)) {

            statement.setInt(1, recipeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setName(resultSet.getString("name"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setCreated(resultSet.getDate("created"));
                    recipe.setUpdated(resultSet.getDate("updated"));
                    recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    recipe.setPreparation(resultSet.getString("preparation"));
                //    int adminId = resultSet.getInt("admin_id");
                    //Admins admins = Admins. loadAdmin by id

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipe;
    }
    /**
     * asadsa
     * Get reicpe by id
     *
     * @param recipeId
     * @param adminId
     * @return
     * @Override
     */
    public Recipe read(Integer recipeId,Integer adminId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY+" AND "+"admin_id="+adminId)) {

            statement.setInt(1, recipeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setName(resultSet.getString("name"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    recipe.setPreparation(resultSet.getString("preparation"));
                    recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    //int adminId = resultSet.getInt("admin_id");
                    //Admins admins = Admins. loadAdmin by id

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipe;
    }
    /**
     * Return all recipse
     *
     * @return
     */

    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipeToAdd = new Recipe();
                recipeToAdd.setId(resultSet.getInt("id"));
                recipeToAdd.setName(resultSet.getString("name"));
                recipeToAdd.setIngredients(resultSet.getString("ingredients"));
                recipeToAdd.setIngredients(resultSet.getString("description"));
                recipeToAdd.setCreated(resultSet.getDate("created"));
                recipeToAdd.setUpdated(resultSet.getDate("updated"));
                recipeToAdd.setPreparationTime(resultSet.getInt("preparation_time"));
                recipeToAdd.setPreparation(resultSet.getString("preparation"));
              //  recipeToAdd.setAdmins(resultSet.getInt("admin_id"));
                recipeList.add(recipeToAdd);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    /**
     * Create a recipe
     *
     * @param
     * @return
     */
     /*
        Return all recipis by admin Id
            @Rreturn
           @Override
        */

    public List<Recipe> findAll( Integer adminId) {
        List<pl.coderslab.model.Recipe> recipeList = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPES_QUERY+" WHERE "+" admin_id= "+adminId );
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pl.coderslab.model.Recipe recipeToAdd = new pl.coderslab.model.Recipe();
                recipeToAdd.setId(resultSet.getInt("id"));
                recipeToAdd.setName(resultSet.getString("name"));
                recipeToAdd.setIngredients(resultSet.getString("ingredients"));
                recipeToAdd.setDescription(resultSet.getString("description"));
                recipeToAdd.setCreated(resultSet.getDate("created"));
                recipeToAdd.setUpdated(resultSet.getDate("updated"));
                recipeToAdd.setPreparationTime(resultSet.getInt("preparation_time"));
                recipeToAdd.setPreparation(resultSet.getString("preparation"));
               // recipeToAdd
                recipeList.add(recipeToAdd);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
    public Recipe create(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, recipe.getName());
            insertStm.setString(2, recipe.getIngredients());
            insertStm.setString(3, recipe.getDescription());
            insertStm.setDate(4, recipe.getCreated());
            insertStm.setDate(5, recipe.getUpdated());
            insertStm.setInt(6, recipe.getPreparationTime());
            insertStm.setString(7, recipe.getPreparation());

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove recipe by id
     *
     * @param recipeId
     */

    public void delete(Integer recipeId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY);) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();

            boolean deleted = statement.execute();

            if (!deleted) {
                throw new RuntimeException("Product not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update recipe
     *
     * @param recipe
     */

    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY);) {
            statement.setInt(7, recipe.getId());
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setDate(4, recipe.getUpdated());
            statement.setInt(5, recipe.getPreparationTime());
            statement.setString(6, recipe.getPreparation());
            //czekamy na Admins

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display count
     *
     * @paramrecipe
     */

    public int getRecipeCount(Integer adminId) {

        int count = 0;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DISPLAY_RECIPES_QUERY);) {
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

}