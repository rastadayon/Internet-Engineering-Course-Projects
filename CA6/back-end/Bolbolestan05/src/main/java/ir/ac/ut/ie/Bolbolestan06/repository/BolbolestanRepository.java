package ir.ac.ut.ie.Bolbolestan06.repository;

import ir.ac.ut.ie.Bolbolestan06.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.ClassTime;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.ExamTime;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan06.repository.ClassTime.ClassTimeMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Course.CourseMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.ExamTime.ExamTimeMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Grade.GradeMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Offering.OfferingMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Prerequisite.PrerequisiteMapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Student.StudentMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            PrerequisiteMapper prerequisiteMapper = new PrerequisiteMapper(true);
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
        try {
            GradeMapper gradeMapper = new GradeMapper(true);
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

    //    ExamTime
    public void insertExamTime(ExamTime examTime) throws SQLException {
        ExamTimeMapper examTimeMapper = new ExamTimeMapper();
        examTimeMapper.insert(examTime);
    }

    //    ClassTime
    public void insertClassTime(ClassTime classTime) throws SQLException {
        ClassTimeMapper classTimeMapper = new ClassTimeMapper();
        classTimeMapper.insert(classTime);
    }

    //    Prerequisite
    public void insertPrerequisite(HashMap<String, ArrayList<String>> prerequisiteInfo) throws SQLException {
        PrerequisiteMapper prerequisiteMapper = new PrerequisiteMapper();
        prerequisiteMapper.insert(prerequisiteInfo);
    }

    //    Grade
    public void insertGrade(Grade grade) throws SQLException {
        GradeMapper gradeMapper = new GradeMapper();
        gradeMapper.insert(grade);
    }

    public Offering findOfferingById(String courseCode, String classCode) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(courseCode);
        args.add(classCode);
//        System.out.println("Offering we wanna get : " + courseCode + '-' + classCode);
        Offering offering = new OfferingMapper().find(new Pair(args));
//        System.out.println("offering found");
        Course course =  new CourseMapper().find(courseCode);
//        System.out.println("course found");
        ExamTime examTime = new ExamTimeMapper().find(new Pair(args));
//        System.out.println("exam time found");
        ClassTime classTime = new ClassTimeMapper().find(new Pair(args));
//        System.out.println("class time found");
        offering.setCourse(course);
        offering.setClassTime(classTime);
        offering.setExamTime(examTime);
//        System.out.println("IN THE DAMN DATABASE");
        offering.print();
        return offering;
    }

    public Student getStudent(String studentId) {
        try {
            Student student = new StudentMapper().find(studentId);
            return student;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Grade> getStudentGrades(String studentId) {
        try {
            ArrayList<Grade> grades = new GradeMapper().getStudentGrades(studentId);
            return grades;
        } catch (Exception e) {
            return null;
        }
    }

    public Course getCourseByCode(String code) {
        try {
            Course course = new CourseMapper().find(code);
            return course;
        } catch (Exception e) {
            return null;
        }
    }
}