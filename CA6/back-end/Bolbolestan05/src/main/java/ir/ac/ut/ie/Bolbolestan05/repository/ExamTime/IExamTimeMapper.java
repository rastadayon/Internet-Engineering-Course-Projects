package ir.ac.ut.ie.Bolbolestan05.repository.ExamTime;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.ExamTime;
import ir.ac.ut.ie.Bolbolestan05.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IExamTimeMapper extends IMapper<ExamTime, String> {
    List<ExamTime> getAll() throws SQLException;
}