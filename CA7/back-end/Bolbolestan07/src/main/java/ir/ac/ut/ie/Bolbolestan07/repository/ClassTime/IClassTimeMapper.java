package ir.ac.ut.ie.Bolbolestan07.repository.ClassTime;

import ir.ac.ut.ie.Bolbolestan07.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.ClassTime;
import ir.ac.ut.ie.Bolbolestan07.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IClassTimeMapper extends IMapper<ClassTime, Pair> {
    List<ClassTime> getAll() throws SQLException;
}