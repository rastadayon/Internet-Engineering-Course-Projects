package ir.ac.ut.ie.Bolbolestan06.repository.Prerequisite;

import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Student.IStudentMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrerequisiteMapper extends Mapper<HashMap<String, ArrayList<String>>, String> implements IPrerequisiteMapper{
    private static final String COLUMNS = " courseCode, prerequisiteCode";
    private static final String TABLE_NAME = "PREREQUISITES";

    public PrerequisiteMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255) not null,\n" +
                            "    prerequisite varchar(255) not null,\n" +
                            "    primary key(courseCode, prerequisite),\n" +
                            "    foreign key (courseCode) references COURSES(code),\n" +
                            "    foreign key (prerequisite) references COURSES(code)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public PrerequisiteMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(String courseCode) {
        return String.format("select * from %s where %s.%s = %s;", TABLE_NAME, TABLE_NAME, "courseCode", Utils.quoteWrapper(courseCode));
    }

    @Override
    protected String getInsertStatement(HashMap<String, ArrayList<String>> courses) {
        String insertString = "";
        assert courses.size() == 1;
        String courseCode = courses.entrySet().stream().findFirst().get().getKey();
        ArrayList<String> prerequisites = courses.entrySet().stream().findFirst().get().getValue();
        for (String prerequisite: prerequisites) {
            insertString += String.format("INSERT INTO %s ( %s ) values (%s, %s);\n", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(courseCode), Utils.quoteWrapper(prerequisite));
        }
        return insertString;
    }

    @Override
    protected String getDeleteStatement(String courseCode) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "courseCode", Utils.quoteWrapper(courseCode));
    }

    @Override
    protected HashMap<String, ArrayList<String>> convertResultSetToObject(ResultSet rs) throws SQLException {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        ArrayList<String> courses = new ArrayList<>();
        String courseCode = "";
        while (rs.next()) {
            String correspondingCourseCode = rs.getString("courseCode");
            if(!courseCode.equals("")) {
                if(!correspondingCourseCode.equals(courseCode)) System.out.println("something went wring in prerequisites");
                assert correspondingCourseCode.equals(courseCode);
            }
            else
                courseCode = correspondingCourseCode;

            String prerequisiteCode = rs.getString("prerequisite");
            courses.add(prerequisiteCode);
        }
        result.put(courseCode, courses);
        return result;
    }
}
