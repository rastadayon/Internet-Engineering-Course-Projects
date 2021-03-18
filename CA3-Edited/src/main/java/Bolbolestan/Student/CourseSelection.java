package Bolbolestan.Student;

import Bolbolestan.Offering.Offering;

import java.util.ArrayList;
import java.util.List;

public class CourseSelection {
    private WeeklySchedule submittedOfferings = new WeeklySchedule();
    private WeeklySchedule selectedOfferings = new WeeklySchedule();
    private List<String> submissionErrors = new ArrayList<String>();

    public WeeklySchedule getSelectedOfferings() { return selectedOfferings; }

    public WeeklySchedule getSubmittedOfferings() { return submittedOfferings; }

    public void addToSelectedOfferings(Offering offering) {
        if (selectedOfferings == null)
            selectedOfferings = new WeeklySchedule();
        selectedOfferings.addToWeeklySchedule(offering);
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        selectedOfferings.removeFromWeeklySchedule(offering);
        submittedOfferings.removeFromWeeklySchedule(offering);
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        if (submittedOfferings == null)
            return "";
        return submittedOfferings.getCourseNameByClassTime(day, startTime);
    }

    public void resetSelectedOfferings() {
        selectedOfferings.copyWeeklySchedule(submittedOfferings);
    }

    public void setErrors(List<String> errors) {
        submissionErrors = new ArrayList<String>();
        submissionErrors.addAll(errors);
        submissionErrors.addAll(selectedOfferings.getSubmissionErrors());
    }

    public List<String> getSubmissionErrors() { return submissionErrors;}

    public void makeFinalized() {
        List<Offering> selected = selectedOfferings.getOfferings();
        List<Offering> submitted = submittedOfferings.getOfferings();
        for (Offering  offering: selected) {
            if (submitted.contains(offering))
                continue;
            offering.reduceCapacity();
        }
        submittedOfferings.copyWeeklySchedule(selectedOfferings);
    }
}
