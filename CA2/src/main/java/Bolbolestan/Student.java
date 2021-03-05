package Bolbolestan;

import Bolbolestan.exeptions.*;

import java.util.ArrayList;

public class Student {
    private final String id;
    private final String name;
    private final String secondName;
    private final String birthDate;

    private WeeklySchedule weeklySchedule = new WeeklySchedule();
    private boolean weeklyScheduleFinalized = false;
    private ArrayList<Grade> grades = new ArrayList<Grade>();

    public Student(String id, String name, String secondName, String birthDate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }

    public void print() {
        System.out.println(String.format("student id : %s", id));
        System.out.println(String.format("student name : %s %s", name, secondName));
        System.out.println(String.format("birth date : %s", birthDate));
    }

    public WeeklySchedule getWeeklySchedule() {
        return weeklySchedule;
    }

    public String getId() {
        return this.id;
    }

    public void addGrade(Grade grade) {
        if (grades == null)
            grades = new ArrayList<Grade>();
        grades.add(grade);
    }

    public void addToWeeklySchedule(Course course) {
        if (weeklySchedule == null)
            weeklySchedule = new WeeklySchedule();
        weeklySchedule.addToWeeklySchedule(course);
        course.reduceCapacity();
    }

    public void removeFromWeeklySchedule(Course course) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanCourseNotFoundError();
        weeklySchedule.removeFromWeeklySchedule(course);
    }
}
