package InterfaceServer;
import Bolbolestan.Bolbolestan;
import Bolbolestan.Offering.Offering;
import Bolbolestan.Student.Student;
import Bolbolestan.Student.Grade;
import Bolbolestan.Student.WeeklySchedule;
import Bolbolestan.exeptions.BolbolestanCourseNotFoundError;
import Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
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
import java.util.Arrays;

public class InterfaceServer {
    private Javalin app;
    private Bolbolestan bolbolestan = new Bolbolestan();
    private HTMLPageHandler HTMLHandler = new HTMLPageHandler();
    private final List<String> days = Arrays.asList("Saturday", "Sunday", "Monday",
            "Tuesday", "Wednesday");
    private final List<String> startTimes = Arrays.asList("7:30", "9:00", "10:30",
            "14:00", "16:00");

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

    public void stop() {
        //app.stop();
    }

    public Bolbolestan getEduSystem() {
        return bolbolestan;
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
                String courseCode = ctx.formParam("course_code");
                String classCode = ctx.formParam("class_code");
                bolbolestan.removeFromWeeklySchedule(studentId, courseCode, classCode);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ctx.redirect("/change_plan/" + ctx.pathParam("studentId"));
        });

        app.get("/plan/:studentId", ctx -> {
            String studentId = ctx.pathParam("studentId");
            try {
                ctx.html(generatePlanPage(studentId));
            } catch (BolbolestanStudentNotFoundError e) {
                ctx.html(readHTMLPage("404.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
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
        app.post("/course/:code/:classCode", ctx -> {
            String studentId = ctx.formParam("std_id");
                try {
                    String classCode = ctx.pathParam("classCode");
                    String courseCode = ctx.pathParam("code");
                    String response = generateAddCourseToStudent(studentId, courseCode, classCode);
                    bolbolestan.addCourseToStudent(studentId, courseCode, classCode);
                    ctx.html(response);
                } catch (BolbolestanCourseNotFoundError e) {
                    ctx.html(readHTMLPage("404.html"));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    ctx.status(502).result(":| " + e.getMessage());
                }
            });

        app.get("/submit/:studentId", ctx -> {
            String studentId = ctx.pathParam("studentId");
            try {
                ctx.html(generateSubmitPage(studentId));
            } catch (BolbolestanStudentNotFoundError e) {
                ctx.html(readHTMLPage("404.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });

        app.post("/submit/:studentId", ctx -> {
            String studentId = ctx.pathParam("studentId");
            try {
                bolbolestan.handleFinalize(studentId);
                ctx.redirect("/submit_ok");
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.redirect("/submit_failed");
                ctx.status(502).result(":| " + e.getMessage());
            }
        });

        app.get("/submit_ok", ctx -> {
            try {
                ctx.html(readHTMLPage("submit_ok.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });

        app.get("/submit_failed", ctx -> {
            try {
                ctx.html(readHTMLPage("submit_failed.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });
    }

    private String generateAddCourseToStudent(
            String studentId, String courseCode, String classCode) throws Exception {
        String response = "";
        ArrayList<Offering> conflictingClassTimes = bolbolestan.getClassTimeConflictingWithStudent(studentId, courseCode, classCode);
        ArrayList<Offering> conflictingExamTimes = bolbolestan.getExamTimeConflictingWithStudent(studentId, courseCode, classCode);
        ArrayList<String> prerequisitesNotPassed = bolbolestan.getPrerequisitesNotPassed(studentId, courseCode, classCode);
        boolean hasCapacity = bolbolestan.offeringHasCapacity(courseCode, classCode);
        if (conflictingClassTimes != null)
            for (Offering offering : conflictingClassTimes)
                response += String.format("Courses %s, %s have conflicting class times.\n",
                        offering.getCourseCode() + "-" + offering.getClassCode(), courseCode + "-" + classCode);
        if (conflictingExamTimes != null)
            for (Offering offering : conflictingExamTimes)
                response += String.format("Courses %s, %s have conflicting exam times.\n",
                        offering.getCourseCode() + "-" + offering.getClassCode(), courseCode + "-" + classCode);
        if (prerequisitesNotPassed != null) {
            response += "The following prerequisites have not been passed yet : [ ";
            for (String prerequisite : prerequisitesNotPassed)
                response += String.format("%s ", prerequisite);
            response += "]\n";
        }
        if (!hasCapacity)
            response += "Course does not have capacity";
        if (response.equals(""))
            response = "Course successfully added";
        return response;
    }

    private String generateProfile(String studentId) throws Exception {
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
            profileHTML += HTMLHandler.fillTemplate(profileItem, studentProfile);
        }
        return profileHTML + readHTMLPage("profile_end.html");
    }

    private String generateCoursesPage() throws Exception {
        String coursesHTML = readHTMLPage("courses_start.html");
        List<Offering> offerings = bolbolestan.getOfferings();
        String courseItemString = readHTMLPage("courses_item.html");

        for (Offering offering : offerings) {
            HashMap<String, String> courseContent = new HashMap<>();
            courseContent.put("code", offering.getCourseCode());
            courseContent.put("classCode", offering.getClassCode());
            courseContent.put("name", offering.getName());
            courseContent.put("units", Integer.toString(offering.getUnits()));
            courseContent.put("capacity", Integer.toString(offering.getCapacity()));
            courseContent.put("type", offering.getType());
            courseContent.put("classDays", offering.getClassDayString("|"));
            courseContent.put("classTime", offering.getClassTime().getTime());
            courseContent.put("examTimeStart", offering.getExamTime().getStart());
            courseContent.put("examTimeEnd", offering.getExamTime().getEnd());
            courseContent.put("prerequisites", offering.getPrerequisitesString());
            coursesHTML += HTMLHandler.fillTemplate(courseItemString, courseContent);
        }
        coursesHTML += readHTMLPage("courses_end.html");
        return coursesHTML;
    }

    private String generateCoursePage(String courseCode, String classCode) throws Exception {
        Offering offering = bolbolestan.getOffering(courseCode, classCode);
        String courseHTML = readHTMLPage("course.html");
        HashMap<String, String> courseContent = new HashMap<>();
        courseContent.put("code", offering.getCourseCode());
        courseContent.put("classCode", offering.getClassCode());
        courseContent.put("units", Integer.toString(offering.getUnits()));
        courseContent.put("classDays", offering.getClassDayString(", "));
        courseContent.put("classTime", offering.getClassTime().getTime());
        courseHTML = HTMLHandler.fillTemplate(courseHTML, courseContent);
        return courseHTML;
    }

    private String generateChangePlanPage(String studentId) throws Exception {
        if (!bolbolestan.doesStudentExist(studentId))
            throw new BolbolestanStudentNotFoundError();

        String changePlanHTML = readHTMLPage("change_plan_start.html");
        WeeklySchedule weeklySchedule = bolbolestan.handleGetWeeklySchedule(studentId);
        if (weeklySchedule == null) {
            changePlanHTML += readHTMLPage("change_plan_end.html");
            return changePlanHTML;
        }
        List<Offering> offerings = weeklySchedule.getOfferings();
        String planItemString = readHTMLPage("change_plan_item.html");
        for (Offering offering : offerings) {
            HashMap<String, String> planContent = new HashMap<>();
            planContent.put("code", offering.getCourseCode());
            planContent.put("classCode", offering.getClassCode());
            planContent.put("name", offering.getName());
            planContent.put("units", Integer.toString(offering.getUnits()));
            changePlanHTML += HTMLHandler.fillTemplate(planItemString, planContent);
        }
        changePlanHTML += readHTMLPage("change_plan_end.html");
        return changePlanHTML;
    }

    private String generatePlanPage(String studentId) throws Exception {
        String planHTML = readHTMLPage("plan_start.html");
        WeeklySchedule weeklySchedule = bolbolestan.handleGetWeeklySchedule(studentId);
        String planItemString = readHTMLPage("plan_item.html");
        for (String day: days) {
            HashMap<String, String> planContent = new HashMap<>();
            for (String startTime: startTimes) {
                String courseName = "";
                if (weeklySchedule != null)
                    courseName = weeklySchedule.getCourseNameByClassTime(day, startTime);
                planContent.put(startTime, courseName);
                planContent.put("day", day);
            }
            planHTML += HTMLHandler.fillTemplate(planItemString, planContent);
        }
        planHTML += readHTMLPage("plan_end.html");
        return planHTML;
    }

    private String generateSubmitPage(String studentId) throws Exception {
        String submitHTML = readHTMLPage("submit.html");
        HashMap<String, String> submitContent = new HashMap<>();
        submitContent.put("studentId", studentId);
        submitContent.put("totalUnits", Integer.toString(bolbolestan.getTotalUnits(studentId)));
        submitHTML = HTMLHandler.fillTemplate(submitHTML, submitContent);
        return submitHTML;
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
        List<Offering> offerings = gson.fromJson(coursesJsonString, new TypeToken<List<Offering>>() {
        }.getType());
        int counter = 1;
        for (Offering offering : offerings) {
            System.out.println(counter + "----------------");
            counter++;
            offering.print();
            try {
                bolbolestan.addOffering(offering);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void importGradesFromWeb(final String gradesURL) throws Exception {
        ArrayList<String> studentIds = bolbolestan.getStudentIds();
        for (String studentId : studentIds) {
            String gradesJsonString = HTTPRequestHandler.getRequest(
                    gradesURL + "/" + studentId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Grade> grades = gson.fromJson(gradesJsonString, new TypeToken<List<Grade>>() {
            }.getType());
            int counter = 1;
            System.out.println(String.format("Student : %s", studentId));
            for (Grade grade : grades) {
                System.out.println(counter + "----------------");
                counter++;
                grade.print();
                try {
                    bolbolestan.addGradeToStudent(studentId, grade);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void assignCoursesForTests(String studentId) throws Exception {
        bolbolestan.addToWeeklySchedule(studentId, "8101028", "01");
        bolbolestan.addToWeeklySchedule(studentId, "8101020", "01");
        bolbolestan.addToWeeklySchedule(studentId, "8101031", "01");
        bolbolestan.addToWeeklySchedule(studentId, "8101021", "01");
    }

}
