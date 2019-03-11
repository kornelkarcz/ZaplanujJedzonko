package pl.coderslab.servlets;

import pl.coderslab.dao.PlanDao;
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

@WebServlet("/app/plan/list")
public class AllPlansList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        Integer adminId = 1;

        try (Connection connection = DbUtil.getConnection()) {

            List<Plan> planList = new PlanDao().findAllPlan(adminId);
            request.setAttribute("planList", planList);
            getServletContext().getRequestDispatcher("/app-schedules.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
