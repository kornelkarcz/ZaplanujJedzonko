package pl.coderslab.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/RecipeDelFromPlanConfirm")
public class RecipeDelFromPlanConfirm extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String recipePlanId = request.getParameter("recipePlanId");

        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("<!DOCTYPE html>");
        response.getWriter().append("<html lang=\"pl\">");
        response.getWriter().append("<meta charset='utf-8'>");
        response.getWriter().append("<p>Czy na pewno chcesz usunąć przepis?<p>");
        response.getWriter().append("<br>");
        response.getWriter().append("<a href='/app/RecipeDelFromList?confirm=ok&recipePlanId="+recipePlanId+"'>OK</a> &nbsp; &nbsp;");
        response.getWriter().append("<a href='/app/RecipeDelFromList?confirm=no&recipePlanId="+recipePlanId+"'>Anuluj</a> &nbsp; &nbsp;");


    }
}
