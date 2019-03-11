package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/App/recipe/details")
public class Apprecipedetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/appRecipeDetails.jsp").forward(request,response);
            String recipeListId = request.getParameter("recipeListId");
            Integer recipeId = Integer.valueOf(recipeListId);
            Integer adminId = 1; // tzreba wziąś z sesji tworzonej przy logowaniu użytkownika

        Recipe recipeDetails= new RecipeDao().read(recipeId,adminId);
        request.setAttribute("recipeDetails",recipeDetails);
        getServletContext().getRequestDispatcher("/appRecipeDetails").forward(request,response);
    }
}
