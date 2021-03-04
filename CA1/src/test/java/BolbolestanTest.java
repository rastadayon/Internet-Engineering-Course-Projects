import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exeptions.BolbolestanStudentNotFoundError;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class BolbolestanTest {
    private static final File weeklyScheduleFile = new File(
            "src/test/resources/weeklySchedule.json");
    private static Bolbolestan bolbolestan = new Bolbolestan();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeClass
    public static void setup() {
        try {
            bolbolestan.addStudent(new Student("810196675", "Ghazal",
                    "1396"));
            bolbolestan.addOffering(new Offering("8101111", "DS",
                    "Ahmad Ahmadi", 3, new ClassTime("14-15:30",
                    new ArrayList<>(Arrays.asList("Sunday"))), new ExamTime(
                    "2021-09-01T08:00:00", "2021-09-01T08:00:00"), 19,
                    new ArrayList<>(Arrays.asList("AP"))));
            bolbolestan.addToWeeklySchedule("810196675", "8101111");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @AfterClass
    public static void teardown() {
    }

    @Test(expected = BolbolestanStudentNotFoundError.class)
    public void testStudentNotFound() throws Exception{
        bolbolestan.handleGetWeeklySchedule("810696288");
    }

    @Test
    public void testGetWeeklySchedule() throws Exception{
        String expected = FileUtils.readFileToString(weeklyScheduleFile);
        String output = (bolbolestan.handleGetWeeklySchedule("810196675").toJSON()).toString(2);
        Assert.assertEquals(expected, output);
    }
}
