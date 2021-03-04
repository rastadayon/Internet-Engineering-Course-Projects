import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassTime {
    private String time;

    private List<String> days = new ArrayList<String>();
    public ClassTime(String time, List<String> days) {
        this.time = time;
        this.days = days;
    }

    public String getTime() { return time; }

    public List<String> getDays() { return days; }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("days", days);
        jo.put("time", time);

        return jo;
    }
}
