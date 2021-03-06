package Bolbolestan;

import Bolbolestan.exeptions.*;
import java.util.ArrayList;
import java.util.List;


public class WeeklySchedule {
    private static final String finalized = "finalized";
    private static final String non_finalized = "non-finalized";
    private List<Course> weeklySchedule = new ArrayList<Course>();
    private String status = non_finalized;

    public List<Course> getOfferings() {
        return weeklySchedule;
    }

    public void addToWeeklySchedule(Course course) {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<Course>();
        if (!weeklySchedule.contains(course)) {
            weeklySchedule.add(course);
            status = non_finalized;
        }
    }

    public void removeFromWeeklySchedule(Course course) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanCourseNotFoundError();
        boolean successful = weeklySchedule.remove(course);
        if (!successful) {
            throw new BolbolestanCourseNotFoundError();
        }
        status = non_finalized;
    }


    public int getTotalUnits() {
        int units = 0;
        for (Course course : weeklySchedule) {
            units += course.getUnits();
        }
        return units;
    }

    private void checkValidNumberOfUnits() throws Exception {
        int units = getTotalUnits();
        if (units > 20)
            throw new BolbolestanMaximumUnitsError();
        if (units < 12)
            throw new BolbolestanMinimumUnitsError();
    }

    public void finalizeWeeklySchedule() throws Exception {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<>();
        //checkCapacity();
        checkValidNumberOfUnits();
//        checkExamTimeCollision();
        status = finalized;
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        String courseName = "";
        for (Course course : weeklySchedule) {
            if (course.hasClassTime(day, startTime)) {
                courseName = course.getName();
                continue;
            }
        }
        return courseName;
    }
}