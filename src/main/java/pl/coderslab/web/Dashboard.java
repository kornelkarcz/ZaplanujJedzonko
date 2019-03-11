package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.RecentPlan;
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

@WebServlet("/app/dashboard/")
public class Dashboard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sess = request.getSession();
        Admins admin = (Admins) sess.getAttribute("currentUser");

        int numberOfRecipes = RecipeDao.countRecipeById(admin.getId());
        int numberOfPlans = PlanDao.showPlanNumbers(admin.getId());
        request.setAttribute("recipeNumber", numberOfRecipes);
        request.setAttribute("planNumber", numberOfPlans);

        List<RecentPlan> recentPlan = PlanDao.showRecentPlan(admin.getId());
        if (recentPlan.size() != 0) {
            List<String> days = showUniqueDays(admin.getId());

            request.setAttribute("recentPlan", recentPlan);
            request.setAttribute("days", days);
        } else {
            request.setAttribute("emptyPlan","emptyPlan");
        }

        getServletContext().getRequestDispatcher("/app/dashboard.jsp").forward(request, response);
    }


    public static List<String> showUniqueDays(int adminId) {
        List<String> days = new ArrayList<>();
        if (adminId == 0 || adminId < 0) {
            System.out.println("Niepoprawne id użytkownika");
        } else {
            try (Connection connection = DbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT day_name.name\n" +
                         "FROM `recipe_plan`\n" +
                         "JOIN day_name on day_name.id=day_name_id \n" +
                         "WHERE plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?) \n" +
                         "group by day_name.name, day_name.order\n" +
                         "order by day_name.order")) {
                statement.setInt(1, adminId);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    days.add(rs.getString(1));
                }

            } catch (SQLException e) {
                System.out.println("Problem z bazą danych");

            }

        }
        return days;
    }

}
