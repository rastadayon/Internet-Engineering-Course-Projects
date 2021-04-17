package ir.ac.ut.ie.Bolbolestan05.domain.Bolbolestan.Course;

import ir.ac.ut.ie.Bolbolestan05.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.domain.Bolbolestan.exeptions.BolbolestanCourseNotFoundError;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses = new ArrayList<>();

    public Course getCourseByCode(String courseCode) throws Exception {
        for (Course course : courses)
            if (course.getCourseCode().equals(courseCode))
                return course;
        throw new BolbolestanCourseNotFoundError();
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
