package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Utilities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.math.BigDecimal;

public final class Utils {
    private static Utils utilsInstance = null;

    private Utils() {}

    public static Utils getInstance() {
        if (utilsInstance == null)
            utilsInstance = new Utils();
        return utilsInstance;
    }

    public static boolean doDateTimesCollide (String interval1Start, String interval1End,
                                        String interval2Start, String interval2End, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime start1 = LocalDateTime.parse(interval1Start, dtf);
        LocalDateTime end1 = LocalDateTime.parse(interval1End, dtf);
        LocalDateTime start2 = LocalDateTime.parse(interval2Start, dtf);
        LocalDateTime end2 = LocalDateTime.parse(interval2End, dtf);
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    public static boolean doTimesCollide(String interval1Start, String interval1End,
                                   String interval2Start, String interval2End, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalTime start1 = LocalTime.parse(interval1Start, dtf);
        LocalTime end1 = LocalTime.parse(interval1End, dtf);
        LocalTime start2 = LocalTime.parse(interval2Start, dtf);
        LocalTime end2 = LocalTime.parse(interval2End, dtf);
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

        public ArrayList<String> correctTimeFormat(String[] time) {
        ArrayList<String> correctTime = new ArrayList<String>();
        for (String t : time) {
            String s = t;
            if (!s.contains(":"))
                s += ":00";
            if (s.indexOf(":") == 1)
                s = "0" + s;
            if (s.indexOf(":") == s.length()-2)
                s = s.substring(0, s.indexOf(":") + 1) + "0" + s.substring(s.indexOf(":") + 1);
            correctTime.add(s);
        }
        return correctTime;
    }

    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
