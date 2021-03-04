import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Offering implements Comparable<Offering> {
    private String code;
    private String name;
    @SerializedName ("Instructor") private String instructor;
    private int units;

    private ClassTime classTime;

    private ExamTime examTime;
    private int capacity;

    private ArrayList<String> prerequisites;

    public Offering(String code, String name, String instructor,
                    int units, ClassTime classTime, ExamTime examTime,
                    int capacity, ArrayList<String> prerequisites) {
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.units = units;
        this.classTime = classTime;
        this.examTime = examTime;
        this.capacity = capacity;
        this.prerequisites = prerequisites;
    }

    public ExamTime getExamTime() { return examTime; }

    public int getUnits() { return units; }

    public int getCapacity() { return capacity; }

    public String getCode() {
        return this.code;
    }

    public void reduceCapacity() { capacity -= 1;}

    public ClassTime getClassTime() { return classTime; }

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
    public int compareTo(Offering o) {
        return this.getName().compareTo(o.getName());
    }
}
