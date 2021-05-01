package ir.ac.ut.ie.Bolbolestan06.repository;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // remote db
        ds.setUrl("jdbc:mysql://localhost:3306/BolbolestanDB");
        ds.setUsername("rasta");
//        ds.setUsername("ghazal");
        ds.setPassword("123");
        ds.setMinIdle(1);
        ds.setMaxIdle(2000);
        ds.setMaxOpenPreparedStatements(2000);
    }

    public static Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        }catch (Exception e){
            ds.setPassword("123");
            return ds.getConnection();
        }
    }

    private ConnectionPool() {
    }
}
