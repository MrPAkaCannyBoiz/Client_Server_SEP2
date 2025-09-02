import Model.Booking;
import Model.Car;
import Model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
  private Vehicle testVehicle;

  @BeforeEach
  void setUp() {
    testVehicle = new Vehicle("TestBrand", "TestModel",
        "Black", "Diesel", "AB12345",
        100.0, 500.0, 20.0, 4,
        4, "B", "Car");
  }

  // Zero (Z)
  @Test
  void testNewBookingHasZeroFinalPayment() {
    Booking booking = new Booking(1, LocalDate.now()
        .plusDays(1),
        LocalDate.now().plusDays(3), testVehicle);
    assertEquals(0.0, booking.getFinalPayment(),
        "Final payment should initialize to 0");
  }

  // Zero (Z) - boundary (B)
  @Test
  void testBookingWithSameStartAndEndDate() {
    LocalDate date = LocalDate.now().plusDays(1);
    // Ensure endDate >= startDate (valid)
    Booking booking = new Booking(1, date, date, testVehicle);
    assertEquals(date, booking.getStartDate());
    assertEquals(date, booking.getEndDate());
  }

  //Many (M)
  @Test
  void testMultipleBookingsForSameVehicle() {
    Vehicle vehicle = new Car("Toyota", "Corolla", "Red",
        "Hybrid", "AB12345",
        25.0, 100.0, 50.0, 4, 5);
    Booking booking1 = new Booking(1, LocalDate.now().
        plusDays(1), LocalDate.now().plusDays(2), vehicle);
    booking1.setToInActiveInstantly();
    Booking booking2 = new Booking(2, LocalDate.now().
        plusDays(3), LocalDate.now().plusDays(4), vehicle);

    // Ensure vehicle is associated with both bookings
    assertEquals(vehicle, booking1.getVehicle());
    assertEquals(vehicle, booking2.getVehicle());
  }

  // Simple (S)
  @Test
  void shouldCreateBookingSuccessfully() {
    Booking booking = new Booking(1,
        LocalDate.parse("2025-07-09"),
        LocalDate.parse("2025-07-12"), testVehicle);
    assertEquals(LocalDate.parse("2025-07-09"), booking.getStartDate());
    assertEquals(LocalDate.parse("2025-07-12"), booking.getEndDate());
    assertEquals(testVehicle, booking.getVehicle());
  }

  //E - Exception
  @Test
  void shouldThrowExceptionWhenStartDateIsInPast() {
    assertThrows(IllegalArgumentException.class, () ->
        new Booking(1, LocalDate.parse("2025-05-07"),
            LocalDate.parse("2025-05-12"), testVehicle));
  }

  //E - Exception
  @Test
  void shouldThrowExceptionWhenEndDateBeforeStartDate() {
    assertThrows(IllegalArgumentException.class, () ->
        new Booking(1, LocalDate.parse("2025-05-12"),
            LocalDate.parse("2025-05-09"), testVehicle));
  }

  // One (O)
  @Test
  void shouldSetVehicleSuccessfully() {
    Booking booking = new Booking(1,
        LocalDate.parse("2026-05-09"),
        LocalDate.parse("2026-05-12"), testVehicle);
    Vehicle newVehicle = new Vehicle("BrandX", "XModel",
        "Blue", "Electric", "XY12345",
        150.0, 700.0, 30.0, 4, 4,
        "C", "SUV");
    booking.setVehicle(newVehicle);
    assertEquals(newVehicle, booking.getVehicle());
  }

  // One (O)
  @Test
  void shouldBecomeInactiveAfterMethodCall() {
    Booking booking = new Booking(1,
        LocalDate.parse("2026-07-09"),
        LocalDate.parse("2026-07-12"), testVehicle);
    booking.setToInActiveInstantly();
    assertFalse(booking.isActive());
  }


  // Simple (S)
  @Test
  void toStringShouldContainVehicleDetails() {
    Booking booking = new Booking(1,
        LocalDate.parse("2026-05-09"),
        LocalDate.parse("2026-05-12"), testVehicle);
    assertTrue(booking.toString().contains("Booking ID:"));
    assertTrue(booking.toString().contains("vehicle="));
  }
}
