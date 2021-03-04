import com.google.gson.annotations.SerializedName;
import exeptions.BolbolestanOfferingNotFoundError;


public class Student {
    private String studentId;
    private String name;
    @SerializedName("enteredAt") private String enterYear;

    private WeeklySchedule weeklySchedule = new WeeklySchedule();
    private boolean weeklyScheduleFinalized = false;

    public Student(String studentId, String name, String enterYear) {
        this.studentId = studentId;
        this.name = name;
        this.enterYear = enterYear;
    }

    public WeeklySchedule getWeeklySchedule() {
        return weeklySchedule;
    }

    public String getId() {
        return this.studentId;
    }

    public void addToWeeklySchedule(Offering offering) {
        if (weeklySchedule == null)
            weeklySchedule = new WeeklySchedule();
        weeklySchedule.addToWeeklySchedule(offering);
        offering.reduceCapacity();
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanOfferingNotFoundError();
        weeklySchedule.removeFromWeeklySchedule(offering);
    }
}
