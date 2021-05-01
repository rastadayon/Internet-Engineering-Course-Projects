package ir.ac.ut.ie.Bolbolestan05.repository.Offering;

import ir.ac.ut.ie.Bolbolestan05.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan05.repository.Mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OfferingMapper extends Mapper<Offering, String> implements IOfferingMapper {

    private static final String COLUMNS = " courseCode, classCode, name, instructor, capacity, signedUp ";
    private static final String TABLE_NAME = "OFFERINGS";

    public OfferingMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    courseCode text,\n" +
                            "    classCode text,\n" +
                            "    name varchar(255) not null,\n" +
                            "    instructor varchar(255) not null,\n" +
                            "    capacity int not null,\n" +
                            "    signedUp int not null,\n" +
                            "    primary key(courseCode, classCode)\n" +
                            "    foreign key (courseCode) references COURSES(code)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public OfferingMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(String id) {
        return null;
    }

    @Override
    protected String getInsertStatement(Offering offering) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s, %d, %d);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(offering.getCourseCode()), Utils.quoteWrapper(offering.getClassCode()),
                Utils.quoteWrapper(offering.getName()), Utils.quoteWrapper(offering.getInstructor()),
                offering.getCapacity(), offering.getSignedUp());
    }

    @Override
    protected String getDeleteStatement(String id) {
        return null;
    }

    @Override
    protected Offering convertResultSetToObject(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public List<Offering> getAll() throws SQLException {
        return null;
    }
}