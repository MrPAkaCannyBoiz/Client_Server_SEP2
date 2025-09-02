import Dao.CustomerDAO;
import Model.Customer;
import Model.DriverLicense;
import Model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelUpdateCustomerTest
{

  private Model model;
  private Customer oldCustomer;
  private ArrayList<Customer> customers;
  private CustomerDAO mockCustomerDao;

  @BeforeEach
  void setup() throws Exception {
    model = new Model();

    var customersField = Model.class.getDeclaredField("customers");
    customersField.setAccessible(true);
    customers = (ArrayList<Customer>) customersField.get(model);
    customers.clear();

    DriverLicense license = new DriverLicense
        ("LIC-123", true, true,
            true, true, true);
    oldCustomer = new Customer("1122334455", "PP555555", "Original Name",
        "12345678", "original@mail.com", license,
        "Denmark", "2000-01-01");
    customers.add(oldCustomer);
  }

  @Test
  void updateCustomer_validChanges_updatesSuccessfully() {
    // Arrange
    DriverLicense license = new DriverLicense
        ("LIC-123", true, true,
            true, true, true);
    Customer editedCustomer = new Customer("1122334455",
        "PP555555","New Name",
        "87654321","new@mail.com",license,"Denmark"
        ,"2000-01-01");

    // Act & Assert
    assertDoesNotThrow(() -> model.updateCustomer(oldCustomer, editedCustomer));

    // Verify field updates
    assertEquals("New Name", oldCustomer.getName());
    assertEquals("87654321", oldCustomer.getPhoneNo());
    assertEquals("new@mail.com", oldCustomer.getEmail());
  }

  @Test
  void updateCustomer_duplicateEmail_throwsException() {
    // Arrange
    Customer duplicateCustomer = createSampleCustomer(2, "duplicate@mail.com");
    customers.add(duplicateCustomer);
    Customer editedCustomer = createEditedCustomer("duplicate@mail.com",
        "1122334455", "new123456", "NEW-12345");

    // Act & Assert
    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> model.updateCustomer(oldCustomer, editedCustomer)
    );
    assertEquals("This email is already used", ex.getMessage());
  }

  @Test
  void updateCustomer_duplicateCpr_throwsException() {
    // Arrange
    Customer duplicateCustomer = createSampleCustomer(2, "some@mail.com");
    customers.add(duplicateCustomer);
    Customer editedCustomer = createEditedCustomer("new@mail.com",
        "CPR-DUPE", "new123456", "NEW-12345");

    // Act & Assert
    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> model.updateCustomer(oldCustomer, editedCustomer)
    );
    assertEquals("This CPR is already used", ex.getMessage());
  }

  @Test
  void updateCustomer_nonexistentCustomer_throwsException() {
    // Arrange
    Customer nonExisting = createSampleCustomer(999, "ghost@mail.com");
    Customer anyEdit = createEditedCustomer("any@mail.com", "123456789",
        "new123456", "NEW-12345");

    // Act & Assert
    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> model.updateCustomer(nonExisting, anyEdit)
    );
    assertTrue(ex.getMessage().contains("Original customer not found"));
  }

  // Helper methods
  private Customer createEditedCustomer(String email, String cpr, String passNo, String licenseNo) {
    DriverLicense newLicense = new DriverLicense(licenseNo, true, true, true, true, true);
    return new Customer(cpr, passNo, "New Name", "87654321", email, newLicense, "Norway", "1995-05-15");
  }

  private Customer createSampleCustomer(int id, String email) {
    DriverLicense license = new DriverLicense("LIC-" + id, true, true, true, true, true);
    return new Customer("CPR-" + id, "PASS-" + id, "Customer " + id,
        "1111111" + id, email, license, "Country", "2000-01-01");
  }
}