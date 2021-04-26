package ir.ac.ut.ie.Bolbolestan05.controllers;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.WeeklySchedule;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.ReportCard;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.StudentInfo;
import ir.ac.ut.ie.Bolbolestan05.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/")
    public ResponseEntity<Object> getStudentInfo() throws IOException {
        System.out.println("in get student info");
        try{
            StudentInfo stdInfo = Bolbolestan.getInstance().getStudentInfo();
            System.out.println("all went fine in student info");
            return ResponseEntity.status(HttpStatus.OK).body(stdInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("no student is logged in.");
        }
    }



    @GetMapping("/reportCards") // change this
    public ResponseEntity getReportCards() throws IOException {
        System.out.println("getting report cards");
        try{
            ArrayList<ReportCard> reportCards = Bolbolestan.getInstance().getStudentReports();
            System.out.println("all went fine when retrieving report card");
            return ResponseEntity.status(HttpStatus.OK).body(reportCards);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("no student is logged in.");
        }
    }

    @GetMapping("/schedule")
    public ResponseEntity<Object> getSchedule(final HttpServletResponse response) throws IOException {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                WeeklySchedule schedule =  bolbolestan.getLoggedInStudent().getSubmittedOfferings();
                schedule.addToWeeklySchedule(bolbolestan.getOfferings().get(0));
                return ResponseEntity.status(HttpStatus.OK).body(schedule);
            } catch (Exception e) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            }
        }
        response.sendError(HttpStatus.BAD_REQUEST.value(), "No user logged in");
        return null;
    }

    @GetMapping("/searchKeyword")
    public ResponseEntity getSearchKeyword() {
        try {
            String searchKeyword = Bolbolestan.getInstance().getLoggedInStudentSearchedKeyword();
            return ResponseEntity.status(HttpStatus.OK).body(searchKeyword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("no student is logged in.");
        }
    }
}
