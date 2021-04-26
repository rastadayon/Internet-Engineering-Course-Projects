package ir.ac.ut.ie.Bolbolestan05.controllers.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassTimeData {
    String farsiDays;
    String time;
    private final HashMap<String, String> englishToFarsiDays = new HashMap<>();

    public ClassTimeData(String time, List<String> days) {
        englishToFarsiDays.put("Saturday", "شنبه");
        englishToFarsiDays.put("Sunday", "یکشنبه");
        englishToFarsiDays.put("Monday", "دوشنبه");
        englishToFarsiDays.put("Tuesday", "سه شنبه");
        englishToFarsiDays.put("Wednesday", "چهار شنبه");
        englishToFarsiDays.put("Thursday", "پنج شنبه");
        englishToFarsiDays.put("Friday", "جمعه");

        ArrayList<String> farsiDays = new ArrayList<>();
        for (String day : days) {
            farsiDays.add(englishToFarsiDays.get(day));
        }

        this.farsiDays = String.join(" - ", farsiDays);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getFarsiDays() {
        return farsiDays;
    }
}
