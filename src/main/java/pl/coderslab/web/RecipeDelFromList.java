package pl.coderslab.web;


import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/RecipeDelFromList")
public class RecipeDelFromList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipeId = Integer.parseInt(request.getParameter("recipeId"));

        String confirm = request.getParameter("confirm");

        if ("ok".equals(confirm)) {
            RecipeDao.delete(recipeId);
            response.sendRedirect("/app/recipe/list/");
        }
        else if ("no".equals(confirm)) {
            response.sendRedirect("/app/recipe/list/");
        }
        else {
            response.sendRedirect("/app/RecipeDelFromListConfirm?recipeId="+recipeId);
        }
    }


}
