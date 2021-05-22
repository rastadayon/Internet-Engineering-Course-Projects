package ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Course.CourseManager;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.OfferingManager;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.*;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.ClassTimeData;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.StudentInfo;
import ir.ac.ut.ie.Bolbolestan07.repository.Prerequisite.PrerequisiteMapper;
import ir.ac.ut.ie.Bolbolestan07.repository.Student.StudentMapper;


import java.util.*;

public class Bolbolestan {
    final static String STUDENTS_URL = "http://138.197.181.131:5000/api/students";
    final static String GRADES_URL = "http://138.197.181.131:5000/api/grades";
    final static String COURSES_URL = "http://138.197.181.131:5000/api/courses";
    private static Bolbolestan instance;
    private StudentManager studentManager = new StudentManager();
    private OfferingManager offeringManager = new OfferingManager();
    private CourseManager courseManager = new CourseManager();

    public String getLoggedInId() { return studentManager.getLoggedInId(); }

    public Boolean isAnybodyLoggedIn() {
        return studentManager.isAnybodyLoggedIn();
    }

    public Offering getOffering(String courseCode, String classCode) throws Exception {
        if(!isAnybodyLoggedIn())
            throw new BolbolestanStudentNotFoundError();
        return offeringManager.getOfferingById(courseCode, classCode);
    }

    public List<Offering> getOfferings () {
        return offeringManager.getOfferings();
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

    public void searchForCourses(String email, String searchCourse) throws Exception {
        Student student = new StudentMapper().getStudentByEmail(email);
        if (student == null)
            throw new BolbolestanStudentNotFoundError();
        studentManager.searchForCourses(student, searchCourse);
    }

    public WeeklySchedule getLoggedInStudentSchedule(String email) throws Exception {
        String studentId = getStudentByEmail(email).getId();
        return studentManager.getStudentScheduleById(getLoggedInId());
    }

    public Student getLoggedInStudent(String email) throws Exception {
        return studentManager.getStudentById(getLoggedInId());
    }

    public Student getStudentByEmail(String email) throws Exception {
        return studentManager.getStudentByEmail(email);
    }

    public void resetSelectedOfferings(String email) throws Exception {
        studentManager.resetSelectionsByEmail(email);
        //.resetSelectedOfferings();
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

    public StudentInfo getStudentInfo(String email) throws Exception {
        Student student = new StudentMapper().getStudentByEmail(email);
        return studentManager.getStudentInfo(student);
    }

    public ArrayList<ReportCard> getStudentReports(String email) throws Exception {
        Student student = new StudentMapper().getStudentByEmail(email);
        return studentManager.getStudentReports(student);
    }

    public String getStudentSearchedKeyword(String email) throws Exception {
        Student student = new StudentMapper().getStudentByEmail(email);
        if(student == null)
            throw new BolbolestanStudentNotFoundError();
        return student.getSearchString();
    }

    public String getSubmissionErrors() throws Exception {
        String errors = "";
        for (String error: studentManager.getSubmissionErrors()) {
            errors += error;
            errors += "\n";
        }
        return errors;
    }

    public String getWaitingErrors() {
        String errors = "";
        for (String error: studentManager.getWaitingErrors()) {
            errors += error;
            errors += "\n";
        }
        return errors;
    }
    
    public ClassTimeData getFarsiClassTime(String courseCode, String classCode)
            throws Exception {
        if (!isAnybodyLoggedIn())
            throw new BolbolestanStudentNotFoundError();
        return offeringManager.getFarsiClassTime(courseCode, classCode);
    }

    public String getCourseNameById (String courseCode) throws Exception {
        Course course = courseManager.getCourseByCode(courseCode);
        return course.getName();
    }

    public CourseSelection getStudentCourseSelection(String email) throws Exception {
        Student student = new StudentMapper().getStudentByEmail(email);
        if (student == null)
            throw new BolbolestanStudentNotFoundError();

        return studentManager.getStudentCourseSelection(student);
    }

    public ArrayList<String> getPrerequisites(String courseCode) {
        ArrayList<String> result = new ArrayList<>();
        try {
            result = new PrerequisiteMapper().getPrerequisites(courseCode);
            for (String prerequisite : result) {
                System.out.println(prerequisite);
            }
            return result;
        } catch (Exception e) {
            System.out.println("get prerequisite error : " + e.getMessage());
            return result;
        }
    }

    public void changePassword(String email, String newPassword) throws Exception {
        Student student = new StudentMapper().getStudentByEmail(email);
        if (student == null)
            throw new BolbolestanStudentNotFoundError();
        studentManager.changePassword(student, newPassword);
    }
}
