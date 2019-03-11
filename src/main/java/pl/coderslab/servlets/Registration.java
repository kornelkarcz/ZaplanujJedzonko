package pl.coderslab.servlets;

import org.apache.commons.lang3.StringUtils;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register")
public class Registration extends HttpServlet {

    private final static String string = "[_a-zA-Z0-9]+(\\.[_a-zA-Z09]+)*@[_a-zA-Z0-9]+(\\.[_a-zA-Z09]+)*\\.([a-zA-Z]{2,}){1}";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        if (StringUtils.isBlank(name) || StringUtils.isBlank(surname) || StringUtils.isBlank(email) ||
                StringUtils.isBlank(password) || StringUtils.isBlank(password1)) {
            response.getWriter().append("Podałeś niepełne dane");
            return;
        }

        Pattern pattern = Pattern.compile(string);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.find()) {
            System.out.println("Niepoprawny adres e-mail.");
            return;
        }


        if (!StringUtils.equals(password, password1)) {
            response.getWriter().append("Niepoprawne hasło");
            return;
        }

        Admins admin = new Admins(name, surname, email, password);
        AdminDao addAdmin = new AdminDao();
        addAdmin.create(admin);

        //pozniej to poprawic
        getServletContext().getRequestDispatcher("/login").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        getServletContext().getRequestDispatcher("/registrationForm.jsp").forward(request, response);
    }
}
