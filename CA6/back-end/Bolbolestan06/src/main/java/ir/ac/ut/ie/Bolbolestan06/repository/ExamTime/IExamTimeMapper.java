package ir.ac.ut.ie.Bolbolestan06.repository.ExamTime;

import ir.ac.ut.ie.Bolbolestan06.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.ExamTime;
import ir.ac.ut.ie.Bolbolestan06.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IExamTimeMapper extends IMapper<ExamTime, Pair> {
    List<ExamTime> getAll() throws SQLException;
}