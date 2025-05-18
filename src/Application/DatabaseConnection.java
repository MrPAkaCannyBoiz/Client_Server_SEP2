package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //TODO change this to your own stuff the own later
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=via_car_rental";
    private static final String USER = "postgres";  // user
    private static final String PASSWORD = "admin"; // password

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
