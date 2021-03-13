package controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import Bolbolestan.Bolbolestan;
import Bolbolestan.Student.Grade;
import Bolbolestan.Student.Student;
import HTTPRequestHandler.HTTPRequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    final String STUDENTS_URL = "http://138.197.181.131:5000/api/students";
    final String GRADES_URL = "http://138.197.181.131:5000/api/grades";

    public void init() throws ServletException {
        try {
            importStudentsFromWeb(STUDENTS_URL);
            importGradesFromWeb(GRADES_URL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("std_id");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.doesStudentExist(studentId)) {
            bolbolestan.makeLoggedIn(studentId);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void importStudentsFromWeb(final String studentsURL) throws Exception {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        String StudentsJsonString = HTTPRequestHandler.getRequest(studentsURL);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Student> students = gson.fromJson(StudentsJsonString, new TypeToken<List<Student>>() {
        }.getType());
        for (Student student : students) {
            try {
                bolbolestan.addStudent(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void importGradesFromWeb(final String gradesURL) throws Exception {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        ArrayList<String> studentIds = bolbolestan.getStudentIds();
        for (String studentId : studentIds) {
            String gradesJsonString = HTTPRequestHandler.getRequest(
                    gradesURL + "/" + studentId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Grade> grades = gson.fromJson(gradesJsonString, new TypeToken<List<Grade>>() {
            }.getType());
            for (Grade grade : grades) {
                try {
                    bolbolestan.addGradeToStudent(studentId, grade);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}