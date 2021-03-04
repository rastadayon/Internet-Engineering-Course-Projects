package Bolbolestan;

import com.google.gson.Gson;
import Bolbolestan.exeptions.*;


import java.util.*;

public class Bolbolestan {
    private Map<String, Student> students = new HashMap<>();
    private Map<String, Course> offerings = new HashMap<>();


    public Map<String, Student> getStudents() { return students; }

    private Course getOfferingByCode(List<Course> weeklySchedule, String code) {
        if (weeklySchedule == null)
            return null;
        for (Course course : weeklySchedule) {
            if (code.equals(course.getCode()))
                return course;
        }
        return null;
    }

    public String addCourse(Course course) throws Exception {
        if (offerings.containsKey(course.getCode()))
            throw new BolbolestanRulesViolationError(String.format("Offering with the code %s already exists.", course.getCode()));
        offerings.put(course.getCode(), course);
        return "Offering successfully added.";
    }

    public String addStudent(Student student) throws Exception {
        if (students.containsKey(student.getId()))
            throw new BolbolestanRulesViolationError(String.format("Student with id %s already exists.", student.getId()));
        students.put(student.getId(), student);
        return "Student successfully added.";
    }

    public void addGradeToStudent(Student student, Grade grade) {
        student.addGrade(grade);
    }

    public List<Course> getOfferings(String studentId, Gson gson) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        List<Course> courseList = new ArrayList<Course>(offerings.values());
        Collections.sort(courseList);
        return courseList;
    }

    public Course getOffering(String studentId, String courseCode, Gson gson) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Course course = offerings.get(courseCode);

        if (course == null)
            throw new BolbolestanOfferingNotFoundError();

        return course;
    }

    public String addToWeeklySchedule(String studentId, String offeringCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        if (!offerings.containsKey(offeringCode))
            throw new BolbolestanOfferingNotFoundError();
        Student student = students.get(studentId);
        Course course = offerings.get(offeringCode);
        student.addToWeeklySchedule(course);
        return "Offering successfully added to weekly schedule.";
    }

    public String removeFromWeeklySchedule(String studentId, String offeringCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        if (!offerings.containsKey(offeringCode))
            throw new BolbolestanOfferingNotFoundError();
        Student student = students.get(studentId);
        Course course = offerings.get(offeringCode);
        student.removeFromWeeklySchedule(course);
        return "Offering successfully removed from weekly schedule.";
    }

    public WeeklySchedule handleGetWeeklySchedule(String studentId) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        return student.getWeeklySchedule();
    }

    public String handleFinalize(String studentId) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        student.getWeeklySchedule().finalizeWeeklySchedule();
        return "Weekly schedule successfully finalized.";
    }
}
