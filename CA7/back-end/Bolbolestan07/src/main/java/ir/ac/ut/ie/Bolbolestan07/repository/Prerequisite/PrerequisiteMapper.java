package ir.ac.ut.ie.Bolbolestan07.repository.Prerequisite;

import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;
import ir.ac.ut.ie.Bolbolestan07.repository.Student.IStudentMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrerequisiteMapper extends Mapper<HashMap<String, ArrayList<String>>, String> implements IPrerequisiteMapper {
    private static final String COLUMNS = " courseCode, prerequisiteCode";
    private static final String TABLE_NAME = "PREREQUISITES";

    public PrerequisiteMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            //st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255) not null,\n" +
                            "    prerequisiteCode varchar(255) not null,\n" +
                            "    primary key(courseCode, prerequisiteCode),\n" +
                            "    foreign key (courseCode) references COURSES(code),\n" +
                            "    foreign key (prerequisiteCode) references COURSES(code)\n" +
                            ");",
                    TABLE_NAME));
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public PrerequisiteMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement() {
        return String.format("select * from %s where courseCode = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindStatement(PreparedStatement statement, String courseCode) throws SQLException{
        statement.setString(1, courseCode);
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (?, ?);\n", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, HashMap<String, ArrayList<String>> courses) throws SQLException{
        String insertString = "";
        assert courses.size() == 1;

        String courseCode = courses.entrySet().stream().findFirst().get().getKey();
        ArrayList<String> prerequisites = courses.entrySet().stream().findFirst().get().getValue();
        for (String prerequisite: prerequisites) {
            PreparedStatement st = statement;
            st.setString(1, courseCode);
            st.setString(2, prerequisite);
            insertString += st;
        }
        //statement = prepareStatement(insertString);
    }

    @Override
    protected String getDeleteStatement() {
        return String.format("delete from %s where courseCode = ?", TABLE_NAME);
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, String courseCode) throws SQLException{
        statement.setString(1, courseCode);
    }

    @Override
    protected HashMap<String, ArrayList<String>> convertResultSetToObject(ResultSet rs) throws SQLException {
        return null;
    }

    private String customConvertResultSetToObject(ResultSet rs) throws SQLException {
        return rs.getString("prerequisiteCode");
    }

    public ArrayList<String> getPrerequisites(String courseCode) throws SQLException {
        String statement = getFindStatement();
        ArrayList<String> result = new ArrayList<>();

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                fillFindStatement(st, courseCode);
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(customConvertResultSetToObject(resultSet));
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getPrerequisites query.");
                throw ex;
            }
        }
    }
}
