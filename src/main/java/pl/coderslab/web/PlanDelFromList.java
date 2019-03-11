package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/PlanDelFromList")
public class PlanDelFromList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int planId = Integer.parseInt(request.getParameter("planId"));

        String confirm = request.getParameter("confirm");

        if ("ok".equals(confirm)) {
            PlanDao.delete(planId);
            response.sendRedirect("/app/plan/list/");
        }
        else if ("no".equals(confirm)) {
            response.sendRedirect("/app/plan/list/");
        }
        else {
            response.sendRedirect("/app/PlanDelFromListConfirm?planId="+planId);
        }
    }
}
