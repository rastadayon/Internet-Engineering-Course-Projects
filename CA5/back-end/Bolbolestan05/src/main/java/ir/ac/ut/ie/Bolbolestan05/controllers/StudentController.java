package ir.ac.ut.ie.Bolbolestan05.controllers;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no student is logged in.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody Login loginData,
            final HttpServletResponse response) throws IOException {
        System.out.println("in login");
        System.out.println("email is " + loginData.getEmail());
        try {
            AuthService.authUser(loginData);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no student is logged in.");
        }
    }
}
