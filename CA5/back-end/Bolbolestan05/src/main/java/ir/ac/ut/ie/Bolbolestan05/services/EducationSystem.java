package ir.ac.ut.ie.Bolbolestan05.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan05.utils.HTTPRequestHandler.HTTPRequestHandler;

import java.util.ArrayList;
import java.util.List;

public class EducationSystem {
    final static String STUDENTS_URL = "http://138.197.181.131:5100/api/students";
    final static String GRADES_URL = "http://138.197.181.131:5100/api/grades";
    final static String COURSES_URL = "http://138.197.181.131:5100/api/courses";
    private static EducationSystem instance;
    private EducationSystem() {}
    public static EducationSystem getInstance() {
        if(instance == null)
            instance = new EducationSystem();
        return instance;
    }

    public void importDataFromWeb() throws Exception{
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
                offerings.get(i).print();
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
                    grade.setUnits(Bolbolestan.getInstance().getUnitsById(grade.getCode()));
                    Bolbolestan.getInstance().addGradeToStudent(studentId, grade);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
