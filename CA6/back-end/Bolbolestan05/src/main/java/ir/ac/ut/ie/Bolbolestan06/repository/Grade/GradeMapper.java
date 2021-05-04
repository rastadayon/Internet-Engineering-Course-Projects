package ir.ac.ut.ie.Bolbolestan06.repository.Grade;

import ir.ac.ut.ie.Bolbolestan06.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Grade;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.Selection;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Prerequisite.IPrerequisiteMapper;

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
    protected String getFindStatement(Pair pair) {
        String studentId = pair.getArgs().get(0);
        return String.format("select * from %s where %s.%s = %s order by term ASC;", TABLE_NAME, TABLE_NAME, "studentId", Utils.quoteWrapper(studentId));
    }


    @Override
    protected String getInsertStatement(Grade grade) {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (%s, %s, %s, %d, %d, %d);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(grade.getStudentId()), Utils.quoteWrapper(grade.getCode()),
                Utils.quoteWrapper(grade.getCourseName()), grade.getGrade(), grade.getUnits(), grade.getTerm());
    }

    @Override
    protected String getDeleteStatement(Pair pair) {
        String studentId = pair.getArgs().get(0);
        return String.format("delete from %s where %s.%s = %s;", TABLE_NAME, TABLE_NAME, "studentId", Utils.quoteWrapper(studentId));
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

    protected String getFindTermStatement(String studentId) {
        return String.format("select * from %s where %s = %s order by term desc limit 1;",
                TABLE_NAME, "studentId", Utils.quoteWrapper(studentId));
    }

    public ArrayList<Grade> getStudentGrades(String studentId) throws SQLException {
        ArrayList<Grade> result = new ArrayList<>();
        String statement = String.format("select * from %s where %s.%s = %s;", TABLE_NAME, TABLE_NAME, "studentId", Utils.quoteWrapper(studentId));
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
                System.out.println("error in Mapper.getStudentGrades query.");
                throw ex;
            }
        }
    }

    public int getCurrentTerm(String studentId) throws SQLException {
        String statement = getFindTermStatement(studentId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                con.close();
                return resultSet.getInt("term");
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findTerm query.");
                throw ex;
            }
        }
    }

}
