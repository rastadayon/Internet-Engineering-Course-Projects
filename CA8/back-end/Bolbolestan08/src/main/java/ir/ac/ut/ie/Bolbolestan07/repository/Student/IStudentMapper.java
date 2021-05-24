package ir.ac.ut.ie.Bolbolestan07.repository.Student;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IStudentMapper extends IMapper<Student, String> {
    List<Student> getAll() throws SQLException;
}