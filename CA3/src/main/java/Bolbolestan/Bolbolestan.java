package Bolbolestan;

import Bolbolestan.Course.Course;
import Bolbolestan.Course.CourseManager;
import Bolbolestan.Offering.Offering;
import Bolbolestan.Offering.OfferingManager;
import Bolbolestan.Student.Grade;
import Bolbolestan.Student.Student;
import Bolbolestan.Student.StudentManager;
import Bolbolestan.Student.WeeklySchedule;
import Bolbolestan.exeptions.*;

import java.util.*;

public class Bolbolestan {
    private static Bolbolestan instance;
    private String loggedInId = "";
    private StudentManager studentManager = new StudentManager();
    private OfferingManager offeringManager = new OfferingManager();
    private CourseManager courseManager = new CourseManager();


    public ArrayList<String> getStudentIds() {
        return studentManager.getStudentIds();
    }

    public Student getStudentById(String studentId) throws Exception {
        return studentManager.getStudentById(studentId);
    }

    public String getLoggedInId() {return loggedInId;}

    public Boolean isAnybodyLoggedIn() {
        if (loggedInId.equals(""))
            return false;
        return true;
    }

    public void makeLoggedIn(String studentId) {
        this.loggedInId = studentId;
    }

    public void makeLoggedOut() {
        this.loggedInId = "";
    }

    public boolean doesStudentExist(String studentId) {
        return studentManager.doesStudentExist(studentId);
    }

    public Offering getOffering(String courseCode, String classCode) throws Exception {
        return offeringManager.getOfferingById(courseCode, classCode);
    }

    public List<Offering> getOfferings () {
        return offeringManager.getOfferings();
    }

    public void addOffering(Offering offering) throws Exception {
        offeringManager.addOffering(offering);
        courseManager.addCourse(offering);
    }

    public void addStudent(Student student) throws Exception {
        studentManager.addStudent(student);
    }

    public void addGradeToStudent(String studentId, Grade grade) throws Exception {
        studentManager.addGradeToStudent(studentId, grade);
    }

    public void addToWeeklySchedule(String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        studentManager.addToWeeklySchedule(studentId, offering);
    }

    public void removeFromWeeklySchedule(String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        studentManager.removeFromWeeklySchedule(studentId, offering);
    }

    public WeeklySchedule handleGetWeeklySchedule(String studentId) throws Exception {
        return studentManager.getWeeklySchedule(studentId);
    }

    public void handleFinalize(String studentId) throws Exception {
        studentManager.finalizeSchedule(studentId);
    }

    public int getUnitsPassed(String studentId) throws Exception {
        Student student = studentManager.getStudentById(studentId);
        int unitsPassed = 0;
        ArrayList<Grade> studentGrades = student.getGrades();
        for (Grade gradeItem : studentGrades) {
            Course course = courseManager.getCourseByCode(gradeItem.getCode());
            if (gradeItem.getGrade() >= 10)
                unitsPassed += course.getUnits();
        }
        return unitsPassed;
    }

    public int getTotalUnits(String studentId) throws Exception{
        return studentManager.getTotalUnits(studentId);
    }

    public ArrayList<Offering> getClassTimeConflictingWithStudent(
            String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        return studentManager.getClassTimeConflicts(studentId, offering);
    }

    public ArrayList<Offering> getExamTimeConflictingWithStudent(
            String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        return studentManager.getExamTimeConflicts(studentId, offering);
    }

    public boolean offeringHasCapacity(String courseCode, String classCode) throws Exception{
        return offeringManager.offeringHasCapacity(courseCode, classCode);
    }

    public ArrayList<String> getPrerequisitesNotPassed(
            String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        return studentManager.getPrerequisitesNotPassed(studentId, offering);
    }

    public void addCourseToStudent(String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        if (offering.hasCapacity())
            studentManager.addCourseToStudent(studentId, offering);
    }

    public void removeAllCoursesFromStudent(String studentId) throws Exception {
        studentManager.removeAllOfferingsFromStudent(studentId);
    }

    private Bolbolestan() {}

    public static Bolbolestan getInstance() {
        if (instance == null) {
            instance = new Bolbolestan();
        }
        return instance;
    }
}
