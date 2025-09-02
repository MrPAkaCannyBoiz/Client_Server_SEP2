import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ModelTest {

  private Model model;
  private Vehicle testVehicle;
  private Vehicle vehicle1;
  private Customer testCustomer;
  private Employee testEmployee;


  @BeforeEach
  void setUp() {
    model = new Model();
    testVehicle = new Car("Toyota", "Corolla", "Red",
        "Hybrid", "AB12345", 50.0, 200.0,
        20.0, 4, 5);
    testCustomer = new Customer("1234567890", "PA1234567",
        "John Doe", "12345678", "john@mail.com",
        new DriverLicense("DL123", true,
            true, true, true,
            true), "Denmark", "2000-01-01");
    testEmployee = new Employee("Alice","Manager");
    vehicle1 = new Car("Toyota", "Corolla", "Blue",
        "Hybrid", "AB54321",
        25.0, 100.0, 50.0, 4, 5);
  }

  // ------------------------ ZERO (Z) CASES ------------------------------
  @Test
  void testInitialStateHasZeroEntities() {
    assertTrue(model.getCustomers().isEmpty(), "No customers initially");
    assertTrue(model.getVehicles().isEmpty(), "No vehicles initially");
    assertTrue(model.getAllBookings().isEmpty(), "No bookings initially");
    assertTrue(model.getEmployees().isEmpty(), "No employees initially");
  }

  // ------------------------ ONE (O) CASES -------------------------------
  @Test
  void testAddOneCustomer() {
    model.addCustomer(testCustomer);
    assertEquals(1, model.getCustomers().size());
  }

  @Test
  void testAddOneVehicle() {
    model.addVehicle(testVehicle);
    assertEquals(1, model.getVehicles().size());
  }

  @Test
  void testAddOneBooking() {
    model.addCustomer(testCustomer);
    model.addVehicle(testVehicle);
    Booking booking = new Booking(1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), testVehicle);
    model.addTheBooking(booking, testCustomer);
    assertEquals(1, model.getAllBookings().size());
  }

  // ------------------------ MANY (M) CASES ------------------------------
  @Test
  void testAddMultipleCustomers() {
    Customer customer2 = new Customer("0987654321", "PB7654321", "Jane", "87654321", "jane@mail.com",
        new DriverLicense("DL456", true, true, true, true, true), "Norway", "1995-05-15");
    model.addCustomer(testCustomer);
    model.addCustomer(customer2);
    assertEquals(2, model.getCustomers().size());
  }

  @Test
  void testMultipleVehiclesWithSameLicenseType() {
    model.addVehicle(testVehicle);
    model.addVehicle(vehicle1);
    assertEquals(2, model.getVehicles().size());
  }

  // ------------------------ BOUNDARIES (B) CASES ------------------------
  @Test
  void testCannotRemoveRentedVehicle() {
    model.addVehicle(testVehicle);
    testVehicle.rentVehicle(testCustomer);
    assertThrows(IllegalArgumentException.class, () -> model.removeVehicle(testVehicle));
  }

  @Test
  void testCannotAddDuplicatePlateNumber() {
    model.addVehicle(testVehicle);
    Vehicle duplicateVehicle = new Car("Toyota", "Corolla",
        "Red", "Hybrid", "AB12345", 50.0,
        200.0, 20.0, 4, 5);
    assertThrows(IllegalArgumentException.class, () ->
        model.addVehicle(duplicateVehicle));
  }


  // ------------------------ EXCEPTIONS (E) CASES ------------------------
  @Test
  void testCannotRentWithoutValidLicense() {
    Customer invalidCustomer = new Customer("1235567890", "PA1234557",
        "Johnny Dopa", "12345678", "john@mail.com",
        new DriverLicense("DL123", true,
            false, true, true,
            true), "Denmark", "2000-01-01");
    model.addCustomer(testCustomer);
    model.addVehicle(testVehicle);
    assertThrows(IllegalArgumentException.class, () ->
        model.makeBookingByEmployee(LocalDate.now(), LocalDate.now().plusDays(1),
            testEmployee, invalidCustomer, testVehicle)
    );
  }

  // ------------------------ SIMPLE (S) CASES ----------------------------
  @Test
  void testRentAndReturnVehicleSuccessfully() {
    model.addCustomer(testCustomer);
    model.addVehicle(testVehicle);
    model.makeBookingByCustomer(LocalDate.now(), LocalDate.now().plusDays(3),
        testCustomer, testVehicle);
    assertEquals("Rented", testVehicle.getStatus());
    
    model.returnBookedVehicleAndPayForEmployee(testVehicle);
    assertEquals("Available", testVehicle.getStatus());
  }

}