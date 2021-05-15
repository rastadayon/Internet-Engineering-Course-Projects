package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassTime {
    private String time;
    private List<String> days = new ArrayList<String>();

    public ClassTime(String time, List<String> days) {
        this.time = time;
        this.days = days;
    }

    public ClassTime(ClassTime that) {
        this(that.getTime(), that.getDays());
    }

    public String getTime() { return time; }

    public List<String> getDays() { return days; }

    public void print() {
        System.out.println("class time :");
        System.out.print("\tdays : [ ");
        for (int i = 0; i < days.size(); i++) {
            if (i != 0)
                System.out.print(", ");
            System.out.print(days.get(i));
        }
        System.out.println(" ]");
        System.out.println(String.format("\ttime : %s", time));
    }

    public boolean hasTime(String day, String startTime) {
        if (days.contains(day) && time.startsWith(startTime))
            return true;
        return false;
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("days", days);
        jo.put("time", time);

        return jo;
    }

    public void setFarsiData() {

    }
}
