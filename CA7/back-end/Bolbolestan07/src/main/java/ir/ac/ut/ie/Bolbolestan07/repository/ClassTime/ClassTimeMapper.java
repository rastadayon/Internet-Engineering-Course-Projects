package ir.ac.ut.ie.Bolbolestan07.repository.ClassTime;

import ir.ac.ut.ie.Bolbolestan07.utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.ClassTime;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ClassTimeMapper extends Mapper<ClassTime, Pair> implements IClassTimeMapper {

    private static final String COLUMNS = " courseCode, classCode, time, firstDay, secondDay ";
    private static final String TABLE_NAME = "CLASS_TIMES";

    public ClassTimeMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255),\n" +
                            "    classCode varchar(255),\n" +
                            "    time varchar(255) not null,\n" +
                            "    firstDay varchar(255) not null,\n" +
                            "    secondDay varchar(255),\n" +
                            "    primary key(courseCode, classCode),\n" +
                            "    foreign key (courseCode, classCode) references OFFERINGS(courseCode, classCode)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public ClassTimeMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement() {
        return String.format("select * from  where courseCode = ? and classCode = ?;", 
            TABLE_NAME);
    }

    @Override
    protected void fillFindStatement(PreparedStatement statement, Pair id) throws SQLException{
        statement.setString(1, id.getArgs().get(0));
        statement.setString(2, id.getArgs().get(1));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (?, ?, ?, ?, ?);", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, ClassTime classTime) throws SQLException{
        statement.setString(1, classTime.getCourseCode());
        statement.setString(2, classTime.getClassCode());
        statement.setString(3, classTime.getTime());
        statement.setString(4, classTime.getFirstDay());
        if (classTime.hasTowDays()) {
            statement.setString(5, classTime.getSecondDay());
        }
        else {
            statement.setString(5, "");
        }
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Pair id) throws SQLException{
        return;
    }

    @Override
    protected ClassTime convertResultSetToObject(ResultSet rs) throws SQLException {
        return new ClassTime(
                rs.getString("time"),
                rs.getString("firstDay"),
                rs.getString("secondDay")
        );
    }

    @Override
    public List<ClassTime> getAll() throws SQLException {
        List<ClassTime> result = new ArrayList<ClassTime>();
        String statement = "SELECT * FROM " + TABLE_NAME;
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }
}
