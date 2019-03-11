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
import java.util.List;

@WebServlet("/app/recipe/list")
public class AppRecipeList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
// tzreba wziąś z sesji tworzonej przy logowaniu użytkownika
        int adminsId=1; // trzeba to podmienic na sesje jak juz będzie

        try (Connection connection= DbUtil.getConnection()){

            List<Recipe> recipeList = new RecipeDao().findAll(adminsId);
            request.setAttribute("recipeList",recipeList);
            getServletContext().getRequestDispatcher("/appRecipeList.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
