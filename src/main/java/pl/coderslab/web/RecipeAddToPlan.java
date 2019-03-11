package pl.coderslab.web;

import com.mysql.jdbc.StringUtils;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/app/recipe/plan/add")
public class RecipeAddToPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        //mealOrder w bazie = int; należy sprawdzić poprawność wprowadzonych danych; pozostałe parametry mają prawidłowy typ
        String mealOrder = request.getParameter("mealOrder");
        if (StringUtils.isNullOrEmpty(mealOrder) || !StringUtils.isStrictlyNumeric(mealOrder)) {
            request.setAttribute("message", "Numer posiłku jest nieprawidłowy, spróbuj jeszcze raz");
            doGet(request, response);
            return;
        }
        int mealOrderInt = Integer.parseInt(mealOrder);
        int recipeId = Integer.parseInt(request.getParameter("chosenRecipe"));
        String mealName = request.getParameter("mealName");
        int dayNameId = Integer.parseInt(request.getParameter("chosenDay"));
        int planId = Integer.parseInt(request.getParameter("chosenPlan"));

        addRecipeToPlan(recipeId,mealName,mealOrderInt,dayNameId,planId);

        request.setAttribute("ok", "Przepis dodano pomyślnie!");
        doGet(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession sess = request.getSession();

        Admins authorizedUser = (Admins) sess.getAttribute("currentUser");
        int userId = authorizedUser.getId();

//        czytam plany dla zalogowanego usera i przesyłam je jako atrybut do jsp
        List<Plan> allPlans = PlanDao.findAll();
        List<Plan> userPlans = new ArrayList<>();
        for (Plan plan : allPlans) {
            if (plan.getAdmins().getId() == userId) {
                userPlans.add(plan);
            }
        }
        //użykownik nie będzie mógł użyć tego dodawnia jeśli nie ma jeszcze planu
        if (userPlans.size() == 0){
            response.sendRedirect("/app/dashboard/?emptyplanorrecipe=true");
            return;
        }
        request.setAttribute("userPlans", userPlans);

//        czytam przepisy dla zalogowanego usera i przesyłam je jako atrybut do jsp
        List<Recipe> allRecipes = RecipeDao.findAll();
        List<Recipe> userRecipes = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (recipe.getAdmins().getId() == userId) {
                userRecipes.add(recipe);
            }
        }
        //użykownik nie będzie mógł użyć tego dodawnia jeśli nie ma jeszcze przepisu
        if (userRecipes.size() == 0){
            response.sendRedirect("/app/dashboard/?emptyplanorrecipe=true");
            return;
        }
        request.setAttribute("userRecipes", userRecipes);

//        czytam listę dni z tabeli i przesyłam je jako atrybut do jsp
        List<String[]> daysList = getDays();
        request.setAttribute("daysList", daysList);


        getServletContext().getRequestDispatcher("/app/recipe/plan/add.jsp").forward(request, response);
    }


    static List<String[]> getDays() {
        List<String[]> daysList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM day_name");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String[] day = new String[2];
                day[0] = resultSet.getString("id");
                day[1] = resultSet.getString("name");
                daysList.add(day);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daysList;
    }


    private static void addRecipeToPlan(int recipeId, String mealName, int mealOrder, int dayNameId, int planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO recipe_plan(recipe_id,meal_name,`order`,day_name_id,plan_id) VALUES (?,?,?,?,?)")) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setString(2, mealName);
            preparedStatement.setInt(3, mealOrder);
            preparedStatement.setInt(4, dayNameId);
            preparedStatement.setInt(5, planId);
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



