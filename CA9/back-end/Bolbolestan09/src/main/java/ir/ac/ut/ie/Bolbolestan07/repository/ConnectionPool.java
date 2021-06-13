package ir.ac.ut.ie.Bolbolestan07.repository;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // remote db
        ds.setUrl("jdbc:mysql://db-svc.kalhor-tadayon-ns:3306/Bolbolestan?useUnicode=true&characterEncoding=UTF-8");
        ds.setUsername(System.getenv("DB_USERNAME"));
        ds.setPassword(System.getenv("DB_PASSWORD"));
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
            statement.execute("ALTER DATABASE Bolbolestan CHARACTER SET utf8 COLLATE utf8_general_ci;");
            connection.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
