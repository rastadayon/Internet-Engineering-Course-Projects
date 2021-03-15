package Bolbolestan.Student;

import Bolbolestan.Offering.Offering;
import Bolbolestan.exeptions.*;

import java.util.ArrayList;

public class Student {
    private final String id;
    private final String name;
    private final String secondName;
    private final String birthDate;
    private WeeklySchedule weeklySchedule = new WeeklySchedule();
    private ArrayList<Grade> grades = new ArrayList<Grade>();

    public Student(String id, String name, String secondName, String birthDate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
        weeklySchedule = new WeeklySchedule();
    }

    public String getName() { return name; }
    public String getSecondName() { return secondName; }
    public String getBirthDate() { return birthDate; }
    public ArrayList<Grade> getGrades() { return grades; }
    public WeeklySchedule getWeeklySchedule() {
        return weeklySchedule;
    }

    public void print() {
        System.out.println(String.format("student id : %s", id));
        System.out.println(String.format("student name : %s %s", name, secondName));
        System.out.println(String.format("birth date : %s", birthDate));
    }

    public float getGPA() {
        int count = 0;
        int gradeSum = 0;
        if (grades != null)
            for (Grade grade : grades) {
                gradeSum += grade.getGrade();
                count += 1;
            }
        if (count != 0)
            return gradeSum/count;
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

    public void addToWeeklySchedule(Offering offering) {
        if (weeklySchedule == null)
            weeklySchedule = new WeeklySchedule();
        weeklySchedule.addToWeeklySchedule(offering);
    }

//    private boolean hasPrerequisites(Course course) {
//        ArrayList<String> prerequisites = course.getPrerequisites();
//        for (String prerequisite : prerequisites) {
//            boolean hasPassed = false;
//            for (Grade grade : grades) {
//                if (grade.getCode().equals(prerequisite))
//                    if (grade.getGrade() >= 10) {
//                        hasPassed = true;
//                        continue;
//                    }
//                if (!hasPassed)
//                    return false;
//            }
//        }
//        return true;
//    }

    public boolean isEqual(Student student) {
        if (student == null)
            return false;
        return this.id.equals(student.getId());
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanCourseNotFoundError();
        weeklySchedule.removeFromWeeklySchedule(offering);
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        if (weeklySchedule == null)
            return "";
        return weeklySchedule.getCourseNameByClassTime(day, startTime);
    }

    public ArrayList<String> getPrerequisitesNotPassed(Offering offering) {
        ArrayList<String> notPassed = null;
        ArrayList<String> prerequisites = offering.getPrerequisites();
        for (String prerequisite : prerequisites) {
            boolean found = false;
            for (Grade grade : grades) {
                if (grade.getCode() == prerequisite && grade.getGrade() >= 10) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (notPassed == null)
                    notPassed = new ArrayList<>();
                notPassed.add(prerequisite);
            }
        }
        return notPassed;
    }
}
