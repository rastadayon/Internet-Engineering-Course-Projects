
import InterfaceServer.InterfaceServer;
import Bolbolestan.Bolbolestan;
import Bolbolestan.Student;
import Bolbolestan.Course;
import Bolbolestan.ClassTime;
import Bolbolestan.ExamTime;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class SubmitUnitsTest {
    private static InterfaceServer interfaceServer;
    private static Bolbolestan bolbolestan;

    public static void fillBolbolestan() throws Exception{
        try {
            bolbolestan.addStudent(new Student("810196675", "Ghazal",
                    "Kalhor", "1399/12/12"));
            bolbolestan.addCourse(new Course("8120144", "01",
                    "AP", 3, "Asli", "Sara", 60,
                    new ArrayList<>(), new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Saturday"))), new ExamTime(
                    "2021-07-01T08:00:00", "2021-07-01T08:00:00")));
            bolbolestan.addCourse(new Course("8120133", "01",
                    "Project", 20, "Asli", "Sara", 60,
                    new ArrayList<>(), new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Monday"))), new ExamTime(
                    "2021-08-01T08:00:00", "2021-08-01T08:00:00")));
            bolbolestan.addCourse(new Course("8120122", "01",
                    "AGT", 10, "Asli", "Sara", 60,
                    new ArrayList<>(), new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Tuesday"))), new ExamTime(
                    "2021-09-01T08:00:00", "2021-09-01T08:00:00")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeClass
    public static void setup() {
        final String STUDENTS_URL = "http://138.197.181.131:5000/api/students";
        final String GRADES_URL = "http://138.197.181.131:5000/api/grades";
        final String COURSES_URL = "http://138.197.181.131:5000/api/courses";
        final int PORT = 8080;
        interfaceServer = new InterfaceServer();
        bolbolestan = interfaceServer.getEduSystem();
        try {
            fillBolbolestan();
            interfaceServer.start(STUDENTS_URL, COURSES_URL, GRADES_URL, PORT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void teardown() {
        bolbolestan.removeAllCoursesFromStudent("810196675");
    }

    @Test
    public void testMinimumUnitsSubmission() {
        try {
            bolbolestan.addCourseToStudent("810196675", "8120144", "01");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        HttpResponse<String> response = Unirest.post("http://localhost:8080/submit/810196675")
                .asString();
        Assert.assertEquals("Your request failed", response.getBody());
        Assert.assertEquals(502, response.getStatus());
    }

    @Test
    public void testMaximumUnitsSubmission() {
        try {
            bolbolestan.addCourseToStudent("810196675", "8120144", "01");
            bolbolestan.addCourseToStudent("810196675", "8120133", "01");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        HttpResponse<String> response = Unirest.post("http://localhost:8080/submit/810196675")
                .asString();
        Assert.assertEquals("Your request failed", response.getBody());
        Assert.assertEquals(502, response.getStatus());
    }

    @Test
    public void testSuccessfulSubmission() {
        try {
            bolbolestan.addCourseToStudent("810196675", "8120144", "01");
            bolbolestan.addCourseToStudent("810196675", "8120122", "01");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        HttpResponse<String> response = Unirest.post("http://localhost:8080/submit/810196675")
                .asString();
        Assert.assertEquals("Your request submitted successfully", response.getBody());
        Assert.assertEquals(502, response.getStatus());
    }
}
