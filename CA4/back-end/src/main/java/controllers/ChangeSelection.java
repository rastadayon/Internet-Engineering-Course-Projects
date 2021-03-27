package controllers;

import Bolbolestan.Bolbolestan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeSelection", urlPatterns = "/changeSelection")
public class ChangeSelection extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            String loggedInStudentId = bolbolestan.getLoggedInId();
            String action = request.getParameter("action");
            String courseCode = request.getParameter("course_code");
            String classCode = request.getParameter("class_code");
            try {
                if("add".equals(action))
                    bolbolestan.addCourseToStudent(loggedInStudentId, courseCode, classCode);
                else if("remove".equals(action))
                    bolbolestan.removeFromWeeklySchedule(loggedInStudentId, courseCode, classCode);
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