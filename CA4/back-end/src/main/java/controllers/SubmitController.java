package controllers;

import Bolbolestan.Bolbolestan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SubmitController", urlPatterns = "/submit")

public class SubmitController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            String loggedInStudentId = bolbolestan.getLoggedInId();
            String action = request.getParameter("action");
            try {
                if ("submit".equals(action)) {
                    if(bolbolestan.finalizeSchedule(loggedInStudentId))
                        response.sendRedirect("http://localhost:8080/ca3_war_exploded/plan");
                    else
                        response.sendRedirect("http://localhost:8080/ca3_war_exploded/submitFailed");
                }
                else if ("reset".equals(action)) {
                    bolbolestan.resetSelectedOfferings(loggedInStudentId);
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("courses.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("http://localhost:8080/ca3_war_exploded/login");
        }
    }
}
