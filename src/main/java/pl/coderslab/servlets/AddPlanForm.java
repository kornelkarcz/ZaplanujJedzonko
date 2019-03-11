package pl.coderslab.servlets;


import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/add")
public class AddPlanForm extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String planName = request.getParameter("planName");
        String planDescription = request.getParameter("planDescription");

       /* if (StringUtils.isBlank(planName) || StringUtils.isBlank(planDescription)) {
            response.getWriter().append("Podaj pełne dane");
            return;
        }*/

        //Co z id i created?

        Plan plan = new Plan(planName, planDescription);
        PlanDao planDao = new PlanDao();
        planDao.createPlan(plan);

        getServletContext().getRequestDispatcher("/app/plan/list").forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        getServletContext().getRequestDispatcher("/AddPlanForm.jsp").forward(request, response);
    }
}

