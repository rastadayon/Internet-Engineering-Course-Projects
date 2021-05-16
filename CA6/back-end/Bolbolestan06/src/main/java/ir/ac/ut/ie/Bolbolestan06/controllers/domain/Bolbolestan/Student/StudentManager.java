package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions.BolbolestanRulesViolationError;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.Selection;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.StudentInfo;
import ir.ac.ut.ie.Bolbolestan06.repository.BolbolestanRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentManager {
    private List<Student> students = new ArrayList<>();
    String loggedInStudent = null;
    List<String> errors = null;

    public Student getStudentById(String studentId) throws Exception{
        if (studentId == null)
            throw new BolbolestanStudentNotFoundError();
        try {
            return BolbolestanRepository.getInstance().findStudentById(studentId);
        }catch (SQLException e){
            throw new BolbolestanStudentNotFoundError();
        }
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
        Student student = getStudentById(studentId); //TODO: duplicatedMethod
        student.addToSelectedOfferings(offering);
        addCourseToStudent(studentId, offering);
    }

    //public void removeFromWeeklySchedule(String studentId, Offering offering) throws Exception {
        //Student student = getStudentById(studentId);
        //student.removeFromWeeklySchedule(offering);
    //}

    public boolean hasCapacityError(List<String> errors) {
        for (String error: errors) {
            if (isCapacityError(error))
                return true;
        }
        return false;
    }

    public boolean isCapacityError(String error) {
        if (error.contains("ظرفیت"))
            return true;
        return false;
    }

    public boolean addCourseToWaitingList(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        addCourseToWaitingForStudent(studentId, offering);
        CourseSelection courseSelection = getStudentCourseSelectionById(studentId);
        student.setCourseSelection(courseSelection);
        student.setReportCards();
        student.setWaitingErrors(offering);
        errors = student.getSubmissionErrors();
        if (errors.size() == 0)
            return true;
        else {
            if (student.getSubmissionErrors().size() == 1 && 
                hasCapacityError(student.getSubmissionErrors()))
                return true;
            student.removeFromWeeklySchedule(offering); //TODO: Remove
            removeFromWeeklySchedule(studentId, offering);
            //student.addToSelectedOfferings(offering);
            return false;
        }
    }

    public boolean finalizeSchedule(String studentId) throws Exception {
        Student student = getStudentById(studentId);
        CourseSelection courseSelection = getStudentCourseSelectionById(studentId);
        student.setCourseSelection(courseSelection);
        student.setReportCards();
        student.setSubmissionErrors();
        errors = student.getSubmissionErrors();
        System.out.println(errors.size());
        if (errors.size() == 0) {
            finalizeScheduleById(studentId);
            return true;
        }
        else
            return false;
    }

    private ArrayList<Grade> getStudentGrades(String studentId) throws Exception {
        return BolbolestanRepository.getInstance().getStudentGrades(studentId);
    }

    private void finalizeScheduleById(String studentId) throws Exception {
        try {
            BolbolestanRepository.getInstance().finalizeScheduleById(studentId);
        }catch (SQLException e){
            throw new BolbolestanStudentNotFoundError();
        }
    }

    public WeeklySchedule getStudentScheduleById(String studentId) throws Exception {
        try {
            return BolbolestanRepository.getInstance().findStudentScheduleById(studentId, "submitted");
        }catch (SQLException e){
            throw new BolbolestanStudentNotFoundError();
        }
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

    public void removeFromWeeklySchedule(String studentId, Offering offering) {
        try {
            BolbolestanRepository.getInstance().removeSelection(studentId, offering.getCourseCode(),
                        offering.getClassCode());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void addCourseToStudent(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        student.addToSelectedOfferings(offering); //TODO: Delete
        Selection selection = new Selection(studentId, offering.getCourseCode(),
                offering.getClassCode(), "selected");
        BolbolestanRepository.getInstance().insertSelection(selection);
    }

    public void addCourseToWaitingForStudent(String studentId, Offering offering) throws Exception {
        Student student = getStudentById(studentId);
        student.addToSelectedOfferings(offering); //TODO: Delete
        Selection selection = new Selection(studentId, offering.getCourseCode(),
                offering.getClassCode(), "waiting");
        BolbolestanRepository.getInstance().insertSelection(selection);
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
        try {
            BolbolestanRepository.getInstance().checkWaitingLists();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public StudentInfo getStudentInfo() throws Exception {
        Student loggedInStudent = BolbolestanRepository.getInstance().getStudent(getLoggedInId());
        if (loggedInStudent == null) {
            throw new BolbolestanStudentNotFoundError();
        }
        return loggedInStudent.getInfo();
    }

    public ArrayList<ReportCard> getStudentReports() throws Exception {
        Student loggedInStudent = getStudentById(getLoggedInId());
        if (loggedInStudent == null)
            throw new BolbolestanStudentNotFoundError();
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

    public CourseSelection getStudentCourseSelectionById(String studentId) throws Exception {
        try {
            return BolbolestanRepository.getInstance().findCourseSelectionById(studentId);
        }catch (SQLException e){
            throw new BolbolestanStudentNotFoundError();
        }
    }

    public void resetSelectionsById(String studentId) throws Exception {
        try {
            BolbolestanRepository.getInstance().deleteSelectionsById(studentId);
        }catch (SQLException e){
            throw new BolbolestanStudentNotFoundError();
        }
    }

    public List<String> getWaitingErrors() {
        List<String> waitingErrors = new ArrayList<String>();
        for (String error: errors) {
            if (isCapacityError(error))
                continue;
            waitingErrors.add(error);
        }
        return waitingErrors;
    }

    public List<String> getSubmissionErrors() {
        return errors;
    }
}
