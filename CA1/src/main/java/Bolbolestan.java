import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import exeptions.BolbolestanOfferingNotFoundError;
import exeptions.BolbolestanRulesViolationError;
import exeptions.BolbolestanStudentNotFoundError;

import java.util.*;

public class Bolbolestan {
    private Map<String, Student> students = new HashMap<>();
    private Map<String, Offering> offerings = new HashMap<>();

    private Offering getOfferingByCode(List<Offering> weeklySchedule, String code) {
        if (weeklySchedule == null)
            return null;
        for (Offering offering : weeklySchedule) {
            if (code.equals(offering.getCode()))
                return offering;
        }
        return null;
    }

    public String addOffering(Offering offering) throws Exception {
        if (offerings.containsKey(offering.getCode()))
            throw new BolbolestanRulesViolationError(String.format("Offering with the code %s already exists.", offering.getCode()));
        offerings.put(offering.getCode(), offering);
        return "Offering successfully added.";
    }

    public String addStudent(Student student) throws Exception {
        if (students.containsKey(student.getId()))
            throw new BolbolestanRulesViolationError(String.format("Student with id %s already exists.", student.getId()));
        students.put(student.getId(), student);
        return "Student successfully added.";
    }

    public List<Offering> getOfferings(String studentId, Gson gson) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        List<Offering> offeringList = new ArrayList<Offering>(offerings.values());
        Collections.sort(offeringList);
        return offeringList;
    }

    public Offering getOffering(String studentId, String courseCode, Gson gson) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Offering offering = offerings.get(courseCode);

        if (offering == null)
            throw new BolbolestanOfferingNotFoundError();

        return offering;
    }

    public String addToWeeklySchedule(String studentId, String offeringCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        if (!offerings.containsKey(offeringCode))
            throw new BolbolestanOfferingNotFoundError();
        Student student = students.get(studentId);
        Offering offering = offerings.get(offeringCode);
        student.addToWeeklySchedule(offering);
        return "Offering successfully added to weekly schedule.";
    }

    public String removeFromWeeklySchedule(String studentId, String offeringCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        if (!offerings.containsKey(offeringCode))
            throw new BolbolestanOfferingNotFoundError();
        Student student = students.get(studentId);
        Offering offering = offerings.get(offeringCode);
        student.removeFromWeeklySchedule(offering);
        return "Offering successfully removed from weekly schedule.";
    }

    public WeeklySchedule handleGetWeeklySchedule(String studentId) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        return student.getWeeklySchedule();
    }

    public String handleFinalize(String studentId) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        student.getWeeklySchedule().finalizeWeeklySchedule();
        return "Weekly schedule successfully finalized.";
    }
}
