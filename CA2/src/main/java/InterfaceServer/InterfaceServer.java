package InterfaceServer;
import Bolbolestan.Bolbolestan;
import Bolbolestan.Student;
import Bolbolestan.Course;
import Bolbolestan.Grade;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.common.base.Charsets;
import io.javalin.Javalin;
import HTTPRequestHandler.HTTPRequestHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class InterfaceServer {
    private Javalin app;
    private Bolbolestan bolbolestan = new Bolbolestan();

    public void start(final String STUDENTS_URL, final String COURSES_URL, final String GRADES_URL, int port) {
        try {
            System.out.println("Importing Students...");
            importStudentsFromWeb(STUDENTS_URL);
            System.out.println("Importing Courses...");
            importCoursesFromWeb(COURSES_URL);
            System.out.println("Importing Grades...");
            importGradesFromWeb(GRADES_URL);
            runServer(port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void runServer(final int port) throws Exception {
        app = Javalin.create().start(port);
        app.get("/courses", ctx -> {
            try {
                ctx.html(generateCoursesPage());
            }catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
        }});

    }

    private String generateCoursesPage() throws Exception {
        String coursesStart = readHTMLPage("courses_start.html");
        return coursesStart;
    }

    private String readHTMLPage(String fileName) throws Exception {
        File file = new File(Resources.getResource("templates/components/" + fileName).toURI());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    private void importStudentsFromWeb(final String studentsURL) throws Exception{
        String StudentsJsonString = HTTPRequestHandler.getRequest(studentsURL);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Student> students = gson.fromJson(StudentsJsonString, new TypeToken<List<Student>>() {
        }.getType());
        int counter = 1;
        for (Student student : students) {
            System.out.println(counter + "----------------");
            counter++;
            student.print();
            try {
                bolbolestan.addStudent(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void importCoursesFromWeb(final String coursesURL) throws Exception{
        String coursesJsonString = HTTPRequestHandler.getRequest(coursesURL);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Course> courses = gson.fromJson(coursesJsonString, new TypeToken<List<Course>>() {
        }.getType());
        int counter = 1;
        for (Course course : courses) {
            System.out.println(counter + "----------------");
            counter++;
            course.print();
            try {
                bolbolestan.addCourse(course);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void importGradesFromWeb(final String gradesURL) throws Exception {
        Map<String, Student> students = bolbolestan.getStudents();
        for (Map.Entry<String, Student> entry : students.entrySet()) {
            String gradesJsonString = HTTPRequestHandler.getRequest(
                    gradesURL + "/" + entry.getKey());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Grade> grades = gson.fromJson(gradesJsonString, new TypeToken<List<Grade>>() {
            }.getType());
            int counter = 1;
            System.out.println(String.format("Student : %s", entry.getKey()));
            for (Grade grade : grades) {
                System.out.println(counter + "----------------");
                counter++;
                grade.print();
                try {
                    bolbolestan.addGradeToStudent(students.get(entry.getKey()), grade);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
