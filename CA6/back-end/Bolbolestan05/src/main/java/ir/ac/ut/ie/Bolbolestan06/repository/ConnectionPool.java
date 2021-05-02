package ir.ac.ut.ie.Bolbolestan06.repository;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // remote db
        ds.setUrl("jdbc:mysql://localhost:3306/BolbolestanDB?useUnicode=true&characterEncoding=UTF-8");
        //ds.setUsername("rasta");
        ds.setUsername("ghazal");
        ds.setPassword("123");
        ds.setMinIdle(1);
        ds.setMaxIdle(2000);
        ds.setMaxOpenPreparedStatements(2000);
        setEncoding();
    }

    public static Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        }catch (Exception e){
            ds.setPassword("123");
            return ds.getConnection();
        }
    }

    public static void setEncoding(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute("ALTER DATABASE BolbolestanDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
            connection.close();
            statement.close();
            System.out.println("SET KARD UTF ESHO >>>>>>>>>>>>>>>>>");
        }
        catch (SQLException e)
        {
            System.out.println("NATUNEST SET KONE UTF ESHO...........");
            System.out.println(e.getMessage());
        }
    }
}
