package ir.ac.ut.ie.Bolbolestan05.controllers;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.CourseSelection;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.SearchData;
import ir.ac.ut.ie.Bolbolestan05.services.SearchService;
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
            @RequestBody SearchData searchData) throws IOException {
        try {
            List<Offering> searchResult = SearchService.searchKeyword(searchData);
            return ResponseEntity.status(HttpStatus.OK).body(searchResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        }
    }

    @GetMapping("/selections")
    public ResponseEntity getSelections() throws IOException {
        System.out.println("in get selections");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                CourseSelection courseSelection =  bolbolestan.getLoggedInStudent().getCourseSelection();
                for (int i = 0; i < 5; i++)
                    courseSelection.addToSelectedOfferings(bolbolestan.getOfferings().get(i));
                System.out.println("selections successful");
                return ResponseEntity.status(HttpStatus.OK).body(courseSelection);
            } catch (Exception e) {
                System.out.println("selection failed");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("student not found. invalid login");
    }
}
