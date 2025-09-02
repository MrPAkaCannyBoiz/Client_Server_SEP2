import Model.Car;
import Model.Customer;
import Model.DriverLicense;
import Model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

  private Vehicle vehicle;
  private Vehicle vehicle1;

  @BeforeEach
  void setUp() {
    vehicle = new Car("Toyota", "Corolla", "Red",
        "Hybrid", "AB12345",
        25.0, 100.0, 50.0, 4, 5);
    vehicle1 = new Car("Toyota", "Corolla", "Blue",
        "Hybrid", "AB54321",
        25.0, 100.0, 50.0, 4, 5);
  }

  @Test
  void testValidConstruction() {
    assertEquals("Corolla", vehicle.getModel());
    assertEquals("AB12345", vehicle.getPlateNo());
  }

  //Zero (Z)
  @Test
  void testNewVehicleIsAvailable() {
    assertEquals("Available", vehicle.getStatus(),
        "New vehicle should be available");
  }

  //Zero (Z)
  @Test
  void testVehicleNotRentedByDefault() {
    assertNull(vehicle.getRentedByCustomer(),
        "No customer should be assigned initially");
  }


  // Boundaries (B) - Exceptions (E)
  @Test
  void testInvalidPlateThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Vehicle("Brand", "Model", "Color", "Engine", "123",
          25.0, 100.0, 50.0, 4, 5, "B", "Car");
    });
  }

  // Boundaries (B) - Exceptions (E)
  @Test
  void testEmptyBrandThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Car("", "Model", "Color", "Engine",
          "AB12345",
          25.0, 100.0, 50.0, 4, 5);
    });
  }

  // Boundaries (B) - Exceptions (E)
  @Test
  void testZeroPriceThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Car("Brand", "Model", "Color", "Engine",
          "AB12345",
          0.0, 100.0, 50.0, 4, 5);
    });
  }

  //One (O)
  @Test
  void testRentVehicleAssignsCustomer() {
    Customer dummy = new Customer("1234567801", "pa2212322",
        "Dominus", "888888", "test@mail.com",
        new DriverLicense( "1273172312", true,
            true, true,true,true),
        "Italy", "2000-12-12");
    assertEquals(null,vehicle.getRentedByCustomer());
    vehicle.rentVehicle(dummy);
    assertEquals(dummy, vehicle.getRentedByCustomer());
  }

  //One (O) - Zero(O)
  @Test
  void testReturnVehicleClearsCustomer() {
    Customer dummy = new Customer("1234567801", "pa2212322",
        "Dominus", "888888", "test@mail.com",
        new DriverLicense("1273172312", true,
            true, true, true,true),
        "Italy", "2000-12-12");
    vehicle.rentVehicle(dummy);
    vehicle.returnVehicle(dummy);
    assertNull(vehicle.getRentedByCustomer());
  }

}
