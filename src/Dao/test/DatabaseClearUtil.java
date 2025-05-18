package Dao.test;

import Application.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseClearUtil {
    public static void clearAllTables() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Uwaga: kolejność zgodna z zależnościami FK
            stmt.executeUpdate("DELETE FROM booking");
            stmt.executeUpdate("DELETE FROM customer");
            stmt.executeUpdate("DELETE FROM employee");
            stmt.executeUpdate("DELETE FROM vehicle");
            stmt.executeUpdate("DELETE FROM drivinglicense");

            // Podtypy jeśli trzeba:
            stmt.executeUpdate("DELETE FROM truck");
            stmt.executeUpdate("DELETE FROM van");
            stmt.executeUpdate("DELETE FROM motorcycle");
            stmt.executeUpdate("DELETE FROM sportscar");
            stmt.executeUpdate("DELETE FROM ufo");

            System.out.println("🧹 Database cleared successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Failed to clear database:");
            e.printStackTrace();
        }
    }
}
