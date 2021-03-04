import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exeptions.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

public class WeeklyScheduleTest {
    private static WeeklySchedule weeklySchedule = new WeeklySchedule();
    private static Offering DA, DM, DS, DB, IE;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeClass
    public static void setup() {
        try {
            DS = new Offering("8101111", "DS",
                    "Ahmad Ahmadi", 3, new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Thursday"))), new ExamTime(
                    "2021-06-01T08:00:00", "2021-06-01T08:00:00"), 20,
                    new ArrayList<>(Arrays.asList("AP")));

            DB = new Offering("8102222", "DB",
                    "Ahmad Ahmadi", 3, new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Saturday"))), new ExamTime(
                    "2021-07-01T08:00:00", "2021-07-01T08:00:00"), 20,
                    new ArrayList<>(Arrays.asList("AP")));
            DA = new Offering("8103333", "DA",
                    "Ahmad Ahmadi", 3, new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Sunday"))), new ExamTime(
                    "2021-08-01T08:00:00", "2021-08-01T08:00:00"), 20,
                    new ArrayList<>(Arrays.asList("AP")));
            DM = new Offering("8104444", "DM",
                    "Ahmad Ahmadi", 3, new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Tuesday"))), new ExamTime(
                    "2021-09-01T08:00:00", "2021-09-01T08:00:00"), 20,
                    new ArrayList<>(Arrays.asList("AP")));

            IE = new Offering("8107892", "IE",
                    "Ahmad Ahmadi", 16, new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Friday"))), new ExamTime(
                    "2020-09-01T08:00:00", "2020-09-01T08:00:00"), 20,
                    new ArrayList<>(Arrays.asList("AP")));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @After
    public void teardown() {
        weeklySchedule.removeAllOfferings();
    }

    @Test(expected = BolbolestanMinimumUnitsError.class)
    public void testMinimumUnits() throws Exception{
        weeklySchedule.addToWeeklySchedule(DA);
        weeklySchedule.finalizeWeeklySchedule();
    }

    @Test(expected = BolbolestanMaximumUnitsError.class)
    public void testMaximumUnits() throws Exception{
        weeklySchedule.addToWeeklySchedule(IE);
        weeklySchedule.addToWeeklySchedule(DM);
        weeklySchedule.addToWeeklySchedule(DS);
        weeklySchedule.finalizeWeeklySchedule();
    }

    @Test(expected = BolbolestanClassTimeCollisionError.class)
    public void testClassTimeCollision() throws Exception{
        weeklySchedule.addToWeeklySchedule(DS);
        weeklySchedule.addToWeeklySchedule(new Offering("8100000", "AP",
                "Ahmad Ahmadi", 12, new ClassTime("14-15:30",
                new ArrayList<>(Arrays.asList("Thursday"))), new ExamTime(
                "2021-05-01T08:00:00", "2021-05-01T08:00:00"), 20,
                new ArrayList<>(Arrays.asList("AP"))));
        weeklySchedule.finalizeWeeklySchedule();
    }

    @Test(expected = BolbolestanExamTimeCollisionError.class)
    public void testExamTimeCollision() throws Exception{
        Offering EM = new Offering("8100001", "EM",
                "Ahmad Ahmadi", 3, new ClassTime("17-18:30",
                new ArrayList<>(Arrays.asList("Wednesday"))), new ExamTime(
                "2021-06-01T08:00:00", "2021-06-01T08:00:00"), 20,
                new ArrayList<>(Arrays.asList("AP")));
        Offering AI = new Offering("8100000", "AI",
                "Ahmad Ahmadi", 12, new ClassTime("17-18:30",
                new ArrayList<>(Arrays.asList("Wednesday"))), new ExamTime(
                "2021-06-01T08:00:00", "2021-06-01T08:00:00"), 20,
                new ArrayList<>(Arrays.asList("AP")));
        weeklySchedule.addToWeeklySchedule(EM);
        weeklySchedule.addToWeeklySchedule(AI);

        weeklySchedule.finalizeWeeklySchedule();
    }

    @Test(expected = BolbolestanCapacityError.class)
    public void testCapacity() throws Exception{
        weeklySchedule.addToWeeklySchedule(DS);
        weeklySchedule.addToWeeklySchedule(DM);
        weeklySchedule.addToWeeklySchedule(DB);
        weeklySchedule.addToWeeklySchedule(new Offering("8100000", "AI",
                "Ahmad Ahmadi", 3, new ClassTime("14-15:30",
                new ArrayList<>(Arrays.asList("Wednesday"))), new ExamTime(
                "2021-05-01T08:00:00", "2021-05-01T08:00:00"), 0,
                new ArrayList<>(Arrays.asList("AP"))));
        weeklySchedule.finalizeWeeklySchedule();
    }
}
