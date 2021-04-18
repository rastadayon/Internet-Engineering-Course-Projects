package ir.ac.ut.ie.Bolbolestan05.controllers.models;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            response.sendError(HttpStatus.OK.value(), "kir");
            return null;
        }
    }

    @PostMapping("/login")
    public void login(
            @RequestBody Integer email, final HttpServletResponse response) {
        System.out.println("in login");
        System.out.println("email is " + email);
    }

}
