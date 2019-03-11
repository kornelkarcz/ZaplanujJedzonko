package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Plan;
import pl.coderslab.model.TimeComparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/app/plan/list/")
public class PlanList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sess = request.getSession();
        Admins admin = (Admins)sess.getAttribute("currentUser");

        List<Plan> plans = PlanDao.findByAdminId(admin.getId());

        Collections.sort(plans, new TimeComparator());

        request.setAttribute("plans", plans);
        getServletContext().getRequestDispatcher("/app/plan/list.jsp").forward(request, response);
    }
}
