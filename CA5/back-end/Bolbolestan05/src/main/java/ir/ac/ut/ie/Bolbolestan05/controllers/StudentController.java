package ir.ac.ut.ie.Bolbolestan05.controllers;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan05.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("")
    public Student getStudent(final HttpServletResponse response) throws IOException {
        System.out.println("in get student");
        try{
            return Bolbolestan.getInstance().getLoggedInStudent();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/login")
    public void login(
            @RequestBody Login loginData,
            final HttpServletResponse response) throws IOException {
        System.out.println("in login");
        System.out.println("email is " + loginData.getStdId());
        try {
            AuthService.authUser(loginData);
            response.sendError(HttpStatus.OK.value());
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    @GetMapping("/grades")
    public ArrayList<Grade> getGrades(final HttpServletResponse response) throws IOException {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                ArrayList<Grade> grades =  bolbolestan.getLoggedInStudent().getGrades();
                response.sendError(HttpStatus.OK.value());
                return grades;
            } catch (Exception e) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            }
        }
        response.sendError(HttpStatus.BAD_REQUEST.value(), "No user logged in");
        return null;
    }
}
