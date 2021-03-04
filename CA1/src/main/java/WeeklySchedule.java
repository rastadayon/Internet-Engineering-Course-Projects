import exeptions.*;
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
    private List<Offering> weeklySchedule = new ArrayList<Offering>();
    private String status = non_finalized;

    public List<Offering> getOfferings() {
        return weeklySchedule;
    }

    public String getStatus() {
        return status;
    }

    public JSONArray toJSON () {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Offering offering : weeklySchedule) {
            JSONObject obj = offering.exposeToJSON();
            obj.put("status", status);
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    public void addToWeeklySchedule(Offering offering) {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<Offering>();
        if (!weeklySchedule.contains(offering)) {
            weeklySchedule.add(offering);
            status = non_finalized;
        }
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        if (weeklySchedule == null)
            throw new BolbolestanOfferingNotFoundError();
        boolean successful = weeklySchedule.remove(offering);
        if (!successful){
            throw new BolbolestanOfferingNotFoundError();
        }
        status = non_finalized;
    }

    public void removeAllOfferings() {
        weeklySchedule = new ArrayList<Offering>();
    }

    private void checkCapacity() throws Exception {
        for (Offering offering : weeklySchedule) {
            if (offering.getCapacity() <= 0)
                throw new BolbolestanCapacityError(offering.getCode());
        }
    }

    private void checkValidNumberOfUnits() throws Exception {
        int units = 0;
        for (Offering offering : weeklySchedule) {
            units += offering.getUnits();
        }
        if (units > 20)
            throw new BolbolestanMaximumUnitsError();
        if (units < 12)
            throw new BolbolestanMinimumUnitsError();
    }

    private ArrayList<String> correctTimeFormat(String[] time) {
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

    private boolean doesClassTimeCollide(Offering o1, Offering o2) {
        List<String> o1Days = o1.getClassTime().getDays();
        List<String> o2Days = o2.getClassTime().getDays();
        o1Days.retainAll(o2Days);
        if (o1Days.isEmpty())
            return false;
        ArrayList<String> o1Time = correctTimeFormat(o1.getClassTime().getTime().split("-"));
        ArrayList<String> o2Time = correctTimeFormat(o2.getClassTime().getTime().split("-"));
        assert o1Time.size() == 2 && o2Time.size() == 2;
        String o1start = o1Time.get(0);
        String o1end = o1Time.get(1);
        String o2start = o2Time.get(0);
        String o2end = o2Time.get(1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
//        Time
        LocalTime start1 = LocalTime.parse(o1start, dtf);
        LocalTime end1 = LocalTime.parse(o1end, dtf);
        LocalTime start2 = LocalTime.parse(o2start, dtf);
        LocalTime end2 = LocalTime.parse(o2end, dtf);
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    private void checkClassTimeCollision() throws Exception {
        for (int i=0; i < weeklySchedule.size(); i++) {
            for (int j=i+1; j < weeklySchedule.size(); j++){
                Offering o1 = weeklySchedule.get(i);
                Offering o2 =  weeklySchedule.get(j);
                if (doesClassTimeCollide(o1,o2))
                    throw new BolbolestanClassTimeCollisionError(o1.getCode(), o2.getCode());
            }
        }
    }

    private boolean doesExamTimeCollide(Offering o1, Offering o2) {
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
                Offering o1 = weeklySchedule.get(i);
                Offering o2 =  weeklySchedule.get(j);
                if (doesExamTimeCollide(o1,o2))
                    throw new BolbolestanExamTimeCollisionError(o1.getCode(), o2.getCode());
            }
        }
    }

    public void finalizeWeeklySchedule() throws Exception {
        if (weeklySchedule == null)
            weeklySchedule = new ArrayList<>();
        checkCapacity();
        checkValidNumberOfUnits();
        checkExamTimeCollision();
        checkClassTimeCollision();
        status = finalized;
    }
}
