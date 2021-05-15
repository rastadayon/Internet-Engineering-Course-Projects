package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Course;

import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private int units;
    private String type;
    private ArrayList<String> prerequisites = new ArrayList<>();

    public Course(String code, String name, int units, String type, ArrayList<String> prerequisites) {
        this.code = code;
        this.name = name;
        this.units = units;
        this.prerequisites = prerequisites;
        this.type = type;
    }

    public Course(Course that) {
        this(that.getCourseCode(), that.getName(), that.getUnits(),
                that.type, new ArrayList<>(that.prerequisites));
    }

    public String getCourseCode() { return code; }

    public String getName() { return name; }

    public int getUnits() { return units; }

    public String getType() { return type; }

    public ArrayList<String> getPrerequisites() { return prerequisites; }

    public void setFarsiPrerequisites(ArrayList<String> farsiPrerequisites) { this.prerequisites = farsiPrerequisites; }
}
