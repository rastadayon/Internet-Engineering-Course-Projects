package ir.ac.ut.ie.Bolbolestan07.repository.Course;

import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseMapper extends Mapper<Course, String> implements ICourseMapper {

    private static final String COLUMNS = " code, name, units, type ";
    private static final String TABLE_NAME = "COURSES";

    public CourseMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    code varchar(255) primary key,\n" +
                            "    name varchar(255) not null,\n" +
                            "    units int not null,\n" +
                            "    type text not null\n" +
//                            "    prerequisite text,\n" +
//                            "    foreign key (prerequisite) references COURSES(code) \n" +
                            ");",
                    TABLE_NAME));
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public CourseMapper() throws SQLException {
    }

    @Override
    protected String getInsertStatement(Course course) {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (%s, %s, %d, %s);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(course.getCourseCode()), Utils.quoteWrapper(course.getName()),
                course.getUnits(), Utils.quoteWrapper(course.getType()));
    }

    @Override
    protected String getDeleteStatement(String code) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "id", Utils.quoteWrapper(code));
    }

    @Override
    protected String getFindStatement(String code) {
        return String.format("select * from %s where %s.%s = %s;", TABLE_NAME, TABLE_NAME, "code", Utils.quoteWrapper(code));
    }

    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> result = new ArrayList<Course>();
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

    @Override
    protected Course convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("code"),
                rs.getString("name"),
                rs.getInt("units"),
                rs.getString("type"),
                null
        );
    }
}
