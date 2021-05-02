package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering;

import org.json.JSONObject;

public class ExamTime {
    private String start;
    private String end;
    private String courseCode;
    private String classCode;

    public ExamTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public ExamTime(String courseCode, String classCode, String start, String end) {
        this.courseCode = courseCode;
        this.classCode = classCode;
        this.start = start;
        this.end = end;
    }

    public String getStart() { return start; }

    public String getEnd() { return end; }

    public String getCourseCode() { return courseCode; }

    public String getClassCode() { return classCode; }

    public void setOffering(String courseCode, String classCode) {
        this.courseCode = courseCode;
        this.classCode = classCode;
    }

    public void print() {
        System.out.println("exam time : ");
        System.out.println(String.format("\tstart : %s", start));
        System.out.println(String.format("\tend : %s", end));
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("end", end);
        jo.put("start", start);

        return jo;
    }
}
