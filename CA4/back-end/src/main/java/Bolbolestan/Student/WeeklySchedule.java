package Bolbolestan.Student;

import Bolbolestan.Offering.Offering;
import Bolbolestan.exeptions.*;
import java.util.ArrayList;
import java.util.List;


public class WeeklySchedule {
    private List<Offering> weeklySchedule;

    public WeeklySchedule() {
        weeklySchedule = new ArrayList<Offering>();
    }

    public List<Offering> getOfferings() {
        return weeklySchedule;
    }

    public void addToWeeklySchedule(Offering offering) {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<Offering>();
        if (!weeklySchedule.contains(offering)) {
            weeklySchedule.add(offering);
        }
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanCourseNotFoundError();
        weeklySchedule.remove(offering);
    }


    public int getTotalUnits() {
        int units = 0;
        for (Offering offering : weeklySchedule) {
            units += offering.getUnits();
        }
        return units;
    }

    private void checkValidNumberOfUnits(int currentUnits) throws Exception {
        int units = getTotalUnits() + currentUnits;
        System.out.println(units);
        if (units > 20)
            throw new BolbolestanMaximumUnitsError();
        if (units < 12)
            throw new BolbolestanMinimumUnitsError();
    }

    private String makeCapacityMessage(Offering offering) {
        String message = "Capacity of course with code " + offering.getCourseCode() +
                " is full.";
        return message;
    }

    private List<String> checkCapacities() {
        List<String> errors = new ArrayList<String>();
        for (Offering offering: weeklySchedule) {
            if (offering.hasCapacity())
                continue;
            errors.add(makeCapacityMessage(offering));;
        }
        return errors;
    }

    public List<String> getSubmissionErrors(int currentUnits) {
        List<String> errors = new ArrayList<String>();
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<>();
        errors.addAll(checkCapacities());
        try {
            checkValidNumberOfUnits(currentUnits);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
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

    void copyWeeklySchedule(WeeklySchedule w){
        this.getOfferings().addAll(w.getOfferings());
    }

}