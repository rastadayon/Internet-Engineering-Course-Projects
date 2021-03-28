package Bolbolestan.Student;

import Bolbolestan.Offering.Offering;

import java.util.ArrayList;
import java.util.List;

public class CourseSelection {
    private WeeklySchedule submittedOfferings = new WeeklySchedule();
    private WeeklySchedule selectedOfferings = new WeeklySchedule();
    private WeeklySchedule waitingOfferings = new WeeklySchedule();
    private List<String> submissionErrors = new ArrayList<String>();
    private List<String> waitingErrors = new ArrayList<String>();

    public WeeklySchedule getSelectedOfferings() { return selectedOfferings; }

    public WeeklySchedule getSubmittedOfferings() { return submittedOfferings; }

    public WeeklySchedule getWaitingOfferings() { return waitingOfferings; }

    public void addToSelectedOfferings(Offering offering) {
        if (selectedOfferings == null)
            selectedOfferings = new WeeklySchedule();
        selectedOfferings.addToWeeklySchedule(offering);
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        if (submittedOfferings.getOfferings().contains(offering))
            offering.decreaseSignedUp();
        selectedOfferings.removeFromWeeklySchedule(offering);
        submittedOfferings.removeFromWeeklySchedule(offering);
        waitingOfferings.removeFromWeeklySchedule(offering);
    }

    public void addToWaitingOfferings(Offering offering) throws Exception{
        if (waitingOfferings == null)
            waitingOfferings = new WeeklySchedule();
        selectedOfferings.removeFromWeeklySchedule(offering);
        if (!submittedOfferings.getOfferings().contains(offering))
            waitingOfferings.addToWeeklySchedule(offering);
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        if (submittedOfferings == null)
            return "";
        return submittedOfferings.getCourseNameByClassTime(day, startTime);
    }

    public void resetSelectedOfferings() {
        selectedOfferings = new WeeklySchedule();
    }

    public void setSubmissionErrors(List<String> errors) {
        submissionErrors = new ArrayList<String>();
        submissionErrors.addAll(checkClassTimeConflicts(selectedOfferings));
        submissionErrors.addAll(checkExamTimeConflicts(selectedOfferings));
        submissionErrors.addAll(errors);
        submissionErrors.addAll(selectedOfferings.getSubmissionErrors(
                submittedOfferings.getTotalUnits()));
    }

    public void setWaitingErrors(List<String> errors) {
        submissionErrors = new ArrayList<String>();
        submissionErrors.addAll(checkClassTimeConflicts(waitingOfferings));
        submissionErrors.addAll(checkExamTimeConflicts(waitingOfferings));
        submissionErrors.addAll(errors);
        submissionErrors.addAll(waitingOfferings.getSubmissionErrors(
                submittedOfferings.getTotalUnits()));
    }

    public List<String> getSubmissionErrors() { return submissionErrors;}

    public List<String> getWaitingErrors() { return submissionErrors;}

    private List<Offering> getUnionOfCourses() {
        List<Offering> union = new ArrayList<Offering>();
        union.addAll(selectedOfferings.getOfferings());
        union.addAll(submittedOfferings.getOfferings());
        union.addAll(waitingOfferings.getOfferings());
        return union;
    }

    public String makeClassTimeConflictMessage(Offering first, Offering second) {
        String message = "Course with code " + first.getCourseCode() +
                " has class time collision with course with code " +
                second.getCourseCode();
        return message;
    }

    public String makeExamTimeConflictMessage(Offering first, Offering second) {
        String message = "Course with code " + first.getCourseCode() +
                " has exam time collision with course with code " +
                second.getCourseCode();
        return message;
    }

    public ArrayList<String> checkClassTimeConflicts(WeeklySchedule weeklySchedule) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Offering first: weeklySchedule.getOfferings()) {
            for (Offering second: getUnionOfCourses()) {
                if (first.equals(second))
                    continue;
                if (first.doesClassTimeCollide(second)) {
                    if (errors.contains(makeClassTimeConflictMessage(second, first)))
                        continue;
                    errors.add(makeClassTimeConflictMessage(first, second));
                }
            }
        }
        return errors;
    }

    public ArrayList<String> checkExamTimeConflicts(WeeklySchedule weeklySchedule) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Offering first: weeklySchedule.getOfferings()) {
            for (Offering second: getUnionOfCourses()) {
                if (first.equals(second))
                    continue;
                if (first.doesExamTimeCollide(second)) {
                    if (errors.contains(makeExamTimeConflictMessage(second, first)))
                        continue;
                    errors.add(makeExamTimeConflictMessage(first, second));
                }
            }
        }
        return errors;
    }

    public void makeFinalized() {
        List<Offering> selected = selectedOfferings.getOfferings();
        List<Offering> submitted = submittedOfferings.getOfferings();
        for (Offering  offering: selected) {
            offering.increaseSignedUp();
        }
        submittedOfferings.copyWeeklySchedule(selectedOfferings);
        selectedOfferings = new WeeklySchedule();
    }

    public int getTotalSelectedUnits() {
        int totalSelectedUnits = selectedOfferings.getTotalUnits();
        totalSelectedUnits += submittedOfferings.getTotalUnits();
        return totalSelectedUnits;
    }
}
