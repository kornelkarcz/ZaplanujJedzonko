package pl.coderslab.web;


import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/app/plan/details")
public class PlanDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //pobranie id planu planu
        String planId = request.getParameter("planId");
        int id = Integer.parseInt(planId);

        //pobranie danych planu
        Plan plan = PlanDao.readById(id);
        request.setAttribute("plan", plan);

        //pobranie przepisów dla planu
        List<RecipePlan> foundRecipes = PlanDao.findRecipesByPlanId(id);
        request.setAttribute("foundRecipes", foundRecipes);

        //pobranie nazw dni tygodnia
        List<DayName> days = DayNameDao.findAll();
        request.setAttribute("days", days);

        getServletContext().getRequestDispatcher("/app/plan/details.jsp").forward(request, response);
    }
}
