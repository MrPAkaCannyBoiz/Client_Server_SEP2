package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //TODO change this to your own stuff the own later
    private static final String URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("DATABASE_USERNAME");  // user
    private static final String PASSWORD = System.getenv("DATABASE_PASSWORD"); // password

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // Validate that all required environment variables are set
        if (URL == null || USER == null || PASSWORD == null)
        {
            throw new SQLException("Database configuration missing. " +
                "Please set DB_URL, DB_USERNAME, and DB_PASSWORD environment variables.");
        }
        else
        {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

    }


}
