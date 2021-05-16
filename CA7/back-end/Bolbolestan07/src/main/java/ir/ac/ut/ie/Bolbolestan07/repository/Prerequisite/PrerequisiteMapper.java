package ir.ac.ut.ie.Bolbolestan07.repository.Prerequisite;

import ir.ac.ut.ie.Bolbolestan07.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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
            insertString += String.format("INSERT IGNORE INTO %s ( %s ) values (%s, %s);\n", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(courseCode), Utils.quoteWrapper(prerequisite));
        }
        System.out.println(insertString);
        return insertString;
    }

    @Override
    protected String getDeleteStatement(String courseCode) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "courseCode", Utils.quoteWrapper(courseCode));
    }

    @Override
    protected HashMap<String, ArrayList<String>> convertResultSetToObject(ResultSet rs) throws SQLException {
        return null;
    }

    private String customConvertResultSetToObject(ResultSet rs) throws SQLException {
        return rs.getString("prerequisiteCode");
    }

    public ArrayList<String> getPrerequisites(String courseCode) throws SQLException {
        String statement = getFindStatement(courseCode);
        ArrayList<String> result = new ArrayList<>();

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
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
