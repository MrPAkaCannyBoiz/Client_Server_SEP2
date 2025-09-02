package Dao;

import Application.DatabaseConnection;
import Model.Booking;
import Model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private Connection connection;
    private VehicleDAO vehicleDAO;

    public BookingDAOImpl() throws SQLException {
        this.connection = new DatabaseConnection().getConnection();
        this.vehicleDAO = new VehicleDAOImpl();
    }

    @Override
    public void addBooking(Booking booking) {
        String sql = "INSERT INTO booking (bookingid, customerid, employeeid, startdate, enddate, vehicleid, active, finalpayment)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)\n";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("[DAO] Inserting booking with ID: " + booking.getBookingId());

            stmt.setInt(1, booking.getBookingId());
            stmt.setInt(2, booking.getCustomerId());
            if (booking.getEmployeeId() < 0) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, booking.getEmployeeId());
            }
            stmt.setDate(4, Date.valueOf(booking.getStartDate()));
            stmt.setDate(5, Date.valueOf(booking.getEndDate()));
            stmt.setString(6, booking.getVehicle().getPlateNo());
            stmt.setBoolean(7, booking.isActive());
            stmt.setDouble(8, booking.getFinalPayment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding booking", e);
        }
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM booking WHERE bookingid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String plateNo = rs.getString("vehicleid");
                Vehicle vehicle = vehicleDAO.getVehicleByPlateNo(plateNo);
                return new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("employeeid"),
                        rs.getDate("startdate").toLocalDate(),
                        rs.getDate("enddate").toLocalDate(),
                        vehicle,
                        rs.getBoolean("active"),
                        rs.getDouble("finalpayment")
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving booking", e);
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String plateNo = rs.getString("vehicleid");
                Vehicle vehicle = vehicleDAO.getVehicleByPlateNo(plateNo);
                bookings.add(new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("employeeid"),
                        rs.getDate("startdate").toLocalDate(),
                        rs.getDate("enddate").toLocalDate(),
                        vehicle,
                        rs.getBoolean("active"),
                        rs.getDouble("finalpayment")
                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving bookings", e);
        }
        return bookings;
    }

    @Override
    public void updateBooking(Booking booking) {
        String sql = "UPDATE booking SET customerid=?, vehicleid=?, employeeid=?, startdate=?, enddate=?, active=?, finalpayment=? WHERE bookingid=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setString(2, booking.getVehicle().getPlateNo());
            if (booking.getEmployeeId() > 0) {
                stmt.setInt(3, booking.getEmployeeId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setDate(4, Date.valueOf(booking.getStartDate()));
            stmt.setDate(5, Date.valueOf(booking.getEndDate()));
            stmt.setBoolean(6, booking.isActive());
            stmt.setDouble(7, booking.getFinalPayment());
            stmt.setInt(8, booking.getBookingId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating booking", e);
        }
    }

    @Override
    public boolean hasBookingsForVehicle(String plateNo) {
        String sql = "SELECT COUNT(*) FROM booking WHERE vehicleid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plateNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking bookings for vehicle", e);
        }
        return false;
    }


    @Override
    public void deleteBooking(int id) {
        String sql = "DELETE FROM booking WHERE bookingid=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting booking", e);
        }
    }
}
