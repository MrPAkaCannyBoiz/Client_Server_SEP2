package Dao;

import Application.DatabaseConnection;
import Model.DriverLicense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverLicenseDAOImpl implements DriverLicenseDAO {

    private Connection connection;

    public DriverLicenseDAOImpl() throws SQLException {
        this.connection = new DatabaseConnection().getConnection();
    }

    @Override
    public void addDriverLicense(DriverLicense license) {
        String sql = "INSERT INTO drivinglicense (drivinglicensenumber, categorya, categoryb, categoryc, categoryd, categoryx) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (drivinglicensenumber) DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, license.getLicenseNumber());
            stmt.setBoolean(2, license.isCategoryA());
            stmt.setBoolean(3, license.isCategoryB());
            stmt.setBoolean(4, license.isCategoryC());
            stmt.setBoolean(5, license.isCategoryD());
            stmt.setBoolean(6, license.isCategoryX());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding driver license", e);
        }
    }

    @Override
    public DriverLicense getDriverLicense(String licenseNumber) {
        String sql = "SELECT * FROM drivinglicense WHERE drivinglicensenumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, licenseNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DriverLicense(
                        rs.getString("drivinglicensenumber"),
                        rs.getBoolean("categorya"),
                        rs.getBoolean("categoryb"),
                        rs.getBoolean("categoryc"),
                        rs.getBoolean("categoryd"),
                        rs.getBoolean("categoryx")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving driver license", e);
        }
        return null;
    }

    @Override
    public List<DriverLicense> getAllDriverLicenses() {
        List<DriverLicense> licenses = new ArrayList<>();
        String sql = "SELECT * FROM drivinglicense";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                licenses.add(new DriverLicense(
                        rs.getString("drivinglicensenumber"),
                        rs.getBoolean("categorya"),
                        rs.getBoolean("categoryb"),
                        rs.getBoolean("categoryc"),
                        rs.getBoolean("categoryd"),
                        rs.getBoolean("categoryx")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving driver licenses", e);
        }
        return licenses;
    }

    @Override
    public void updateDriverLicense(DriverLicense license) {
        String sql = "UPDATE drivinglicense SET categorya=?, categoryb=?, categoryc=?, categoryd=?, categoryx=? WHERE drivinglicensenumber=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, license.isCategoryA());
            stmt.setBoolean(2, license.isCategoryB());
            stmt.setBoolean(3, license.isCategoryC());
            stmt.setBoolean(4, license.isCategoryD());
            stmt.setBoolean(5, license.isCategoryX());
            stmt.setString(6, license.getLicenseNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating driver license", e);
        }
    }

    @Override
    public void deleteDriverLicense(String licenseNumber) {
        String sql = "DELETE FROM drivinglicense WHERE drivinglicensenumber=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, licenseNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting driver license", e);
        }
    }
}
