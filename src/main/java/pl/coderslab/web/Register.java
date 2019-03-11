package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Admins admins = new Admins();

        admins.setFirstName(request.getParameter("name"));
        admins.setLastName(request.getParameter("surname"));
        admins.setEmail(request.getParameter("email"));
        admins.setPassword(request.getParameter("password"));
        AdminDao.create(admins);

        response.sendRedirect("/login");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);

    }
}
