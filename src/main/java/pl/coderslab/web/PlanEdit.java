package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/edit")
public class PlanEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String planName = request.getParameter("planName");
        String planDescription = request.getParameter("planDescription");
        int planId = Integer.parseInt(request.getParameter("planId"));

        Plan editedPlan = PlanDao.readById(planId);
        editedPlan.setName(planName);
        editedPlan.setDescription(planDescription);

        PlanDao.update(editedPlan);

        response.sendRedirect("/app/plan/list/");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        int planId = Integer.parseInt(request.getParameter("planId"));
        Plan plan = PlanDao.readById(planId);
        request.setAttribute("planFromDB",plan);

        getServletContext().getRequestDispatcher("/app/plan/edit.jsp").forward(request, response);
    }
}
