package Bolbolestan.Student;

import Bolbolestan.Offering.Offering;
import Bolbolestan.exeptions.*;
import java.util.ArrayList;
import java.util.List;


public class WeeklySchedule {
    private static final String finalized = "finalized";
    private static final String non_finalized = "non-finalized";
    private List<Offering> weeklySchedule = new ArrayList<Offering>();
    private String status = non_finalized;

    public List<Offering> getOfferings() {
        return weeklySchedule;
    }

    public void addToWeeklySchedule(Offering offering) {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<Offering>();
        if (!weeklySchedule.contains(offering)) {
            weeklySchedule.add(offering);
            status = non_finalized;
        }
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanCourseNotFoundError();
        boolean successful = weeklySchedule.remove(offering);
        if (!successful) {
            throw new BolbolestanCourseNotFoundError();
        }
        status = non_finalized;
    }


    public int getTotalUnits() {
        int units = 0;
        for (Offering offering : weeklySchedule) {
            units += offering.getUnits();
        }
        return units;
    }

    private void checkValidNumberOfUnits() throws Exception {
        int units = getTotalUnits();
        System.out.println(units);
        if (units > 20)
            throw new BolbolestanMaximumUnitsError();
        if (units < 12)
            throw new BolbolestanMinimumUnitsError();
    }

    public void finalizeWeeklySchedule() throws Exception {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<>();
        checkValidNumberOfUnits();
        status = finalized;

        for (Offering offering : weeklySchedule) {
            offering.reduceCapacity();
        }
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        String courseName = "";
        for (Offering offering : weeklySchedule) {
            if (offering.hasClassTime(day, startTime)) {
                courseName = offering.getName();
                break;
            }
        }
        return courseName;
    }

    public void removeAllCourses() {
        weeklySchedule = new ArrayList<Offering>();
    }

}