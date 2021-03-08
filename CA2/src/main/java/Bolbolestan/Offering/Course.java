package Bolbolestan.Offering;


import Bolbolestan.Utils;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Course implements Comparable<Course> {
    private final String code;
    private final String classCode;
    private final String name;
    private final int units;
    private final String type;
    private final String instructor;
    private final int capacity;
    private ArrayList<String> prerequisites;
    private ClassTime classTime;
    private ExamTime examTime;
    private int seatsTaken;
    public Course(String code, String classCode, String name, int units,
                  String type, String instructor, int capacity, ArrayList<String> prerequisites,
                  ClassTime classTime, ExamTime examTime) {
        this.code = code;
        this.classCode = classCode;
        this.name = name;
        this.units = units;
        this.type = type;
        this.instructor = instructor;
        this.capacity = capacity;
        this.prerequisites = prerequisites;
        this.classTime = classTime;
        this.examTime = examTime;
        this.seatsTaken = 0;
    }

    public String getType() { return type; }

    public ExamTime getExamTime() { return examTime; }

    public String getClassCode() { return classCode; }

    public int getUnits() { return units; }

    public int getCapacity() { return capacity; }

    public String getCode() {
        return this.code;
    }

    public void reduceCapacity() {
        System.out.println("reducing capacity"); seatsTaken += 1; }

    public int getSeatsTaken() { return seatsTaken; }

    public ClassTime getClassTime() { return classTime; }

    public boolean hasCapacity() { return capacity - seatsTaken > 0; }

    public ArrayList<String> getPrerequisites() { return prerequisites; }

    public String getClassDayString(String delimiter) {
        List<String> days = classTime.getDays();
        String daysString = "";
        for (int i = 0; i < days.size(); i++) {
            if (i > 0)
                daysString += delimiter;
            daysString += days.get(i);
        }
        return daysString;
    }

    public String getPrerequisitesString() {
        String prerequisitesString = "";
        for (int i = 0; i < prerequisites.size(); i++) {
            if (i > 0)
                prerequisitesString += "|";
            prerequisitesString += prerequisites.get(i);
        }
        return prerequisitesString;
    }

    public void print() {
        System.out.println(String.format("course code : %s-%s", code, classCode));
        System.out.println(String.format("course name : %s", name));
        System.out.println(String.format("units : %d", units));
        System.out.println(String.format("type : %s", type));
        System.out.println(String.format("instructor : %s", instructor));
        System.out.println(String.format("capacity : %d", capacity));
        System.out.print("prerequisites : [ ");
        for (int i = 0; i < prerequisites.size(); i++) {
            if (i != 0)
                System.out.print(", ");
            System.out.print(prerequisites.get(i));
        }
        System.out.println(" ]");
        classTime.print();
        examTime.print();
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
       jo.put("prerequisites", prerequisites);
       jo.put("capacity", capacity);
       jo.put("examTime", examTime.toJSON());
       jo.put("classTime", classTime.toJSON());
       jo.put("units", units);
       jo.put("Instructor", instructor);
       jo.put("name", name);
       jo.put("code", code);

        return jo;
    }

    public JSONObject exposeToJSON() {
        JSONObject jo = new JSONObject();
        jo.put("examTime", examTime.toJSON());
        jo.put("classTime", classTime.toJSON());
        jo.put("name", name);
        jo.put("code", code);

        return jo;
    }

    public String getName() {return name;}

    public boolean hasClassTime(String day, String startTime) {
        return classTime.hasTime(day, startTime);
    }

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean doesClassTimeCollide (Course c) {
        List<String> cDays = c.getClassTime().getDays();
        cDays.retainAll(classTime.getDays());
        if (cDays.isEmpty())
            return false;
        Utils utils = Utils.getInstance();
        ArrayList<String> courseTime = utils.correctTimeFormat(this.getClassTime().getTime().split("-"));
        ArrayList<String> cTime = utils.correctTimeFormat(c.getClassTime().getTime().split("-"));
        assert courseTime.size() == 2 && cTime.size() == 2;
        String courseStart = courseTime.get(0);
        String courseEnd = courseTime.get(1);
        String cStart = cTime.get(0);
        String cEnd = cTime.get(1);
        return utils.doTimesCollide(cStart, cEnd, courseStart, courseEnd, "kk:mm");
    }

    public boolean doesExamTimeCollide (Course c) {
        String cStart = c.getExamTime().getStart();
        String cEnd = c.getExamTime().getEnd();
        return Utils.getInstance().doDateTimesCollide(cStart, cEnd, this.getExamTime().getStart(),
                this.getExamTime().getEnd(), "yyyy-MM-dd'T'kk:mm:ss");
    }
}
