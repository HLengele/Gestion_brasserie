package dataAccess;

import java.sql.*;

public class SingletonConnection {

    private static Connection uniqueConnection;

    // URL, Utilisateur et Options modifiés pour MySQL selon votre tutoriel
    private static final String URL      = "jdbc:mysql://localhost:3306/BrasserieDB?useSSL=false";
    private static final String USER     = "hugolengele";
    private static final String PASSWORD = "monpassword"; // Celui défini à l'installation du serveur

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