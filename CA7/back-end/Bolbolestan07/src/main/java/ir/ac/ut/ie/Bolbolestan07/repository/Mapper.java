package ir.ac.ut.ie.Bolbolestan07.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> implements IMapper<T, I> {

    protected Map<I, T> loadedMap = new HashMap<I, T>();

    abstract protected String getFindStatement();

    abstract protected void fillFindStatement(PreparedStatement statement, I id) throws SQLException;

    abstract protected String getInsertStatement();

    abstract protected void fillInsertStatement(PreparedStatement statement, T obj) throws SQLException;

    abstract protected String getDeleteStatement();

    abstract protected void fillDeleteStatement(PreparedStatement statement, I id) throws SQLException;

    abstract protected T convertResultSetToObject(ResultSet rs) throws SQLException;

    public T find(I id) throws SQLException {
        T result = loadedMap.get(id);
        if (result != null)
            return result;

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            ResultSet resultSet;
            try {
                fillFindStatement(st, id);
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getInsertStatement())
        ) {
            try {
                fillInsertStatement(st, obj);
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.insert query.");
                throw ex;
            }
        }
    }

    public void delete(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteStatement())
        ) {
            try {
                fillDeleteStatement(st, id);
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }
}