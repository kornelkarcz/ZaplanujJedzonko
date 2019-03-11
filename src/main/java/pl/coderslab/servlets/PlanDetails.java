package pl.coderslab.servlets;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.DayMealRecipe;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/app/plan/details")
public class PlanDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        //pozniej bedziemy to przekazywac sesja
        Integer planId = 1;

        try (Connection connection = DbUtil.getConnection()) {

            //stad wezmiemy name i description planu
            Plan plan = new PlanDao().readPlan(planId);
            request.setAttribute("plan", plan);

            //tu dac kod ktory bedzie poebieral z plan_recipe dane na temat poszczegolnych przepisow?
            List<DayMealRecipe> list = new PlanDao().getPlanDetails(planId);
            request.setAttribute("list", list);
            List<DayMealRecipe> list1 = list;
            request.setAttribute("list1", list1);

            getServletContext().getRequestDispatcher("/app-detail-schedule.jsp").forward(request, response);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
