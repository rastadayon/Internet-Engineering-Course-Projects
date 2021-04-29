package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Utilities;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.ExamTime;

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

    public static String informalDate(ExamTime examTime) {
        String date = "";
        String end = examTime.getEnd();
        String[] tempData = end.split("T");
        assert (tempData.length == 2);
        String tempDate = tempData[0];
        tempData = tempDate.split("-");
        assert (tempData.length == 3);
        for (int i = tempData.length - 2; i < tempData.length; i++) {
            date += tempData[i];
            if (i != tempData.length - 1) date += '/';
        }

        return date;
    }

    public static String informalTimeSpan(ExamTime examTime) {
        String start = examTime.getStart();
        String end = examTime.getEnd();
        String[] tempStart = start.split("T");
        String[] tempEnd = end.split("T");
        assert (tempStart.length == 2 && tempEnd.length == 2);
        start = tempStart[1];
        end = tempEnd[1];
        tempStart = start.split(":");
        tempEnd = end.split(":");
        assert (tempStart.length == 3 && tempEnd.length == 3);
        start = "";
        end = "";
        for (int i = 0; i < tempStart.length-1; i++) {
            start += tempStart[i];
            end += tempEnd[i];
            if (i != tempStart.length-2) {
                start += ":";
                end += ":";
            }
        }
        return start + " - " + end;
    }
}
