package ir.ac.ut.ie.Bolbolestan07.repository.Offering;

import ir.ac.ut.ie.Bolbolestan07.utils.Pair;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan07.repository.ConnectionPool;
import ir.ac.ut.ie.Bolbolestan07.repository.Mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferingMapper extends Mapper<Offering, Pair> implements IOfferingMapper {

    private static final String COLUMNS = "courseCode, classCode, instructor, capacity, signedUp";
    private static final String TABLE_NAME = "OFFERINGS";

    public OfferingMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (\n" +
                            "    courseCode varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    instructor varchar(255) not null,\n" +
                            "    capacity int not null,\n" +
                            "    signedUp int not null,\n" +
                            "    primary key(courseCode, classCode),\n" +
                            "    foreign key (courseCode) references COURSES(code)\n" +
                            ");",
                    TABLE_NAME));
            st.execute(String.format("ALTER TABLE %s CHARACTER SET utf8 COLLATE utf8_general_ci;", TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public OfferingMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement() {
        return String.format("select * from %s where courseCode = ? and classCode = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindStatement(PreparedStatement statement, Pair id) throws SQLException{
        statement.setString(1, id.getArgs().get(0));
        statement.setString(2, id.getArgs().get(1));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s ( %s ) values (?, ?, ?, ?, ?);", TABLE_NAME, COLUMNS);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, Offering offering) throws SQLException{
        statement.setString(1, offering.getCourseCode());
        statement.setString(2, offering.getClassCode());
        statement.setString(3, offering.getInstructor());
        statement.setInt(4, offering.getCapacity());
        statement.setInt(5, offering.getSignedUp());
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Pair id) throws SQLException{
        return;
    }

    @Override
    protected Offering convertResultSetToObject(ResultSet rs) throws SQLException {
        System.out.println(rs);
        return new Offering(
                rs.getString("courseCode"),
                rs.getString("classCode"),
                rs.getString("instructor"),
                rs.getInt("capacity"),
                rs.getInt("signedUp")
        );
    }

    @Override
    public List<Offering> getAll() throws SQLException {
        return null;
    }

    public String getIncreaseSignedUpStatement() {
        return String.format("update %s set signedUp = signedUp + 1 where courseCode = ? and classCode = ?;",
                TABLE_NAME);
    }

    protected void fillIncreaseSignedUpStatement(PreparedStatement statement, 
        String courseCode, String classCode) throws SQLException{
        statement.setString(1, courseCode);
        statement.setString(2, classCode);
    }


    public void increaseSignedUp(String courseCode, String classCode) throws SQLException {
        String statement = getIncreaseSignedUpStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                fillIncreaseSignedUpStatement(st, courseCode, classCode);
                con.setAutoCommit(false);
                st.executeUpdate();
                con.commit();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.increaseSignedUp query.");
                throw ex;
            }
        }
    }

    public String getIncreaseCapacityStatement() {
        return String.format("update %s set signedUp = signedUp + 1 and capacity = capacity + 1 where courseCode = ? and classCode = ?;",
                TABLE_NAME);
    }

    protected void fillIncreaseCapacityStatement(PreparedStatement statement, 
        String courseCode, String classCode) throws SQLException{
        statement.setString(1, courseCode);
        statement.setString(2, classCode);
    }

    public void increaseCapacity(String courseCode, String classCode) throws SQLException {
        String statement = getIncreaseCapacityStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                fillIncreaseCapacityStatement(st, courseCode, classCode);
                con.setAutoCommit(false);
                st.executeUpdate();
                con.commit();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.increaseCapacity query.");
                throw ex;
            }
        }
    }

    public String getDecreaseSignedUpStatement() {
        return String.format("update %s set signedUp = signedUp - 1 where courseCode = %s and classCode = %s;",
                TABLE_NAME);
    }

    protected void fillDecreaseSignedUpStatement(PreparedStatement statement, 
        String courseCode, String classCode) throws SQLException{
        statement.setString(1, courseCode);
        statement.setString(2, classCode);
    }

    public void decreaseSignedUp(String courseCode, String classCode) throws SQLException {
        String statement = getDecreaseSignedUpStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                fillDecreaseSignedUpStatement(st, courseCode, classCode);
                con.setAutoCommit(false);
                st.executeUpdate();
                con.commit();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.decreaseSignedUp query.");
                throw ex;
            }
        }
    }

    private String getSearchStatement(String filter) {
        String statement;
        if (filter.equals("All"))
            statement = String .format("select * from OFFERINGS inner join COURSES" +
                    " on COURSES.code=OFFERINGS.courseCode" +
                    " where name like ?;");
        else
            statement = String .format("select * from OFFERINGS inner join COURSES" +
                            " on COURSES.code=OFFERINGS.courseCode" +
                            " where name like ?" +
                            " and type=?;");
        return statement;
    }

    protected void fillSearchStatement(PreparedStatement statement, 
        String keyword, String filter) throws SQLException{
        statement.setString(1, Utils.searchKeywordWrapper(keyword));
        if (!filter.equals("All"))
            statement.setString(2, filter);
    }

    public ArrayList<Offering> getSearchedOfferings(
            String keyword, String filter) throws SQLException{
        String statement = getSearchStatement(filter);
        ArrayList<Offering> result = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                fillSearchStatement(st, keyword, filter);
                resultSet = st.executeQuery();
                while (resultSet.next()) {
                    result.add(convertResultSetToObject(resultSet));
                }
                con.close();

                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getSearchedOfferings query.");
                throw ex;
            }
        }
    }
}
