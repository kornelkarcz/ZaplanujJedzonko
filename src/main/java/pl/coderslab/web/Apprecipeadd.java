package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/App/recipe/add")
public class Apprecipeadd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int adminsId =1;

        String nameAddRecipe = request.getParameter("name");
        String descriptionAddRecipe = request.getParameter("description");
        String preparationTimeAddRecipe = request.getParameter("preparationTime");
        int preparationAddRecipeTimeInt = Integer.valueOf(preparationTimeAddRecipe);
        String preparationAddRecipe = request.getParameter("preparation");
        String ingredientsAddRecipe = request.getParameter("ingredients");
        Date createDate = new Date();
//Dodać admins ID jak sie poprawi metoda i pojawi klasa
      //  Recipe newRecipe = new RecipeDao().create(new Recipe(nameAddRecipe,ingredientsAddRecipe,descriptionAddRecipe,createDate,createDate,preparationAddRecipeTimeInt,preparationAddRecipe));

        try (Connection connection= DbUtil.getConnection()){

            List<Recipe> recipeList = new RecipeDao().findAll(adminsId);
            request.setAttribute("recipeList",recipeList);
        getServletContext().getRequestDispatcher("/appRecipeList.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/appRecipeAdd.jsp").forward(request,response);


    }
}
