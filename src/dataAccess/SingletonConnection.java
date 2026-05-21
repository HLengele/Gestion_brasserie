package dataAccess;

import java.sql.*;

public class SingletonConnection {

    private static Connection uniqueConnection;

    private static final String URL      = "jdbc:mysql://localhost:3306/BrasserieDB?useSSL=false";
    private static final String USER     = "hugolengele"; // a modifier slon le besion
    private static final String PASSWORD = "monpassword"; // a modifier slon le besion

    private SingletonConnection() {}

    public static Connection getInstance() throws SQLException {
        if (uniqueConnection == null || uniqueConnection.isClosed()) {
            uniqueConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return uniqueConnection;
    }

    public static void closeConnection() throws SQLException {
        if (uniqueConnection != null && !uniqueConnection.isClosed()) {
            uniqueConnection.close();
        }
    }
}