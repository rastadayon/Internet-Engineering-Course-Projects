package ir.ac.ut.ie.Bolbolestan06.repository.Offering;

import ir.ac.ut.ie.Bolbolestan06.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OfferingMapper extends Mapper<Offering, Pair> implements IOfferingMapper {

    private static final String COLUMNS = " courseCode, classCode, instructor, capacity, signedUp ";
    private static final String TABLE_NAME = "OFFERINGS";

    public OfferingMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    instructor varchar(255) not null,\n" +
                            "    capacity int not null,\n" +
                            "    signedUp int not null,\n" +
                            "    primary key(courseCode, classCode),\n" +
                            "    foreign key (courseCode) references COURSES(code)\n" +
                            ");",
                    TABLE_NAME));
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public OfferingMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(Pair id) {
        return String.format("select * from %s where %s = %s and %s = %s;", TABLE_NAME,
                "courseCode", Utils.quoteWrapper(id.getArgs().get(0)),
                "classCode", Utils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected String getInsertStatement(Offering offering) {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (%s, %s, %s, %d, %d);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(offering.getCourseCode()), Utils.quoteWrapper(offering.getClassCode()), 
                Utils.quoteWrapper(offering.getInstructor()), offering.getCapacity(), offering.getSignedUp());
    }

    @Override
    protected String getDeleteStatement(Pair id) {
        return null;
    }

    @Override
    protected Offering convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Offering(
                rs.getString("classCode"),
                rs.getString("instructor"),
                rs.getInt("capacity"),
                rs.getInt("signedUp")
        );
    }

    @Override
    public List<Offering> getAll() throws SQLException {
        return null;
    }
}