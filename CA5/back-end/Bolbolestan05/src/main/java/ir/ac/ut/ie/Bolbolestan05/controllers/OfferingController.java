package ir.ac.ut.ie.Bolbolestan05.controllers;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan05.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/offering")
public class OfferingController {

    @GetMapping("/{courseCode}/{classCode}")
    public Offering getOffering(
            @PathVariable String courseCode,
            @PathVariable String classCode,
            final HttpServletResponse response) throws IOException {
        System.out.println("in get student");
        try{
            Offering offering = Bolbolestan.getInstance().getOffering(courseCode, classCode);
            response.sendError(HttpStatus.OK.value());
            return offering;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchForCourses(
            @RequestParam String searchKey) throws IOException {
        System.out.println("searching");
        System.out.println("search keyword is : " + searchKey);
        try {
            Bolbolestan.getInstance().searchForCourses(searchKey);
            List<Offering> searchResult = Bolbolestan.getInstance().getSearchedOfferings();
            return ResponseEntity.status(HttpStatus.OK).body(searchResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        }
    }
}
