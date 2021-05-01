package ir.ac.ut.ie.Bolbolestan06.repository;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan06.repository.ClassTime.ClassTimeMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Course.CourseMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.ExamTime.ExamTimeMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Offering.OfferingMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Student.StudentMapper;

import java.sql.SQLException;

public class BolbolestanRepository {
    private static BolbolestanRepository instance;

    private BolbolestanRepository() {
        createAllTables();
    }

    public static BolbolestanRepository getInstance() {
        if (instance == null)
            instance = new BolbolestanRepository();
        return instance;
    }

    private void createAllTables() {
        try {
            StudentMapper studentMapper = new StudentMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CourseMapper courseMapper = new CourseMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            OfferingMapper offeringMapper = new OfferingMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ExamTimeMapper examTimeMapper = new ExamTimeMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ClassTimeMapper classTimeMapper = new ClassTimeMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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