package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/plan/delete")
public class RecipeDelFromPlan extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipePlanId = Integer.parseInt(request.getParameter("recipePlanId"));


        PlanDao.deleteRecipeFromPlan(recipePlanId);
        //tutaj problem z odesłaniem do serwletu - dlatego podana strona jsp
        response.sendRedirect("/app/plan/list");

    }
}
