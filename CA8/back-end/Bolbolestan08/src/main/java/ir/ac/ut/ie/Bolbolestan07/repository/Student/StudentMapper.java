package ir.ac.ut.ie.Bolbolestan07.repository.Student;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentMapper extends Mapper<Student, String> implements IStudentMapper {

    private static final String COLUMNS = " id, email, password, name, secondName, birthDate, field, faculty, level, status, img ";
    private static final String TABLE_NAME = "STUDENTS";

    public StudentMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            //st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    id varchar(255) primary key,\n" +
                            "    email varchar(255) not null,\n" +
                            "    password varchar(255) not null,\n" +
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
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public StudentMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement() {
        return String.format("select * from %s where id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindStatement(PreparedStatement statement, String id) throws SQLException{
        statement.setString(1, id);
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, Student student) throws SQLException{
        statement.setString(1, student.getId());
        statement.setString(2, student.getEmail());
        statement.setString(3, student.getPassword());
        statement.setString(4, student.getName());
        statement.setString(5, student.getSecondName());
        statement.setString(6, student.getBirthDate());
        statement.setString(7, student.getField());
        statement.setString(8, student.getFaculty());
        statement.setString(9, student.getLevel());
        statement.setString(10, student.getStatus());
        statement.setString(11, student.getImg());
    }

    @Override
    protected String getDeleteStatement() {
        return String.format("delete from %s where id = ?", TABLE_NAME);
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, String id) throws SQLException{
        statement.setString(1, id);
    }

    @Override
    protected Student convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("id"),
                rs.getString("email"),
                rs.getString("password"),
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

    public List<String> getIds() throws SQLException {
        List<String> result = new ArrayList<String>();
        String statement = "SELECT * FROM " + TABLE_NAME;
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet).getId());
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }

    protected void fillGetStudentByEmail(PreparedStatement statement, String email) throws SQLException{
        statement.setString(1, email);
    }

    public Student getStudentByEmail(String email) throws SQLException {
        String statement = String.format("select * from %s where email = ?;", TABLE_NAME);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                fillGetStudentByEmail(st, email);
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getStudentByEmail query.");
                throw ex;
            }
        }
    }

    private String getChangePasswordStatement() {
        return String.format("UPDATE %s \n" +
                "SET password = ?\n" +
                "WHERE email = ?;", TABLE_NAME);
    }

    private void fillChangePasswordStatement(
            PreparedStatement statement, String email,
            String password) throws SQLException {
        statement.setString(1, password);
        statement.setString(2, email);
    }

    public void changePassword(String email, String newPassword) throws SQLException {
        String statement = getChangePasswordStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement)
        ) {
            try {
                fillChangePasswordStatement(st, email, newPassword);
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in StudentMapper. changePassword query.");
                throw ex;
            }
        }
    }

}
