package ir.ac.ut.ie.Bolbolestan07.repository.Course;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan07.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface ICourseMapper extends IMapper<Course, String> {
    List<Course> getAll() throws SQLException;
}