package ir.ac.ut.ie.Bolbolestan05.repository.Offering;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IOfferingMapper extends IMapper<Offering, String> {
    List<Offering> getAll() throws SQLException;
}