package ir.ac.ut.ie.Bolbolestan05.repository;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan05.repository.Course.CourseMapper;
import ir.ac.ut.ie.Bolbolestan05.repository.Offering.OfferingMapper;
import ir.ac.ut.ie.Bolbolestan05.repository.Student.StudentMapper;

import java.sql.SQLException;

public class BolbolestanRepository {
    private static BolbolestanRepository instance;

    private BolbolestanRepository() {
    }

    public static BolbolestanRepository getInstance() {
        if (instance == null)
            instance = new BolbolestanRepository();
        return instance;
    }

    //    Student
    public void insertStudent(Student student) throws SQLException {
        StudentMapper studentMapper = new StudentMapper();
        studentMapper.insert(student);
    }

    //    Course
    public void insertCourse(Course course) throws SQLException {
        CourseMapper courseMapper = new CourseMapper();
        courseMapper.insert(course);
    }

    //    Offering
    public void insertOffering(Offering offering) throws SQLException {
        OfferingMapper offeringMapper = new OfferingMapper();
        offeringMapper.insert(offering);
    }
}