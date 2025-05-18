package Dao.test;

import Dao.*;
import Model.Employee;
import Model.Booking;
import Model.Customer;
import Model.DriverLicense;
import Model.Vehicle;

import java.sql.SQLException;
import java.time.LocalDate;

public class FullDAOIntegrationTest {

    public static void main(String[] args) {
        System.out.println("🚀 Start of FullDAOIntegrationTest...");

        // 🔄 1. clear DB
        DatabaseClearUtil.clearAllTables();

        try {
            // === 2. DRIVER LICENSE ===
            DriverLicenseDAO driverLicenseDAO = new DriverLicenseDAOImpl();
            DriverLicense license = new DriverLicense("DL99999", true, false, false, true, false);
            driverLicenseDAO.addDriverLicense(license);
            System.out.println("✅ Driver license added.");

            // === 3. CUSTOMER ===
            CustomerDAO customerDAO = new CustomerDAOImpl();
            Customer customer = new Customer(
                    101,
                    "111111-2222",
                    "",
                    "Alicja Testowa",
                    "12345678",
                    "alicja@example.com",
                    license,
                    "Polish",
                    "1985-05-01"
            );
            customerDAO.addCustomer(customer);
            System.out.println("✅ Customer added.");

            // === 4. EMPLOYEE ===
            EmployeeDAO employeeDAO = new EmployeeDAOImpl();
            Employee employee = new Employee(11, "Bartek Pracownik", "Admin");
            employeeDAO.addEmployee(employee);
            System.out.println("✅ Employee added.");

            // === 5. VEHICLE ===
            VehicleDAO vehicleDAO = new VehicleDAOImpl();
            Vehicle vehicle = new Vehicle(
                    "Ford",
                    "Focus",
                    "Red",
                    "Petrol",
                    "AB12345",
                    45.0,
                    300.0,
                    4.0,
                    4,
                    5,
                    "B",
                    "car"
            );
            vehicleDAO.addVehicle(vehicle);
            System.out.println("✅ Vehicle added.");

            // === 6. BOOKING ===
            BookingDAO bookingDAO = new BookingDAOImpl();
            Booking booking = new Booking(
                    501,
                    101,
                    11,
                    LocalDate.now().plusDays(2),
                    LocalDate.now().plusDays(5),
                    vehicle
            );
            bookingDAO.addBooking(booking);
            System.out.println("✅ Booking added.");

            // === 7. Walidacja pobrań ===
            Customer fetchedCustomer = customerDAO.getCustomerById(101);
            Vehicle fetchedVehicle = vehicleDAO.getVehicleByPlateNo("AB12345");
            Booking fetchedBooking = bookingDAO.getBookingById(501);
            Employee fetchedEmployee = employeeDAO.getEmployeeById(11);

            System.out.println("🔎 VALIDATION:");
            System.out.println("📌 Customer: " + (fetchedCustomer != null ? fetchedCustomer : "❌ Not Found"));
            System.out.println("📌 Vehicle: " + (fetchedVehicle != null ? fetchedVehicle : "❌ Not Found"));
            System.out.println("📌 Booking: " + (fetchedBooking != null ? fetchedBooking : "❌ Not Found"));
            System.out.println("📌 Employee: " + (fetchedEmployee != null ? fetchedEmployee : "❌ Not Found"));

            System.out.println("🎉 TEST FINISHED");

        } catch (SQLException e) {
            System.err.println("❌ ERROR during DAO integration test:");
            e.printStackTrace();
        }
    }
}
