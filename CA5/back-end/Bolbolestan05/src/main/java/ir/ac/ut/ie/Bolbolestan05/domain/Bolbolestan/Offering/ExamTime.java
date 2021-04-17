package ir.ac.ut.ie.Bolbolestan05.domain.Bolbolestan.Offering;

import org.json.JSONObject;

public class ExamTime {
    private String start;
    private String end;

    public ExamTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() { return start; }

    public String getEnd() { return end; }

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
