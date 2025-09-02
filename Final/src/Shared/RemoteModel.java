package Shared;

import Model.*;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.Remote;
import java.time.LocalDate;
import java.util.List;

public interface RemoteModel {

  // Vehicle related
  void addVehicle(Vehicle vehicle);
  void removeVehicle(Vehicle vehicle);
  List<Vehicle> getVehicles();

  // Customer related
  void addCustomer(Customer customer);
  void removeCustomer(Customer customer);
  void checkIfCustomerInfoIsUnique(Customer customer);
  List<Customer> getCustomers();
// Employee related
  void addEmployees(Employee employee) throws IOException;
  void removeEmployees(Employee employee) throws IOException;
  List<Employee> getEmployees();

  // Booking related

  void makeBookingByCustomer(LocalDate startDate, LocalDate endDate,
      Customer customerWhoBooked, Vehicle vehicleToBook);
  void makeBookingByEmployee(LocalDate startDate, LocalDate endDate,
      Employee employeeWhoBook, Customer customerWhoBooked, Vehicle selectedVehicle) throws IOException;
  void cancelBooking(Booking bookingToCancel, Customer customerWhoBooked) ;
  void cancelBookingWithByPassing(Booking booking);
  void returnBookedVehicleAndPay(Vehicle vehicle, Customer customer);
  void returnBookedVehicleAndPayForEmployee(Vehicle vehicle);
  List<Booking> getAllBookings();


  void addPropertyChangeListener(String eventName, PropertyChangeListener listener);
  void removePropertyChangeListener(String eventName, PropertyChangeListener listener);

  void firePropertyForCurrentCustomer(Customer selectedCustomer);
  void firePropertyForSelectedVehicle(Vehicle chosenVehicle);
  void firePropertyForCustomer();
  void firePropertyForCurrentEmployee(Employee selectedEmployee);
  void rentTheVehicle(Vehicle vehicle, Customer customer);
  void returnTheVehicle(Vehicle vehicle, Customer customer);
  void firePropertyForAllCustomerBooking();
  void updateCustomer(Customer oldCustomer, Customer editedCustomer);

}
