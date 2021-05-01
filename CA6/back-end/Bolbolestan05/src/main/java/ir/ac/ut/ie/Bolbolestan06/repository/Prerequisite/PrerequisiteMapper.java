package ir.ac.ut.ie.Bolbolestan06.repository.Prerequisite;

import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;
import ir.ac.ut.ie.Bolbolestan06.repository.Student.IStudentMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrerequisiteMapper extends Mapper<ArrayList<Course>, String> implements IPrerequisiteMapper{
    private static final String COLUMNS = " courseCode, prerequisiteCode";
    private static final String TABLE_NAME = "PREREQUISITES";

    public PrerequisiteMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255) not null FOREIGN KEY REFERENCES COURSES(courseCode),\n" +
                            "    prerequisite varchar(255) not null FOREIGN KEY REFERENCES COURSES(prerequisite),\n" +
                            "    primary key(courseCode, prerequisite)\n" +
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
    protected String getInsertStatement(ArrayList<Course> course) {
        ArrayList<String> prerequisites = new ArrayList<>(course.getPrerequisites());
        String insertString = "";
        for (String code : prerequisites) {
            String query = String.format("INSERT INTO %s ( %s ) values (%s);\n", TABLE_NAME, COLUMNS,
                    Utils.quoteWrapper(course.getCourseCode()), Utils.quoteWrapper(code));
            insertString += query;
        }
        return insertString;
    }

    @Override
    protected String getDeleteStatement(String courseCode) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "courseCode", Utils.quoteWrapper(courseCode));
    }

    @Override
    protected ArrayList<Course> convertResultSetToObject(ResultSet rs) throws SQLException {
        ArrayList<Course> result = new ArrayList<>();
        while (rs.next()) {
            String coffeeName = rs.getString("");
            int supplierID = rs.getInt("SUP_ID");
            float price = rs.getFloat("PRICE");
            int sales = rs.getInt("SALES");
            int total = rs.getInt("TOTAL");
            System.out.println(coffeeName + ", " + supplierID + ", " + price +
                    ", " + sales + ", " + total);
        }
    }
}

public class StudentMapper extends Mapper<Student, String> implements IStudentMapper {

    private static final String COLUMNS = " id, name, secondName, birthDate, field, faculty, level, status, img ";
    private static final String TABLE_NAME = "STUDENTS";

    public StudentMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    id varchar(255) primary key,\n" +
                            "    name varchar(255) not null,\n" +
                            "    secondName varchar(255) not null,\n" +
                            "    birthDate varchar(255) not null,\n" +
                            "    field varchar(255) not null,\n" +
                            "    faculty varchar(255) not null,\n" +
                            "    level varchar(255) not null,\n" +
                            "    status varchar(255) not null,\n" +
                            "    img text not null\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public StudentMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(String id) {
        return String.format("select * from %s where %s.%s = %s;", TABLE_NAME, TABLE_NAME, "id", Utils.quoteWrapper(id));
    }

    @Override
    protected String getInsertStatement(Student student) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s, %s, %s, %s, %s, %s);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(student.getId()), Utils.quoteWrapper(student.getName()),
                Utils.quoteWrapper(student.getSecondName()), Utils.quoteWrapper(student.getBirthDate()),
                Utils.quoteWrapper(student.getField()), Utils.quoteWrapper(student.getFaculty()),
                Utils.quoteWrapper(student.getLevel()), Utils.quoteWrapper(student.getStatus()),
                Utils.quoteWrapper(student.getImg()));
    }

    @Override
    protected String getDeleteStatement(String id) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "id", Utils.quoteWrapper(id));
    }

    @Override
    protected Student convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("secondName"),
                rs.getString("birthDate"),
                rs.getString("field"),
                rs.getString("faculty"),
                rs.getString("level"),
                rs.getString("status"),
                rs.getString("img")
        );
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> result = new ArrayList<Student>();
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