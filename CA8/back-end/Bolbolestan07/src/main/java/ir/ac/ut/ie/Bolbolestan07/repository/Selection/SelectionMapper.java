package ir.ac.ut.ie.Bolbolestan07.repository.Selection;

import ir.ac.ut.ie.Bolbolestan07.utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.Selection;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SelectionMapper extends Mapper<Selection, Pair> implements ISelectionMapper {
    private static final String COLUMNS = " studentId, courseCode, classCode, status";
    private static final String TABLE_NAME = "SELECTIONS";

    public SelectionMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            //st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    studentId varchar(255) not null,\n" +
                            "    courseCode varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    status varchar(255) not null,\n" +
                            "    primary key(studentId, courseCode),\n" +
                            "    foreign key (studentId) references STUDENTS(id),\n" +
                            "    foreign key (courseCode, classCode) references OFFERINGS(courseCode, classCode)\n" +
                            ");",
                    TABLE_NAME));
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public SelectionMapper() throws SQLException {
    }

    protected String getFindScheduleStatement() {
        return String.format("select * from %s where studentId = ? and status = ?;", 
            TABLE_NAME);
    }

    protected void fillFindScheduleStatement(PreparedStatement statement, 
        String studentId, String status) throws SQLException{
        statement.setString(1, studentId);
        statement.setString(2, status);
    }

    @Override
    protected String getFindStatement() {
        return String.format("select * from %s where studentId = ? and courseCode = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindStatement(PreparedStatement statement, Pair id) throws SQLException{
        statement.setString(1, id.getArgs().get(0));
        statement.setString(2, id.getArgs().get(1));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT INTO %s ( %s ) values (?, ?, ?, ?);", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, Selection selection) throws SQLException{
        statement.setString(1, selection.getStudentId());
        statement.setString(2, selection.getCourseCode());
        statement.setString(3, selection.getClassCode());
        statement.setString(4, selection.getStatus());
    }

    @Override
    protected String getDeleteStatement() {
        return String.format("delete from %s where studentId = ? and courseCode = ?;", TABLE_NAME);
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Pair id) throws SQLException{
        statement.setString(1, id.getArgs().get(0));
        statement.setString(2, id.getArgs().get(1));
    }

    public String getDeleteSelectionsStatement() {
        return String.format("delete from %s where studentId = ? and status = ?;",
                TABLE_NAME);
    }

    protected void fillDeleteSelectionsStatement(PreparedStatement statement, 
        String studentId, String status) throws SQLException{
        statement.setString(1, studentId);
        statement.setString(2, status);
    }

    public String getFinalizeStatement() {
        return String.format("update %s set %s = %s where %s = ? and %s = %s;",
                TABLE_NAME, "status", "'submitted'", "studentId", "status", "'selected'");
    }

    protected void fillFinalizeStatement(PreparedStatement statement, String studentId) throws SQLException{
        statement.setString(1, studentId);
    }

    public void deleteSelections(String studentId) throws SQLException {
        String statement = getDeleteSelectionsStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                fillDeleteSelectionsStatement(st, studentId, "selected");
                st.executeUpdate();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.deleteSelections query.");
                throw ex;
            }
        }
    }

    @Override
    protected Selection convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Selection(
                rs.getString("studentId"),
                rs.getString("courseCode"),
                rs.getString("classCode"),
                rs.getString("status")
        );
    }

    public List<Selection> findStudentSchedule(String studentId, String status) throws SQLException {
        List<Selection> result = new ArrayList<Selection>();
        String statement = getFindScheduleStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                fillFindScheduleStatement(st, studentId, status);
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findSchedule query.");
                throw ex;
            }
        }
    }

    public void finalizeSelection(String studentId) throws SQLException {
        String statement = getFinalizeStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                fillFinalizeStatement(st, studentId);
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.finalizeSelection query.");
                throw ex;
            }
        }
    }

    public String getUpdateWaitingsStatement() {
        return String.format("update %s set status = 'submitted' where status = 'waiting';",
                TABLE_NAME);
    }

    public void updateWaitings() throws SQLException {
        String statement = getUpdateWaitingsStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.updateWaitings query.");
                throw ex;
            }
        }
    }

    protected String getFindWaitingsStatement() {
        return String.format("select * from %s where status = 'waiting';", TABLE_NAME);
    }

    public List<Selection> findWaitings() throws SQLException {
        List<Selection> result = new ArrayList<Selection>();
        String statement = getFindWaitingsStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findWaiting query.");
                throw ex;
            }
        }
    }
}
