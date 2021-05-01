package ir.ac.ut.ie.Bolbolestan06;

import ir.ac.ut.ie.Bolbolestan06.schedulers.MinJob;
import ir.ac.ut.ie.Bolbolestan06.services.EducationSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Bolbolestan06Application {

	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		try {
			EducationSystem.getInstance().importDataFromWeb();
			// Start scheduler for wait list
			new MinJob().run();
			scheduler.scheduleAtFixedRate(new MinJob(), 0, 1, TimeUnit.MINUTES);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("run mere");
		SpringApplication.run(Bolbolestan06Application.class, args);
	}
}