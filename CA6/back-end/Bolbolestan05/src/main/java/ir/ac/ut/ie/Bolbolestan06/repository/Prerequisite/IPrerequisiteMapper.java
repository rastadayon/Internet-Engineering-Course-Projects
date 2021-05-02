package ir.ac.ut.ie.Bolbolestan06.repository.Prerequisite;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan06.repository.IMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IPrerequisiteMapper extends IMapper<HashMap<String, ArrayList<String>>, String> {
}
