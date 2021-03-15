package controllers;

import javax.servlet.annotation.WebServlet;

import Bolbolestan.Course.Course;
import Bolbolestan.Offering.Offering;
import Bolbolestan.Student.Grade;
import HTTPRequestHandler.HTTPRequestHandler;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import Bolbolestan.Bolbolestan;
import Bolbolestan.Student.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    final static String STUDENTS_URL = "http://138.197.181.131:5000/api/students";
    final static String GRADES_URL = "http://138.197.181.131:5000/api/grades";
    final static String COURSES_URL = "http://138.197.181.131:5000/api/courses";
    public void init() throws ServletException {

        try {
            System.out.println("importing students..");
            importStudentsFromWeb(STUDENTS_URL);
            System.out.println("importing offerings..");
            importCoursesFromWeb(COURSES_URL);
            System.out.println("importing grades..");
            importGradesFromWeb(GRADES_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("std_id");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.doesStudentExist(studentId)) {
            bolbolestan.makeLoggedIn(studentId);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void importStudentsFromWeb(String studentsURL) throws Exception{
        String StudentsJsonString = HTTPRequestHandler.getRequest(studentsURL);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Student> students = gson.fromJson(StudentsJsonString, new TypeToken<List<Student>>() {
        }.getType());
        for (Student student : students) {
            try {
                student.print();
                Bolbolestan.getInstance().addStudent(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void importCoursesFromWeb(final String coursesURL) throws Exception{
        String coursesJsonString = HTTPRequestHandler.getRequest(coursesURL);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Offering> offerings = gson.fromJson(coursesJsonString, new TypeToken<List<Offering>>() {
        }.getType());
        List<Course> courses = gson.fromJson(coursesJsonString, new TypeToken<List<Course>>() {
        }.getType());
        for (int i = 0; i < offerings.size(); i++) {
            try {
                Offering offering = offerings.get(i);
                offering.setCourse(courses.get(i));
                Bolbolestan.getInstance().addOffering(offering);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void importGradesFromWeb(final String gradesURL) throws Exception {
        ArrayList<String> studentIds = Bolbolestan.getInstance().getStudentIds();
        for (String studentId : studentIds) {
            String gradesJsonString = HTTPRequestHandler.getRequest(
                    gradesURL + "/" + studentId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Grade> grades = gson.fromJson(gradesJsonString, new TypeToken<List<Grade>>() {
            }.getType());
            for (Grade grade : grades) {
                try {
                    Bolbolestan.getInstance().addGradeToStudent(studentId, grade);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}