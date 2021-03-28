package Bolbolestan.Student;

import Bolbolestan.Offering.Offering;
import Bolbolestan.exeptions.*;
import Bolbolestan.Utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String id;
    private final String name;
    private final String secondName;
    private final String birthDate;
    private ArrayList<Grade> grades = new ArrayList<Grade>();
    private String searchString = null;
    private CourseSelection courseSelection;

    public Student() {
        courseSelection = new CourseSelection();
        this.id = null;
        this.name = null;
        this.secondName = null;
        this.birthDate = null;
    }

    public Student(String id, String name, String secondName, String birthDate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
        courseSelection = new CourseSelection();
    }

    public String getName() { return name; }

    public String getSecondName() { return secondName; }

    public String getBirthDate() { return birthDate; }

    public ArrayList<Grade> getGrades() { return grades; }

    public WeeklySchedule getSelectedOfferings() {
        return courseSelection.getSelectedOfferings();
    }

    public WeeklySchedule getSubmittedOfferings() {
        return courseSelection.getSubmittedOfferings();
    }

    public WeeklySchedule getWaitingOfferings() {
        return courseSelection.getWaitingOfferings();
    }

    public String getSearchString() { return searchString; }

    public void print() {
        System.out.println(String.format("student id : %s", id));
        System.out.println(String.format("student name : %s %s", name, secondName));
        System.out.println(String.format("birth date : %s", birthDate));
    }

    public float getGPA() {
        int count = 0;
        float gradeSum = 0;
        Utils utils = Utils.getInstance();
        if (grades != null)
            for (Grade grade : grades) {
                gradeSum += (grade.getGrade()*grade.getUnits());
                count += grade.getUnits();
            }
        if (count != 0)
            return utils.round(gradeSum/count, 2);
        else
            return 0;
    }

    public String getId() {
        return this.id;
    }

    public void addGrade(Grade grade) {
        if (grades == null)
            grades = new ArrayList<>();
        grades.add(grade);
    }

    public void addToSelectedOfferings(Offering offering) {
        courseSelection.addToSelectedOfferings(offering);
    }

    public void addToWaitingOfferings(Offering offering) throws Exception {
        courseSelection.addToWaitingOfferings(offering);
    }

    public boolean isEqual(Student student) {
        if (student == null)
            return false;
        return this.id.equals(student.getId());
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        courseSelection.removeFromWeeklySchedule(offering);
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        String courseName = courseSelection.getCourseNameByClassTime(day, startTime);
        return courseName;
    }

    public ArrayList<String> getPrerequisitesNotPassed(Offering offering) {
        ArrayList<String> notPassed = new ArrayList<>();
        ArrayList<String> prerequisites = offering.getPrerequisites();
        for (String prerequisite : prerequisites) {
            boolean found = false;
            for (Grade grade : grades) {
                if (grade.getCode().equals(prerequisite) && grade.getGrade() >= 10) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                notPassed.add(prerequisite);
            }
        }
        return notPassed;
    }

    public boolean notPassedBefore(Offering offering) {
        for (Grade grade : grades) {
            if (grade.getCode().equals(offering.getCourseCode()) && grade.getGrade() >= 10) {
                return false;
            }
        }
        return true;
    }

    public void searchFor(String courseName) {
        searchString = courseName;
    }

    public void clearSearch() {
        searchString = null;
    }

    public void resetSelectedOfferings() {
        courseSelection.resetSelectedOfferings();
    }

    public String makePassedMessage(Offering offering) {
        String message = "Course with code " + offering.getCourseCode() +
                " has been passed before";
        return message;
    }

    private List<String> checkNotPassedBefore() {
        List<String> errors = new ArrayList<String>();
        List<Offering> offerings = getSelectedOfferings().getOfferings();
        for (Offering offering: offerings)
            errors.addAll(coursePassedBeforeErrors(offering));
        return errors;
    }

    private List<String> coursePassedBeforeErrors(Offering offering) {
        List<String> errors = new ArrayList<String>();
        if (notPassedBefore(offering) == false)
            errors.add(makePassedMessage(offering));
        return errors;
    }

    public String makePrerequisitesMessage(List<String> notPassed, Offering offering) {
        String message = "Prerequisites for course with code " + offering.getCourseCode() +
        " are not passed {";
        for (int i=0; i<notPassed.size(); i++) {
            message += notPassed.get(i);
            if (i == (notPassed.size()-1))
                message += "}";
            else
                message += ", ";
        }
        return message;
    }

    private List<String> checkHasPrerequisites() {
        List<String> errors = new ArrayList<String>();
        List<Offering> offerings = getSelectedOfferings().getOfferings();
        for (Offering offering: offerings)
            errors.addAll(coursePrerequisitesErrors(offering));
        return errors;
    }

    private List<String> coursePrerequisitesErrors(Offering offering) {
        List<String> errors = new ArrayList<String>();
        if (getPrerequisitesNotPassed(offering).size() != 0)
            errors.add(makePrerequisitesMessage(getPrerequisitesNotPassed(offering),
                    offering));
        return errors;
    }

    public void setSubmissionErrors() {
        List<String> errors = new ArrayList<String>();
        errors.addAll(checkHasPrerequisites());
        errors.addAll(checkNotPassedBefore());
        courseSelection.setSubmissionErrors(errors);
    }

    public void setWaitingErrors(Offering offering) {
        List<String> errors = new ArrayList<String>();
        errors.addAll(coursePrerequisitesErrors(offering));
        errors.addAll(coursePassedBeforeErrors(offering));
        courseSelection.setWaitingErrors(errors);
    }

    public List<String> getSubmissionErrors() { return courseSelection.getSubmissionErrors();}

    public List<String> getWaitingErrors() { return courseSelection.getWaitingErrors();}

    public void finalizeSchedule() {
        courseSelection.makeFinalized();
    }

    public int getTotalSelectedUnits() {
        return courseSelection.getTotalSelectedUnits();
    }
}
