package ir.ac.ut.ie.Bolbolestan06.repository.ClassTime;

import ir.ac.ut.ie.Bolbolestan06.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.ClassTime;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;

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
    protected String getFindStatement(Pair id) {
        return String.format("select * from %s where %s = %s and %s = %s;", TABLE_NAME,
                "courseCode", Utils.quoteWrapper(id.getArgs().get(0)),
                "classCode", Utils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected String getInsertStatement(ClassTime classTime) {
        if (classTime.hasTowDays()) {
            return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s, %s);", TABLE_NAME, COLUMNS,
                    Utils.quoteWrapper(classTime.getCourseCode()), Utils.quoteWrapper(classTime.getClassCode()),
                    Utils.quoteWrapper(classTime.getTime()), Utils.quoteWrapper(classTime.getFirstDay()),
                    Utils.quoteWrapper(classTime.getSecondDay()));
        }
        else {
            return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s);", TABLE_NAME, COLUMNS,
                    Utils.quoteWrapper(classTime.getCourseCode()), Utils.quoteWrapper(classTime.getClassCode()),
                    Utils.quoteWrapper(classTime.getTime()), Utils.quoteWrapper(classTime.getFirstDay()));
        }
    }

    @Override
    protected String getDeleteStatement(Pair id) {
        return null;
    }

    @Override
    protected ClassTime convertResultSetToObject(ResultSet rs) throws SQLException {
        return new ClassTime(
                rs.getString("courseCode"),
                rs.getString("classCode"),
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