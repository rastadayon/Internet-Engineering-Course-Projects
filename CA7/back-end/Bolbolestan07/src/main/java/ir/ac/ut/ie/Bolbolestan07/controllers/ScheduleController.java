package ir.ac.ut.ie.Bolbolestan07.controllers;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.WeeklySchedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @GetMapping("/")
    public ResponseEntity<Object> getSchedule(final HttpServletResponse response) throws IOException {
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        if (bolbolestan.isAnybodyLoggedIn()) {
            try {
                //WeeklySchedule schedule =  bolbolestan.getLoggedInStudent().getSubmittedOfferings();
                WeeklySchedule schedule =  bolbolestan.getLoggedInStudentSchedule();
                return ResponseEntity.status(HttpStatus.OK).body(schedule);
            } catch (Exception e) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            }
        }
        response.sendError(HttpStatus.BAD_REQUEST.value(), "No user logged in");
        return null;
    }
}
