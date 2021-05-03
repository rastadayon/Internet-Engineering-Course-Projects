package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassTime {

    private String time;
    private String courseCode;
    private String classCode;
    private List<String> days = new ArrayList<String>();

    public ClassTime(String time, List<String> days) {
        this.time = time;
        this.days = days;
    }

    public ClassTime(String time, String firstDay, String secondDay) {
        this.time = time;
        this.days.add(firstDay);
        if (secondDay != null)
            this.days.add(secondDay);
    }

    public String getTime() { return time; }

    public List<String> getDays() { return days; }

    public void setOffering(String courseCode, String classCode) {
        this.courseCode = courseCode;
        this.classCode = classCode;
    }

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

    public String getCourseCode() { return courseCode; }

    public String getClassCode() { return classCode; }

    public String getFirstDay() { 
    	if (days.size() >= 1)
    		return days.get(0); 
    	return "";
    }

    public String getSecondDay() { 
    if (days.size() >= 2)
    		return days.get(1); 
    	return ""; 
    }

    public boolean hasTime(String day, String startTime) {
        if (days.contains(day) && time.startsWith(startTime))
            return true;
        return false;
    }

    public boolean hasTowDays() {
        if (days.size() == 2)
            return true;
        return false;
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("days", days);
        jo.put("time", time);

        return jo;
    }
}
