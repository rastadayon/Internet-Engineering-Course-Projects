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

    @PostMapping("/remove")
    public ResponseEntity removeCourse(
            @RequestParam String courseCode,
            @RequestParam String classCode) throws IOException {
        System.out.println("in remove course");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                String loggedInStudentId = bolbolestan.getLoggedInId();
                bolbolestan.removeFromWeeklySchedule(loggedInStudentId, courseCode, classCode);
                System.out.println("remove successful");
                return ResponseEntity.status(HttpStatus.OK).body("OK");
            } catch (Exception e) {
                System.out.println("remove failed");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("student not found. invalid login");
    }

    @PostMapping("/add")
    public ResponseEntity addCourse(
            @RequestParam String courseCode,
            @RequestParam String classCode) throws IOException {
        System.out.println("in add course");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                String loggedInStudentId = bolbolestan.getLoggedInId();
                bolbolestan.addCourseToStudent(loggedInStudentId, courseCode, classCode);
                System.out.println("add successful");
                return ResponseEntity.status(HttpStatus.OK).body("OK");
            } catch (Exception e) {
                System.out.println("add failed");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("student not found. invalid login");
    }

    @PostMapping("/wait")
    public ResponseEntity waitForCourse(
            @RequestParam String courseCode,
            @RequestParam String classCode) throws IOException {
        System.out.println("in wait for course");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                String loggedInStudentId = bolbolestan.getLoggedInId();
                if (bolbolestan.addCourseToWaitingList(loggedInStudentId, courseCode,
                            classCode)) {
                    System.out.println("wait successful");
                    return ResponseEntity.status(HttpStatus.OK).body("OK");
                } else {
                    System.out.println("wait failed");
                    String errors = bolbolestan.getWaitingErrors();
                    System.out.println(errors);
                    return ResponseEntity.status(HttpStatus.OK).body(errors);
                }
            } catch (Exception e) {
                System.out.println("wait failed");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("student not found. invalid login");
    }

    @PostMapping("/reset")
    public ResponseEntity resetSelections() throws IOException {
        System.out.println("in reset selections");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                String loggedInStudentId = bolbolestan.getLoggedInId();
                bolbolestan.resetSelectedOfferings(loggedInStudentId);
                System.out.println("reset successful");
                return ResponseEntity.status(HttpStatus.OK).body("OK");
            } catch (Exception e) {
                System.out.println("reset failed");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("student not found. invalid login");
    }

    @PostMapping("/finalize")
    public ResponseEntity finalizeSelections() throws IOException {
        System.out.println("in finalize selections");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                String loggedInStudentId = bolbolestan.getLoggedInId();
                if (bolbolestan.finalizeSchedule(loggedInStudentId)) {
                    System.out.println("finalize successful");
                    return ResponseEntity.status(HttpStatus.OK).body("OK");
                } else {
                    System.out.println("finalize failed");
                    String errors = bolbolestan.getSubmissionErrors();
                    System.out.println(errors);
                    return ResponseEntity.status(HttpStatus.OK).body(errors);
                }
            } catch (Exception e) {
                System.out.println("finalize failed");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("student not found. invalid login");
    }

    @GetMapping("/selections")
    public ResponseEntity getSelections() throws IOException {
        System.out.println("in get selections");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                CourseSelection courseSelection =  bolbolestan.getLoggedInStudent().getCourseSelection();
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
