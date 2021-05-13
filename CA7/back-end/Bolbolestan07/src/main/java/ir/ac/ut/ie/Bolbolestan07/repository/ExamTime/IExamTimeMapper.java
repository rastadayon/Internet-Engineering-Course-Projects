package ir.ac.ut.ie.Bolbolestan07.repository.ExamTime;

import ir.ac.ut.ie.Bolbolestan07.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.ExamTime;
import ir.ac.ut.ie.Bolbolestan07.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IExamTimeMapper extends IMapper<ExamTime, Pair> {
    List<ExamTime> getAll() throws SQLException;
}