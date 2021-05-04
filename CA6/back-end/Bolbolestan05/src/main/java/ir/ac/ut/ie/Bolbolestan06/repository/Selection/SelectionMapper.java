package ir.ac.ut.ie.Bolbolestan06.repository.Selection;

import ir.ac.ut.ie.Bolbolestan06.Utils.Pair;
import ir.ac.ut.ie.Bolbolestan06.Utils.Utils;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.WeeklySchedule;
import ir.ac.ut.ie.Bolbolestan06.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan06.repository.Mapper;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.Selection;

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

    protected String getFindScheduleStatement(String studentId, String status) {
        return String.format("select * from %s where %s = %s and %s = %s;", TABLE_NAME,
                "studentId", Utils.quoteWrapper(studentId),
                "status", Utils.quoteWrapper(status));
    }

    @Override
    protected String getFindStatement(Pair id) {
        return String.format("select * from %s where %s = %s and %s = %s;", TABLE_NAME,
                "studentId", Utils.quoteWrapper(id.getArgs().get(0)),
                "courseCode", Utils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected String getInsertStatement(Selection selection) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s);", TABLE_NAME, COLUMNS,
                Utils.quoteWrapper(selection.getStudentId()), Utils.quoteWrapper(selection.getCourseCode()),
                Utils.quoteWrapper(selection.getClassCode()), Utils.quoteWrapper(selection.getStatus()));
    }

    @Override
    protected String getDeleteStatement(Pair id) {
        return String.format("delete from %s where %s = %s and %s = %s;", TABLE_NAME,
                "studentId", Utils.quoteWrapper(id.getArgs().get(0)),
                "courseCode", Utils.quoteWrapper(id.getArgs().get(1)));
    }

    public String getDeleteSelectionsStatement(String studentId, String status) {
        return String.format("delete from %s where %s = %s and %s = %s;",
                TABLE_NAME, "studentId", Utils.quoteWrapper(studentId),
                "status", Utils.quoteWrapper(status));
    }

    public String getFinalizeStatement(String studentId) {
        return String.format("update %s set %s = %s where %s = %s and %s = %s;",
                TABLE_NAME, "status", "submitted", "studentId", Utils.quoteWrapper(studentId),
                "status", "selected");
    }

    public void deleteSelections(String studentId) throws SQLException {
        String statement = getDeleteSelectionsStatement(studentId, "selected");
        System.out.println(statement);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
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
        String statement = getFindScheduleStatement(studentId, status);
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
                System.out.println("error in Mapper.findSchedule query.");
                throw ex;
            }
        }
    }

    public void finalizeSelection(String studentId) throws SQLException {
        String statement = getFinalizeStatement(studentId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.finalizeSelection query.");
                throw ex;
            }
        }
    }
}
