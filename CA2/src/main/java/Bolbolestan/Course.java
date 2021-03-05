package Bolbolestan;

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

    public void reduceCapacity() { seatsTaken += 1;}

    public int getSeatsTaken() { return seatsTaken; }

    public ClassTime getClassTime() { return classTime; }

    public String getClassDayString() {
        List<String> days = classTime.getDays();
        String daysString = "";
        for (int i = 0; i < days.size(); i++) {
            if (i > 0)
                daysString += "|";
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

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }
}
