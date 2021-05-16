package ir.ac.ut.ie.Bolbolestan06.repository.Offering;

import ir.ac.ut.ie.Bolbolestan06.utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IOfferingMapper extends IMapper<Offering, Pair> {
    List<Offering> getAll() throws SQLException;
}
