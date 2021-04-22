package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Course.CourseManager;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.OfferingManager;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.ReportCard;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.StudentManager;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.StudentInfo;


import java.util.*;

public class Bolbolestan {
    final static String STUDENTS_URL = "http://138.197.181.131:5000/api/students";
    final static String GRADES_URL = "http://138.197.181.131:5000/api/grades";
    final static String COURSES_URL = "http://138.197.181.131:5000/api/courses";
    private static Bolbolestan instance;
    private StudentManager studentManager = new StudentManager();
    private OfferingManager offeringManager = new OfferingManager();
    private CourseManager courseManager = new CourseManager();


    public ArrayList<String> getStudentIds() {
        return studentManager.getStudentIds();
    }

    public Student getStudentById(String studentId) throws Exception {
        return studentManager.getStudentById(studentId);
    }

    public String getLoggedInId() { return studentManager.getLoggedInId(); }

    public Boolean isAnybodyLoggedIn() {
        return studentManager.isAnybodyLoggedIn();
    }

    public void makeLoggedIn(String studentId) {
        studentManager.makeLoggedIn(studentId);
    }

    public void makeLoggedOut() {
        studentManager.makeLoggedOut();
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

//    public void addGradeToStudent(String studentId, Grade grade) throws Exception {
//        studentManager.addGradeToStudent(studentId, grade);
//    }

    public void addToWeeklySchedule(String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        studentManager.addToSelectedOfferings(studentId, offering);
    }

    public void removeFromWeeklySchedule(String studentId, String courseCode, String classCode) throws Exception {
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        studentManager.removeFromWeeklySchedule(studentId, offering);
    }

    public boolean finalizeSchedule(String studentId) throws Exception {
        return studentManager.finalizeSchedule(studentId);
    }

//    public int getUnitsPassed(String studentId) throws Exception {
//        Student student = studentManager.getStudentById(studentId);
//        int unitsPassed = 0;
//        ArrayList<Grade> studentGrades = student.getGrades();
//        if (studentGrades == null)
//            return 0;
//        for (Grade gradeItem : studentGrades) {
//            Course course = courseManager.getCourseByCode(gradeItem.getCode());
//            if (gradeItem.getGrade() >= 10)
//                unitsPassed += course.getUnits();
//        }
//        return unitsPassed;
//    }

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
        studentManager.addCourseToStudent(studentId, offering);
    }

    public boolean addCourseToWaitingList(String studentId, String courseCode, String classCode) throws Exception {
        if (offeringManager.offeringHasCapacity(courseCode, classCode))
            return false;
        Offering offering = offeringManager.getOfferingById(courseCode, classCode);
        return studentManager.addCourseToWaitingList(studentId, offering);
    }

    private Bolbolestan() {}

    public static Bolbolestan getInstance() {
        if (instance == null) {
            instance = new Bolbolestan();
        }
        return instance;
    }

    public void searchForCourses(String studentId, String searchCourse) throws Exception {
        Student student = studentManager.getStudentById(studentId);
        student.searchFor(searchCourse);
    }

    public Student getLoggedInStudent() throws Exception {
        return studentManager.getStudentById(getLoggedInId());
    }

    public List<Offering> getSearchedOfferings(String studentId) throws Exception {
        List<Offering> offerings;
        String searchString = studentManager.getStudentById(studentId).getSearchString();
        offerings = offeringManager.getSimilarOfferings(searchString);
        return offerings;
    }

    public void clearSearch(String studentId) throws Exception {
        Student student = studentManager.getStudentById(studentId);
        student.clearSearch();
    }

    public void resetSelectedOfferings(String studentId) throws Exception {
        Student student = studentManager.getStudentById(studentId);
        student.resetSelectedOfferings();
    }

    public void checkWaitingLists() throws Exception {
        studentManager.checkWaitingLists();
    }

    public int getUnitsById(String courseCode) {
        return offeringManager.getUnitsById(courseCode);
    }

    public String getCourseNameByCourseCode(String courseCode) {
        return offeringManager.getCourseName(courseCode);
    }

    public StudentInfo getStudentInfo() throws Exception {
        if (!isAnybodyLoggedIn())
            throw new BolbolestanStudentNotFoundError();
        return studentManager.getStudentInfo();
    }

    public ArrayList<ReportCard> getStudentReports() throws Exception {
        if (!isAnybodyLoggedIn())
            throw new BolbolestanStudentNotFoundError();
        return studentManager.getStudentReports();
    }

    public void setReportCards(String studentId, ArrayList<Grade> grades) throws Exception {
        for (Grade grade : grades) {
            grade.setUnits(getUnitsById(grade.getCode()));
            grade.setCourseName(getCourseNameByCourseCode(grade.getCode()));
        }
        studentManager.setReportCards(studentId, grades);
    }
}
