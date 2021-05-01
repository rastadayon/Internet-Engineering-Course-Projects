package ir.ac.ut.ie.Bolbolestan06.repository.ClassTime;

import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.ClassTime;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ClassTimeMapper extends Mapper<ClassTime, String> implements IClassTimeMapper {

    private static final String COLUMNS = " time ";
    private static final String TABLE_NAME = "CLASS_TIMES";

    public ClassTimeMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    time text primary key\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public ClassTimeMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(String id) {
        return null;
    }

    @Override
    protected String getInsertStatement(ClassTime classTime) {
        return String.format("INSERT INTO %s ( %s ) values (%s);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(classTime.getTime()));
    }

    @Override
    protected String getDeleteStatement(String id) {
        return null;
    }

    @Override
    protected ClassTime convertResultSetToObject(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public List<ClassTime> getAll() throws SQLException {
        return null;
    }
}