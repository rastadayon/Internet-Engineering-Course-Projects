package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Course;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions.BolbolestanCourseNotFoundError;
import ir.ac.ut.ie.Bolbolestan06.repository.BolbolestanRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses = new ArrayList<>();

    public Course getCourseByCode(String courseCode) throws Exception {
        try {
            return BolbolestanRepository.getInstance().findCourseByCode(courseCode);
        }catch (SQLException e){
            throw new BolbolestanCourseNotFoundError();
        }
    }

    public boolean doesCourseExist(String courseCode) {
        if (courses == null)
            return false;
        for (Course course : courses)
            if (course.getCourseCode().equals(courseCode))
                return true;
        return false;
    }

    public void addCourse(Offering offering) {
        String courseCode = offering.getCourseCode();
        if(!doesCourseExist(courseCode)) {
            if (courses == null)
                courses = new ArrayList<>();
            courses.add(offering.getCourse());
        }
    }
}
