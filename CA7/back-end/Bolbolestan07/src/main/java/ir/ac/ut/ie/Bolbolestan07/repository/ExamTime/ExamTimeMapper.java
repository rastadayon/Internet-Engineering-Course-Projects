package ir.ac.ut.ie.Bolbolestan07.repository.ExamTime;

import ir.ac.ut.ie.Bolbolestan07.utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.ExamTime;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamTimeMapper extends Mapper<ExamTime, Pair> implements IExamTimeMapper {

    private static final String COLUMNS = " courseCode, classCode, start, end ";
    private static final String TABLE_NAME = "EXAM_TIMES";

    public ExamTimeMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255),\n" +
                            "    classCode varchar(255),\n" +
                            "    start varchar(255) not null,\n" +
                            "    end varchar(255) not null,\n" +
                            "    primary key(courseCode, classCode),\n" +
                            "    foreign key (courseCode, classCode) references OFFERINGS(courseCode, classCode)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public ExamTimeMapper() throws SQLException {}

    @Override
    protected String getFindStatement() {
        return String.format("select * from %s where courseCode = ? and classCode = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindStatement(PreparedStatement statement, Pair id) throws SQLException{
        statement.setString(0, id.getArgs().get(0));
        statement.setString(1, id.getArgs().get(1));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (?, ?, ?, ?);", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, ExamTime examTime) throws SQLException{
        statement.setString(0, examTime.getCourseCode());
        statement.setString(1, examTime.getClassCode());
        statement.setString(2, examTime.getStart());
        statement.setString(3, examTime.getEnd());
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
    protected ExamTime convertResultSetToObject(ResultSet rs) throws SQLException {
        return new ExamTime(
                rs.getString("start"),
                rs.getString("end")
        );
    }

    @Override
    public List<ExamTime> getAll() throws SQLException {
        List<ExamTime> result = new ArrayList<ExamTime>();
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
