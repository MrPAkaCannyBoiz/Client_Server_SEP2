import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

  private DriverLicense validLicense;

  @BeforeEach
  void setUp() {
    validLicense = new DriverLicense("1273172312", true
        , true, true, true, true);
  }

  // Zero (Z)
  @Test
  void testNewCustomerHasZeroBookings() {
    Customer customer = new Customer("1234567890", "pa2212322",
        "Alice", "12345678", "alice@mail.com",
        validLicense, "Denmark", "2000-01-01");
    assertTrue(customer.getBookingList().isEmpty(),
        "New customer should have 0 bookings");
  }

  // Many (M)
  @Test
  void testAddAndRemoveMultipleBookings() {
    Customer customer = new Customer("1234567890", "pa2212322",
        "Alice", "12345678", "alice@mail.com",
        validLicense, "Denmark", "2000-01-01");
    Vehicle vehicle1 = new Car("Toyota", "Corolla", "Blue",
        "Hybrid", "AB54321",
        25.0, 100.0, 50.0, 4, 5);
    Vehicle vehicle = new Car("Toyota", "Corolla", "Red",
        "Hybrid", "AB12345",
        25.0, 100.0, 50.0, 4, 5);

    Booking booking1 = new Booking(1,
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(2), vehicle1);
    Booking booking2 = new Booking(2,
        LocalDate.now().plusDays(3),
        LocalDate.now().plusDays(4), vehicle);

    customer.addBooking(booking1);
    customer.addBooking(booking2);
    assertEquals(2, customer.getBookingList().size(),
        "Should have 2 bookings");

    customer.removeBooking(booking1);
    assertEquals(1, customer.getBookingList().size(),
        "Should have 1 booking after removal");
  }


  // Simple (S)
  @Test
  void shouldCreateCustomerSuccessfully() {
    Customer customer = new Customer("1234567890", "pa2212322",
        "John Doe", "12345678", "john@mail.com",
        validLicense, "Denmark", "2000-01-01");

    assertEquals("John Doe", customer.getName());
    assertEquals("1234567890", customer.getCpr());
    assertEquals("pa2212322", customer.getPassNo());
    assertEquals(LocalDate.parse("2000-01-01"), customer.getDob());
    assertEquals(validLicense, customer.getDriverLicense());
  }

  // Boundaries (B) - Exceptions (E)
  @Test
  void shouldThrowExceptionWhenNameIsEmpty() {
    assertThrows(IllegalArgumentException.class, () ->
        new Customer("1234567890", "pa2212322", "",
            "12345678", "john@mail.com",
            validLicense, "Denmark", "2000-01-01")
    );
  }

  // Boundaries (B) - Exceptions (E)
  @Test
  void shouldThrowExceptionWhenDateFormatIsWrong() {
    assertThrows(IllegalArgumentException.class, () ->
        new Customer("1234567890", "pa2212322", "John",
            "12345678", "john@mail.com",
            validLicense, "Denmark", "01-01-2000")
    );
  }

  // Boundaries (B) - Exceptions (E)
  @Test
  void shouldThrowExceptionWhenCPRAndPassportInvalid() {
    assertThrows(IllegalArgumentException.class, () ->
        new Customer("", "", "John", "12345678",
            "john@mail.com",
            validLicense, "Denmark", "2000-01-01")
    );
  }

  // Zero (Z) - One(O)
  @Test
  void testAddAndRemoveBooking() {
    Customer customer = new Customer("1234567890", "pa2212326",
        "Jane", "87654321", "jane@mail.com",
        validLicense, "Norway", "1995-05-15");

    Vehicle dummyVehicle = new Car("Toyota", "Yaris", "White"
        , "Hybrid", "AB12345",
        30.0, 200.0, 15.0, 4, 5);

    Booking booking = new Booking(1,
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(3), dummyVehicle);

    customer.addBooking(booking);
    assertEquals(1, customer.getBookingList().size());

    customer.removeBooking(booking);
    assertTrue(customer.getBookingList().isEmpty());
  }

  // One (O)
  @Test
  void shouldDetectCPRAndPassportPresence() {
    Customer customer = new Customer("1234567890", "pa2212326",
        "Ana", "00000000", "baba@mail.com",
        validLicense, "Brazil", "1990-10-10");

    assertTrue(customer.hasCPR());
    assertTrue(customer.hasPassport());
  }
}
