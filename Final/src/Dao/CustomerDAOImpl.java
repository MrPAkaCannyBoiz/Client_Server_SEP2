package Dao;

import Application.DatabaseConnection;
import Model.Customer;
import Model.DriverLicense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void addCustomer(Customer customer) {
        String insertLicenseSQL = "INSERT INTO drivinglicense (drivinglicensenumber, categorya, categoryb, categoryc, categoryd, categoryx) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (drivinglicensenumber) DO NOTHING"; // don't want duplicates

        String insertCustomerSQL = "INSERT INTO customer (customerid, name, phoneno, email, nationality, dob, driverlicense, cpr, passno) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            // 1. Insert into drivinglicense
            try (PreparedStatement stmt = conn.prepareStatement(insertLicenseSQL)) {
                stmt.setString(1, customer.getDriverLicense().getLicenseNumber());
                stmt.setBoolean(2, customer.getDriverLicense().canDrive("A"));
                stmt.setBoolean(3, customer.getDriverLicense().canDrive("B"));
                stmt.setBoolean(4, customer.getDriverLicense().canDrive("C"));
                stmt.setBoolean(5, customer.getDriverLicense().canDrive("D"));
                stmt.setBoolean(6, customer.getDriverLicense().canDrive("X"));
                stmt.executeUpdate();
            }

            // 2. Insert into customer
            try (PreparedStatement stmt = conn.prepareStatement(insertCustomerSQL)) {
                stmt.setInt(1, customer.getVIAId());
                stmt.setString(2, customer.getName());
                stmt.setString(3, customer.getPhoneNo());
                stmt.setString(4, customer.getEmail());
                stmt.setString(5, customer.getNationality());
                stmt.setDate(6, Date.valueOf(customer.getDob()));
                stmt.setString(7, customer.getDriverLicense().getLicenseNumber());
                stmt.setString(8, customer.getCpr());
                stmt.setString(9, customer.getPassNo());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE customerid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String licenseNumber = rs.getString("driverlicense");
                DriverLicense driverLicense = getDriverLicenseFromDb(licenseNumber);

                return new Customer(
                        rs.getInt("customerid"),
                        rs.getString("cpr"),
                        rs.getString("passno"),
                        rs.getString("name"),
                        rs.getString("phoneno"),
                        rs.getString("email"),
                        driverLicense,
                        rs.getString("nationality"),
                        rs.getDate("dob").toString()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String licenseNumber = rs.getString("driverlicense");
                DriverLicense driverLicense = getDriverLicenseFromDb(licenseNumber);

                Customer customer = new Customer(
                        rs.getInt("customerid"),
                        rs.getString("cpr"),
                        rs.getString("passno"),
                        rs.getString("name"),
                        rs.getString("phoneno"),
                        rs.getString("email"),
                        driverLicense,
                        rs.getString("nationality"),
                        rs.getDate("dob").toString()
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name = ?, phoneno = ?, email = ?, nationality = ?, dob = ?, driverlicense = ?, cpr = ?, passno = ? WHERE customerid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhoneNo());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getNationality());
            stmt.setDate(5, Date.valueOf(customer.getDob()));
            stmt.setString(6, customer.getDriverLicense().getLicenseNumber());
            stmt.setString(7, customer.getCpr());
            stmt.setString(8, customer.getPassNo());
            stmt.setInt(9, customer.getVIAIdCounter());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE customerid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DriverLicense getDriverLicenseFromDb(String licenseNumber) throws SQLException {
        String sql = "SELECT * FROM drivinglicense WHERE drivinglicensenumber = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licenseNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new DriverLicense(
                        licenseNumber, // <-- now required!
                        rs.getBoolean("categorya"),
                        rs.getBoolean("categoryb"),
                        rs.getBoolean("categoryc"),
                        rs.getBoolean("categoryd"),
                        rs.getBoolean("categoryx")
                );
            }
        }

        return null;
    }

}
