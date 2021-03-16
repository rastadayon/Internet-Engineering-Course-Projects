package controllers;

import Bolbolestan.Bolbolestan;
import Bolbolestan.Student.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SearchCourses", urlPatterns = "/searchCourses")
public class SearchCourses extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            String searchString = request.getParameter("search");
            System.out.println("String that was searched : " + searchString);
            try {
                Student loggedIn = bolbolestan.getLoggedInStudent();
                bolbolestan.searchForCourses(loggedIn, searchString);
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
