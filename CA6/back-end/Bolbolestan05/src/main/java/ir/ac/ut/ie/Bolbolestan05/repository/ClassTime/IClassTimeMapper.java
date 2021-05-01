package ir.ac.ut.ie.Bolbolestan05.repository.ClassTime;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.ClassTime;
import ir.ac.ut.ie.Bolbolestan05.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IClassTimeMapper extends IMapper<ClassTime, String> {
    List<ClassTime> getAll() throws SQLException;
}