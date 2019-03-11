package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/edit")
public class RecipeEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipeId = Integer.valueOf(request.getParameter("recipeId"));
        Recipe recipe = RecipeDao.read(recipeId);
        int adminId = recipe.getAdmins().getId();
        Admins admin = AdminDao.read(adminId);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int prepareTime = Integer.parseInt(request.getParameter("prepartime"));
        String ingredients = request.getParameter("ingredients");
        String preparation =request.getParameter("preparation");

        Recipe recipeUpdated = new Recipe(recipeId,name,ingredients,description,prepareTime,preparation,admin);
        RecipeDao.update(recipeUpdated);

        response.sendRedirect("/app/recipe/list/");





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String recipId = request.getParameter("recipeId");
        request.setAttribute("recipeId", recipId);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        getServletContext().getRequestDispatcher("/app/recipe/edit.jsp").forward(request, response);
    }
}
