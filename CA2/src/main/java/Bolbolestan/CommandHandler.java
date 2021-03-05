package Bolbolestan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CommandHandler {
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final int COMMAND = 0;
    private static final int DATA = 1;
    private static final int MAX_ARGUMENT_COUNT = 2;
    private static Gson gson = new GsonBuilder().create();
    private static Bolbolestan bolbolestan = new Bolbolestan();


    public static void main(String[] args) throws IOException {
        run();
    }

    private static void printOutput(boolean successful, Object data) {
        JSONObject jsonObject = new JSONObject();
        if (successful)
            jsonObject.put("data", data);
        else
            jsonObject.put("error", data);

        jsonObject.put("success", successful);
        System.out.println(jsonObject.toString(2));
    }

    private static void printOutput(boolean successful, List<Course> data, String status) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        if (successful) {
            for (Course course : data) {
                JSONObject obj = course.exposeToJSON();
                obj.put("status", status);
                jsonArray.put(obj);
            }
            jsonObject.put("weeklySchedule", jsonArray);
        }
        else
            jsonObject.put("error", data);

        jsonObject.put("success", successful);
        System.out.println(jsonObject.toString(2));
    }

    private static void printOutput(boolean successful, Course data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", successful);
        if (successful)
            jsonObject.put("data", data.toJSON());
        else
            jsonObject.put("error", data);
        System.out.println(jsonObject.toString(2));
    }

    public static void run() throws IOException {
        InputStream input = System.in;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = buffer.readLine()) != null) {
            try {
                String[] info = line.split(SPACE, MAX_ARGUMENT_COUNT);
                String command = info[COMMAND];
                String data = EMPTY;
                if (info.length == MAX_ARGUMENT_COUNT)
                    data = info[DATA];

                if (command.equals("addOffering"))
                    handleAddOffering(data);

                else if (command.equals("addStudent"))
                    handleAddStudent(data);

                else if (command.equals("getOfferings")) // returns null as data if student has no offerings
                    handleGetOfferings(data);

                else if (command.equals("getOffering")) // same as the before command
                    handleGetOffering(data);

//                else if (command.equals("addToWeeklySchedule"))
//                    handleAddToWeeklySchedule(data);

                else if (command.equals("removeFromWeeklySchedule"))
                    handleRemoveFromWeeklySchedule(data);

                else if (command.equals("getWeeklySchedule"))
                    handleGetWeeklySchedule(data);

//                else if (command.equals("finalize"))
//                    handleFinalize(data);

            }
            catch (Exception e){
                String error = e.getMessage();
                printOutput(false, error);
            }
        }
    }

    public static void handleAddOffering(String data) throws Exception {
        Course course = gson.fromJson(data, Course.class);
        printOutput(true, bolbolestan.addCourse(course));
    }

    public static void handleAddStudent(String data) throws Exception {
        Student student = gson.fromJson(data, Student.class);
        printOutput(true, bolbolestan.addStudent(student));
    }

    public static void handleGetOfferings(String data) throws Exception {
        String studentId = getValueByKey(data,"StudentId");
        printOutput(true, bolbolestan.getOfferings(studentId, gson));
    }

    public static void handleGetOffering(String data) throws Exception {
        String studentId = getValueByKey(data,"StudentId");
        String offeringCode = getValueByKey(data,"code");
        printOutput(true, bolbolestan.getOffering(studentId, offeringCode, gson));
    }

//    public static void handleAddToWeeklySchedule(String data) throws Exception{
//        String studentId = getValueByKey(data,"StudentId");
//        String offeringCode = getValueByKey(data,"code");
//        String message = bolbolestan.addToWeeklySchedule(studentId, offeringCode);
//        printOutput(true, message);
//    }

    public static void handleRemoveFromWeeklySchedule(String data) throws Exception {
        String studentId = getValueByKey(data,"StudentId");
        String offeringCode = getValueByKey(data,"code");
        String message = bolbolestan.removeFromWeeklySchedule(studentId, offeringCode);
        printOutput(true, message);
    }

    public static void handleGetWeeklySchedule(String data) throws Exception {
        String studentId = getValueByKey(data,"StudentId");
        WeeklySchedule weeklySchedule = bolbolestan.handleGetWeeklySchedule(studentId);
        printOutput(true, weeklySchedule.getOfferings(), weeklySchedule.getStatus());
    }

//    public static void handleFinalize(String data) throws Exception {
//        String studentId = getValueByKey(data,"StudentId");
//        String message = bolbolestan.handleFinalize(studentId);
//        printOutput(true, message);
//    }

    private static String getValueByKey(String data, String key) {
        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        String value = jsonObject.get(key).getAsString();
        return value;
    }
}
