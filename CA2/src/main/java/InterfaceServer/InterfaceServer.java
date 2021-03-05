package InterfaceServer;
import Bolbolestan.Bolbolestan;
import Bolbolestan.Student;
import Bolbolestan.Course;
import Bolbolestan.Grade;
import Bolbolestan.exeptions.BolbolestanCourseNotFoundError;
import Bolbolestan.exeptions.BolbolestanRulesViolationError;
import Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.javalin.Javalin;
import HTTPRequestHandler.HTTPRequestHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterfaceServer {
    private Javalin app;
    private Bolbolestan bolbolestan = new Bolbolestan();
    private HTMLPageHandler HTMLHandler = new HTMLPageHandler();

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
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });
        app.get("/course/:code/:classCode", ctx -> {
            try {
                ctx.html(generateCoursePage(ctx.pathParam("code"), ctx.pathParam("classCode")));
            } catch (BolbolestanCourseNotFoundError e) {
                ctx.html(readHTMLPage("404.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });

        app.get("/change_plan/:studentId", ctx -> {
            String studentId = ctx.pathParam("studentId");
            try {
                ctx.html(generateChangePlanPage(studentId));
            } catch (BolbolestanStudentNotFoundError e) {
                ctx.html(readHTMLPage("404.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });

        app.post("/change_plan/:studentId", ctx -> {
            try {
                String studentId = ctx.pathParam("studentId");
                String code = ctx.formParam("course_code") + '-'
                        + ctx.formParam("class_code");
                bolbolestan.removeFromWeeklySchedule(studentId, code);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ctx.redirect("/change_plan/" + ctx.pathParam("studentId"));
        });
        app.get("/profile/:studentId", ctx -> {
            try {
                ctx.html(generateProfile(ctx.pathParam("studentId")));
            } catch (BolbolestanCourseNotFoundError e) {
                ctx.html(readHTMLPage("404.html"));
            } catch (BolbolestanStudentNotFoundError e) {
                ctx.html(readHTMLPage("404.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });
//        app.post("/course/:code/:classCode", ctx -> {
//            try {
////                ctx.html(generateCoursePage(ctx.pathParam("code"), ctx.pathParam("classCode")));
//                ctx.
//            } catch (BolbolestanCourseNotFoundError e) {
//                ctx.html(readHTMLPage("404.html"));
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//                ctx.status(502).result(":| " + e.getMessage());
//            }
//        });
    }

    private String generateProfile(String studentId) throws Exception {
        if(!bolbolestan.doesStudentExist(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = bolbolestan.getStudentById(studentId);
        String profileHTML = readHTMLPage("profile_start.html");

        HashMap<String, String> studentProfile = new HashMap<>();
        studentProfile.put("id", student.getId());
        studentProfile.put("name", student.getName());
        studentProfile.put("secondName", student.getSecondName());
        studentProfile.put("birthDate", student.getBirthDate());
        studentProfile.put("GPA", Float.toString(student.getGPA()));
        studentProfile.put("totalUnits", Integer.toString(bolbolestan.getUnitsPassed(studentId)));

        profileHTML = HTMLHandler.fillTemplate(profileHTML, studentProfile);
        String profileItem = readHTMLPage("profile_item.html");
        for (Grade grade : student.getGrades()) {
            studentProfile = new HashMap<>();
            studentProfile.put("code", grade.getCode());
            studentProfile.put("grade", Integer.toString(grade.getGrade()));
            profileItem += HTMLHandler.fillTemplate(profileItem, studentProfile);
        }
        return profileHTML + readHTMLPage("profile_end.html");
    }

//    private String generateAddCourseToSchedulePage(
//            String courseCode, String classCode, String studentId) throws Exception {
//        if (!bolbolestan.doesCourseExist(courseCode, classCode))
//            throw new BolbolestanCourseNotFoundError();
//        if(!bolbolestan.doesStudentExist(studentId))
//            throw new BolbolestanStudentNotFoundError();
//        Course course = bolbolestan.getCourseByIdentifier(courseCode, classCode);
//        Student student = bolbolestan.getStudentById(studentId);
//        ArrayList<Course>
//    }

    private String generateCoursesPage() throws Exception {
        String coursesHTML = readHTMLPage("courses_start.html");
        Map<String, Course> coursesMap = bolbolestan.getCourses();
        ArrayList<Course> courses = new ArrayList<>(coursesMap.values());
        String courseItemString = readHTMLPage("courses_item.html");

        for (Course course : courses) {
            HashMap<String, String> courseContent = new HashMap<>();
            courseContent.put("code", course.getCode());
            courseContent.put("classCode", course.getClassCode());
            courseContent.put("name", course.getName());
            courseContent.put("units", Integer.toString(course.getUnits()));
            courseContent.put("capacity", Integer.toString(course.getCapacity() - course.getSeatsTaken()));
            courseContent.put("type", course.getType());
            courseContent.put("classDays", course.getClassDayString("|"));
            courseContent.put("classTime", course.getClassTime().getTime());
            courseContent.put("examTimeStart", course.getExamTime().getStart());
            courseContent.put("examTimeEnd", course.getExamTime().getEnd());
            courseContent.put("prerequisites", course.getPrerequisitesString());
            coursesHTML += HTMLHandler.fillTemplate(courseItemString, courseContent);
        }
        coursesHTML += readHTMLPage("courses_end.html");
        return coursesHTML;
    }

    private String generateCoursePage(String courseCode, String classCode) throws Exception {
        if (!bolbolestan.doesCourseExist(courseCode, classCode))
            throw new BolbolestanCourseNotFoundError();
        
        Course course = bolbolestan.getCourseByIdentifier(courseCode, classCode);
        String courseHTML = readHTMLPage("course.html");
        HashMap<String, String> courseContent = new HashMap<>();
        courseContent.put("code", course.getCode());
        courseContent.put("classCode", course.getClassCode());
        courseContent.put("units", Integer.toString(course.getUnits()));
        courseContent.put("classDays", course.getClassDayString(", "));
        courseContent.put("classTime", course.getClassTime().getTime());
        courseHTML = HTMLHandler.fillTemplate(courseHTML, courseContent);
        return courseHTML;
    }

    private String generateChangePlanPage(String studentId) throws Exception {
        if (!bolbolestan.doesStudentExist(studentId))
            throw new BolbolestanStudentNotFoundError();

        String changePlanHTML = readHTMLPage("change_plan_start.html");
        List<Course> courses = bolbolestan.handleGetWeeklySchedule(studentId).getOfferings();
        String planItemString = readHTMLPage("change_plan_item.html");
        for (Course course: courses) {
            HashMap<String, String> planContent = new HashMap<>();
            planContent.put("code", course.getCode());
            planContent.put("classCode", course.getClassCode());
            planContent.put("name", course.getName());
            planContent.put("units", Integer.toString(course.getUnits()));
            changePlanHTML += HTMLHandler.fillTemplate(planItemString, planContent);
        }
        changePlanHTML += readHTMLPage("change_plan_end.html");
        return changePlanHTML;
    }

    private String readHTMLPage(String fileName) throws Exception {
        File file = new File(Resources.getResource("templates/" + fileName).toURI());
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

    //private void assignCoursesForTests(String studentId) throws Exception {
        //bolbolestan.addToWeeklySchedule(studentId, "8101028-01");
        //bolbolestan.addToWeeklySchedule(studentId, "8101020-01");
   // }

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
