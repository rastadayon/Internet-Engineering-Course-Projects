package Bolbolestan;

import Bolbolestan.exeptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class WeeklySchedule {
    private static final String finalized = "finalized";
    private static final String non_finalized = "non-finalized";
    private List<Course> weeklySchedule = new ArrayList<Course>();
    private String status = non_finalized;

    public List<Course> getOfferings() {
        return weeklySchedule;
    }

    public String getStatus() {
        return status;
    }

    public JSONArray toJSON () {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Course course : weeklySchedule) {
            JSONObject obj = course.exposeToJSON();
            obj.put("status", status);
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    public void addToWeeklySchedule(Course course) {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<Course>();
        if (!weeklySchedule.contains(course)) {
            weeklySchedule.add(course);
            status = non_finalized;
        }
    }

    public void removeFromWeeklySchedule(Course course) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanCourseNotFoundError();
        boolean successful = weeklySchedule.remove(course);
        if (!successful){
            throw new BolbolestanCourseNotFoundError();
        }
        status = non_finalized;
    }

    public void removeAllOfferings() {
        weeklySchedule = new ArrayList<Course>();
    }

    private void checkCapacity() throws Exception {
        for (Course course : weeklySchedule) {
            if (course.getSeatsTaken() >= course.getCapacity())
                throw new BolbolestanCapacityError(course.getCode());
        }
    }

    public int getTotalUnits() {
        int units = 0;
        for (Course course : weeklySchedule) {
            units += course.getUnits();
        }
        return units;
    }

    private void checkValidNumberOfUnits() throws Exception {
        int units = getTotalUnits();
        if (units > 20)
            throw new BolbolestanMaximumUnitsError();
        if (units < 12)
            throw new BolbolestanMinimumUnitsError();
    }

//    private ArrayList<String> correctTimeFormat(String[] time) {
//        ArrayList<String> correctTime = new ArrayList<String>();
//        for (String t : time) {
//            String s = t;
//            if (!s.contains(":"))
//                s += ":00";
//            if (s.indexOf(":") == 1)
//                s = "0" + s;
//            if (s.indexOf(":") == s.length()-2)
//                s = s.substring(0, s.indexOf(":") + 1) + "0" + s.substring(s.indexOf(":") + 1);
//            correctTime.add(s);
//        }
//        return correctTime;
//    }

//    private boolean doesClassTimeCollide(Course o1, Course o2) {
//        List<String> o1Days = o1.getClassTime().getDays();
//        List<String> o2Days = o2.getClassTime().getDays();
//        o1Days.retainAll(o2Days);
//        if (o1Days.isEmpty())
//            return false;
//        ArrayList<String> o1Time = correctTimeFormat(o1.getClassTime().getTime().split("-"));
//        ArrayList<String> o2Time = correctTimeFormat(o2.getClassTime().getTime().split("-"));
//        assert o1Time.size() == 2 && o2Time.size() == 2;
//        String o1start = o1Time.get(0);
//        String o1end = o1Time.get(1);
//        String o2start = o2Time.get(0);
//        String o2end = o2Time.get(1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
////        Time
//        LocalTime start1 = LocalTime.parse(o1start, dtf);
//        LocalTime end1 = LocalTime.parse(o1end, dtf);
//        LocalTime start2 = LocalTime.parse(o2start, dtf);
//        LocalTime end2 = LocalTime.parse(o2end, dtf);
//        return !start1.isAfter(end2) && !start2.isAfter(end1);
//    }



//    private void checkClassTimeCollision() throws Exception {
//        for (int i=0; i < weeklySchedule.size(); i++) {
//            for (int j=i+1; j < weeklySchedule.size(); j++){
//                Course o1 = weeklySchedule.get(i);
//                Course o2 =  weeklySchedule.get(j);
//                if (doesClassTimeCollide(o1,o2))
//                    throw new BolbolestanClassTimeCollisionError(o1.getCode(), o2.getCode());
//            }
//        }
//    }

    private boolean doesExamTimeCollide(Course o1, Course o2) {
        String o1start = o1.getExamTime().getStart();
        String o1end = o1.getExamTime().getEnd();
        String o2start = o2.getExamTime().getStart();
        String o2end = o2.getExamTime().getEnd();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'kk:mm:ss");
        System.out.println(o1start);
        LocalDateTime start1 = LocalDateTime.parse(o1start, dtf);
        LocalDateTime end1 = LocalDateTime.parse(o1end, dtf);
        LocalDateTime start2 = LocalDateTime.parse(o2start, dtf);
        LocalDateTime end2 = LocalDateTime.parse(o2end, dtf);
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    private void checkExamTimeCollision() throws Exception {
        for (int i=0; i < weeklySchedule.size(); i++) {
            for (int j=i+1; j < weeklySchedule.size(); j++){
                Course o1 = weeklySchedule.get(i);
                Course o2 =  weeklySchedule.get(j);
                if (doesExamTimeCollide(o1,o2))
                    throw new BolbolestanExamTimeCollisionError(o1.getCode(), o2.getCode());
            }
        }
    }

    public void finalizeWeeklySchedule() throws Exception {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<>();
        //checkCapacity();
        checkValidNumberOfUnits();
        checkExamTimeCollision();
        status = finalized;
    }

    private boolean doTimesCollide(String interval1Start, String interval1End,
                                   String interval2Start, String interval2End, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalTime start1 = LocalTime.parse(interval1Start, dtf);
        LocalTime end1 = LocalTime.parse(interval1End, dtf);
        LocalTime start2 = LocalTime.parse(interval2Start, dtf);
        LocalTime end2 = LocalTime.parse(interval2End, dtf);
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    public boolean doesCourseTimeCollide(Course course) {
        List<String> courseDays = course.getClassTime().getDays();
        String[] courseTime = course.getClassTime().getTime().split("-");
        assert courseTime.length == 2;
        String courseStart = courseTime[0];
        String courseEnd = courseTime[1];
        for (int i = 0; i < weeklySchedule.size(); i++) {
            Course c = weeklySchedule.get(i);
            List<String> cDays = c.getClassTime().getDays();
            cDays.retainAll(courseDays);
            if (cDays.isEmpty())
                return false;
            String[] cTime = c.getClassTime().getTime().split("-");
            assert cTime.length == 2;
            String cStart = cTime[0];
            String cEnd = cTime[1];
            if (doTimesCollide(cStart, cEnd, courseStart, courseEnd, "kk:mm"))
                return true;
        }
        return false;
    }

    private boolean doDateTimesCollide (String interval1Start, String interval1End,
                                        String interval2Start, String interval2End, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime start1 = LocalDateTime.parse(interval1Start, dtf);
        LocalDateTime end1 = LocalDateTime.parse(interval1End, dtf);
        LocalDateTime start2 = LocalDateTime.parse(interval2Start, dtf);
        LocalDateTime end2 = LocalDateTime.parse(interval2End, dtf);
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    public boolean doesExamTimeCollide(Course course) {
        String courseExamStart = course.getExamTime().getStart();
        String courseExamEnd = course.getExamTime().getEnd();
        for (int i = 0; i < weeklySchedule.size(); i++) {
            Course c = weeklySchedule.get(i);
            String cExamStart = c.getExamTime().getStart();
            String cExamEnd = c.getExamTime().getEnd();
            if(doDateTimesCollide(courseExamStart, courseExamEnd, cExamStart, cExamEnd, "yyyy-MM-dd'T'kk:mm:ss"))
                return true;
        }
        return false;
    }
}
