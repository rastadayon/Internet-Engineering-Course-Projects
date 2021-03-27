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
            String loggedInStudentId = bolbolestan.getLoggedInId();
            String action = request.getParameter("action");
            if ("search".equals(action)) {
                String searchString = request.getParameter("search");
                System.out.println("String that was searched : " + searchString);
                handleSearch(loggedInStudentId, searchString, request, response);
            } else if ("clear".equals(action)) {
                System.out.println("clearing search for student");
                handleClearSearch(loggedInStudentId, request, response);
            }
        } else {
            response.sendRedirect("http://localhost:8080/ca3_war_exploded/login");
        }
    }

    private void handleSearch(String studentId, String searchString,
                              HttpServletRequest request, HttpServletResponse response) {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        try {
            bolbolestan.searchForCourses(studentId, searchString);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("courses.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClearSearch(String studentId, HttpServletRequest request, HttpServletResponse response) {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        try {
            bolbolestan.clearSearch(studentId);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("courses.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
