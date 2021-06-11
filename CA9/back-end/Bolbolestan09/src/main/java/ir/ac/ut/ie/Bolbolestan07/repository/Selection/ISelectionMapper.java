package ir.ac.ut.ie.Bolbolestan07.repository.Selection;

import ir.ac.ut.ie.Bolbolestan07.utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.Selection;
import ir.ac.ut.ie.Bolbolestan07.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface ISelectionMapper extends IMapper<Selection, Pair> {
    //List<Selection> getAll() throws SQLException;
}
