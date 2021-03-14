package Bolbolestan.Course;

import java.util.ArrayList;

public class Course {
    private String courseCode;
    private String name;
    private int units;
    private ArrayList<String> prerequisites = new ArrayList<>();

    public Course(String courseCode, String name, int units, ArrayList<String> prerequisites) {
        this.courseCode = courseCode;
        this.name = name;
        this.units = units;
        this.prerequisites = prerequisites;
    }

    public String getCourseCode() { return courseCode; }

    public String getName() { return name; }

    public int getUnits() { return units; }

    public ArrayList<String> getPrerequisites() { return prerequisites; }
}
