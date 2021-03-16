package Bolbolestan.Course;

import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private int units;
    private ArrayList<String> prerequisites = new ArrayList<>();

    public Course(String code, String name, int units, ArrayList<String> prerequisites) {
        this.code = code;
        this.name = name;
        this.units = units;
        this.prerequisites = prerequisites;
    }

    public String getCourseCode() { return code; }
    public String getName() { return name; }
    public int getUnits() { return units; }

    public ArrayList<String> getPrerequisites() { return prerequisites; }
}
