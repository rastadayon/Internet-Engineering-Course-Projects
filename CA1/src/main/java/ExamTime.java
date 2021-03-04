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

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("end", end);
        jo.put("start", start);

        return jo;
    }
}
