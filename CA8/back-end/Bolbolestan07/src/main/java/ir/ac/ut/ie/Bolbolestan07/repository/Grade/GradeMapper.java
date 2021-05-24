package ir.ac.ut.ie.Bolbolestan07.repository.Grade;

import ir.ac.ut.ie.Bolbolestan07.utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.Selection;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;
import ir.ac.ut.ie.Bolbolestan07.repository.Prerequisite.IPrerequisiteMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GradeMapper extends Mapper<Grade, Pair> implements IGradeMapper { // returns Grade objects which do not have the course name and
    // should be set later via the getCourseNameByCourseCode function

    private static final String COLUMNS = "studentId, courseCode, courseName, grade, units, term";
    private static final String TABLE_NAME = "GRADES";

    public GradeMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    studentId varchar(255) not null,\n" +
                            "    courseCode varchar(255) not null,\n" +
                            "    courseName varchar(255) not null,\n" +
                            "    grade int,\n" +
                            "    units int not null,\n" +
                            "    term int not null,\n" +
                            "    primary key(studentId, courseCode, term),\n" +
                            "    foreign key (courseCode) references COURSES(code)\n" +
                            ");",
                    TABLE_NAME));
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public GradeMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement() {
        return String.format("select * from %s where studentId = ? order by term ASC;", TABLE_NAME);
    }
    
    @Override
    protected void fillFindStatement(PreparedStatement statement, Pair id) throws SQLException{
        statement.setString(1, id.getArgs().get(0));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (?, ?, ?, ?, ?, ?);", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, Grade grade) throws SQLException{
        statement.setString(1, grade.getStudentId());
        statement.setString(2, grade.getCode());
        statement.setString(3, grade.getCourseName());
        statement.setDouble(4, grade.getGrade());
        statement.setInt(5, grade.getUnits());
        statement.setInt(6, grade.getTerm());
    }

    @Override
    protected String getDeleteStatement() {
        return String.format("delete from %s where studentId = ?;", TABLE_NAME);
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Pair pair) throws SQLException{
        statement.setString(1, pair.getArgs().get(0));
    }

    @Override
    protected Grade convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Grade(
                rs.getString("studentId"),
                rs.getString("courseCode"),
                rs.getString("courseName"),
                rs.getInt("grade"),
                rs.getInt("units"),
                rs.getInt("term")
        );
    }

    protected String getFindTermStatement() {
        return String.format("select * from %s where studentId = ? order by term desc limit 1;",
                TABLE_NAME);
    }

    protected void fillFindTermStatement(PreparedStatement statement, String studentId) throws SQLException{
        statement.setString(1, studentId);
    }

    protected String getFindStudentGradesStatement() {
        return String.format("select * from %s where studentId = ?;", TABLE_NAME);
    }

    protected void fillFindStudentGradesStatement(PreparedStatement statement, String studentId) throws SQLException{
        statement.setString(1, studentId);
    }

    public ArrayList<Grade> getStudentGrades(String studentId) throws SQLException {
        ArrayList<Grade> result = new ArrayList<>();
        String statement = getFindStudentGradesStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                fillFindStudentGradesStatement(st, studentId);
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getStudentGrades query.");
                throw ex;
            }
        }
    }

    public int getCurrentTerm(String studentId) throws SQLException {
        String statement = getFindTermStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                fillFindTermStatement(st, studentId);
                resultSet = st.executeQuery();
                resultSet.next();
                return resultSet.getInt("term");
                //con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findTerm query.");
                throw ex;
            }
        }
    }

}
