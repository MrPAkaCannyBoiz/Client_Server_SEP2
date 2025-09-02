package Dao;

import java.sql.SQLException;

public class DAOFactory {
    private static VehicleDAO vehicleDAO;
    private static CustomerDAO customerDAO;
    private static BookingDAO bookingDAO;
    private static EmployeeDAO employeeDAO;

    public static VehicleDAO getVehicleDAO() {
        if (vehicleDAO == null) {
            try {
                vehicleDAO = new VehicleDAOImpl();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create VehicleDAO", e);
            }
        }
        return vehicleDAO;
    }

    public static CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            try {
                customerDAO = new CustomerDAOImpl();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create CustomerDAO", e);
            }
        }
        return customerDAO;
    }


    public static BookingDAO getBookingDAO() {
        if (bookingDAO == null) {
            try {
                bookingDAO = new BookingDAOImpl();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create BookingDAO", e);
            }
        }
        return bookingDAO;
    }

    public static EmployeeDAO getEmployeeDAO() {
        if (employeeDAO == null) {
            try {
                employeeDAO = new EmployeeDAOImpl();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create EmployeeDAO", e);
            }
        }
        return employeeDAO;
    }

}
