package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions.BolbolestanRulesViolationError;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    String loggedInStudent = null;

    public Student getStudentById(String studentId) throws Exception{
        if (studentId == null)
            throw new BolbolestanStudentNotFoundError();
        for (Student student : students)
            if (student.getId().equals(studentId))
                return student;
        throw new BolbolestanStudentNotFoundError();
    }

    public ArrayList<String> getStudentIds() {
        ArrayList<String> studentIds = new ArrayList<>();
        for (Student student : students)
            studentIds.add(student.getId());
        return studentIds;
    }

    public boolean doesStudentExist(String studentId) {
        for (Student student : students)
            if (student.getId().equals(studentId))
                return true;
        return false;
    }

    public boolean doesStudentExist(Student student) {
        for (Student existingStudent: students)
            if (existingStudent.isEqual(student))
                return true;
        return false;
    }

    public void addStudent(Student student) throws Exception {
        if (doesStudentExist(student))
            throw new BolbolestanRulesViolationError(String.format("Student with id %s already exists.", student.getId()));
        students.add(student);
    }

//    public void addGradeToStudent(String studentId, Grade grade) throws Exception{
//        Student student = getStudentById(studentId);
//        student.addGrade(grade);
//    }

    public void addToSelectedOfferings(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        student.addToSelectedOfferings(offering);
    }

    public void removeFromWeeklySchedule(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        student.removeFromWeeklySchedule(offering);
    }

    public boolean hasCapacityError(List<String> errors) {
        for (String error: errors) {
            if (error.contains("ظرفیت"))
                return true;
        }
        return false;
    }

    public boolean addCourseToWaitingList(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        student.addToWaitingOfferings(offering);
        student.setWaitingErrors(offering);
        if (student.getSubmissionErrors().size() == 0)
            return true;
        else {
            if (student.getSubmissionErrors().size() == 1 && 
                hasCapacityError(student.getSubmissionErrors()))
                return true;
            student.removeFromWeeklySchedule(offering);
            //student.addToSelectedOfferings(offering);
            return false;
        }
    }

    public boolean finalizeSchedule(String studentId) throws Exception {
        Student student = getStudentById(studentId);
        student.setSubmissionErrors();
        if (student.getSubmissionErrors().size() == 0) {
            student.finalizeSchedule();
            return true;
        }
        else
            return false;
    }

    public ArrayList<Offering> getClassTimeConflicts(Student student, Offering offering, List<Offering> schedule) throws Exception {
        ArrayList<Offering> conflictingOfferings = null;
        for (Offering scheduleOffering : schedule) {
            if (offering.doesClassTimeCollide(scheduleOffering )) {
                if (conflictingOfferings == null)
                    conflictingOfferings = new ArrayList<>();
                conflictingOfferings.add(scheduleOffering);
            }
        }
        return conflictingOfferings;
    }

    public ArrayList<String> getPrerequisitesNotPassed(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        return student.getPrerequisitesNotPassed(offering);
    }

    public void addCourseToStudent(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        student.addToSelectedOfferings(offering);
    }

    public String getLoggedInId() { return this.loggedInStudent; }

    public Boolean isAnybodyLoggedIn() {
        if (loggedInStudent == null)
            return false;
        return true;
    }

    public void makeLoggedIn(String studentId) {
        this.loggedInStudent = studentId;
    }

    public void makeLoggedOut() {
        this.loggedInStudent = null;
    }

    public void checkWaitingLists() {
        for (Student student: students) {
            student.checkWaitingCourses();
        }
    }

    public StudentInfo getStudentInfo() throws Exception{
        Student loggedInStudent = getStudentById(getLoggedInId());
        if (loggedInStudent == null) {
            throw new BolbolestanStudentNotFoundError();
        }
        return loggedInStudent.getInfo();
    }

    public ArrayList<ReportCard> getStudentReports() throws Exception {
        Student loggedInStudent = getStudentById(getLoggedInId());
        if (loggedInStudent == null) {
            throw new BolbolestanStudentNotFoundError();
        }
        return loggedInStudent.getReportCards();
    }

    public void setReportCards(String studentId, ArrayList<Grade> grades) throws Exception {
        System.out.println("setting report cards for: " + studentId);
        Student student = getStudentById(studentId);
        student.setReportCards(grades);
    }

    public void searchForCourses(String searchCourse) throws Exception {
        if(!isAnybodyLoggedIn())
            throw new BolbolestanStudentNotFoundError();
        Student loggedIn = getStudentById(loggedInStudent);
        loggedIn.searchFor(searchCourse);
    }
}
