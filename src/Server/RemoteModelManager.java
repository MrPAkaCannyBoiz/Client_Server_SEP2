package Server;

import Model.*;
import Shared.RemoteModel;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RemoteModelManager implements RemoteModel
{
  private RemoteModel model;

  public RemoteModelManager(RemoteModel model){
    this.model=model;
  }

  @Override public void addVehicle(Vehicle vehicle)
  {
    model.addVehicle(vehicle);
  }

  @Override public void removeVehicle(Vehicle vehicle)
  {
    model.removeVehicle(vehicle);
  }

  @Override public List<Vehicle> getVehicles()
  {
    return model.getVehicles();
  }

  @Override public List<Customer> getCustomers()
  {
    return model.getCustomers();
  }

  @Override public void addEmployee(Employee employee)
  {
    model.addEmployee(employee);
  }

  @Override public List<Employee> getEmployees()
  {
    return model.getEmployees();
  }


  @Override public List<Booking> getAllBookings()
  {
    return model.getAllBookings();
  }


  @Override public void addPropertyChangeListener(String eventName,
      PropertyChangeListener listener)
  {
    model.addPropertyChangeListener(eventName, listener);
  }

  @Override public void removePropertyChangeListener(String eventName,
      PropertyChangeListener listener)
  {
  model.removePropertyChangeListener(eventName,listener);
  }

  @Override public void firePropertyForCurrentCustomer(
      Customer selectedCustomer)
  {
    model.firePropertyForCurrentCustomer(selectedCustomer);
  }

  @Override public void firePropertyForSelectedVehicle(Vehicle chosenVehicle)
  {
model.firePropertyForSelectedVehicle(chosenVehicle);
  }

  @Override public void firePropertyForCustomer()
  {
model.firePropertyForCustomer();
  }

  @Override public void firePropertyForCurrentEmployee(
      Employee selectedEmployee)
  {
model.firePropertyForCurrentEmployee(selectedEmployee);
  }

  @Override public void rentTheVehicle(Vehicle vehicle, Customer customer)
  {
  model.rentTheVehicle(vehicle,customer);
  }

  @Override public void returnTheVehicle(Vehicle vehicle,
      Customer customer)
  {
  model.returnTheVehicle(vehicle,customer);
  }

  @Override public void returnBookedVehicleAndPayForEmployee(
      Vehicle vehicle)
  {
model.returnBookedVehicleAndPayForEmployee(vehicle);
  }

  @Override public void returnBookedVehicleAndPay(Vehicle vehicle,
      Customer customer)
  {
model.returnBookedVehicleAndPay(vehicle,customer);
  }

  @Override public void cancelBookingWithByPassing(Booking booking)
  {
model.cancelBookingWithByPassing(booking);
  }


  @Override public void makeBookingByEmployee(LocalDate startDate, LocalDate endDate,
      Employee employeeWhoBook, Customer customerWhoBooked, Vehicle selectedVehicle)
      throws IOException
  {
  model.makeBookingByEmployee(startDate,endDate,employeeWhoBook,customerWhoBooked,selectedVehicle);
  }

  @Override public void cancelBooking(Booking bookingToCancel, Customer customerWhoBooked)
  {
model.cancelBooking(bookingToCancel,customerWhoBooked);
  }

  @Override public void makeBookingByCustomer(LocalDate startDate, LocalDate endDate,
      Customer customerWhoBooked, Vehicle vehicleToBook)
  {
model.makeBookingByCustomer(startDate,endDate,customerWhoBooked,vehicleToBook);
  }

  @Override public void removeEmployees(Employee employee)
      throws IOException
  {
 model.removeEmployees(employee);
  }

  @Override public void addEmployees(Employee employee) throws IOException
  {
model.addEmployees(employee);
  }


  @Override public void removeEmployee(Employee employee) throws IOException
  {
    model.removeEmployees(employee);
  }

  @Override public void checkIfCustomerInfoIsUnique(Customer customer)
  {
  model.checkIfCustomerInfoIsUnique(customer);
  }

  @Override public void removeCustomer(Customer customer)
  {
model.removeCustomer(customer);
  }

  @Override public void addCustomer(Customer customer)
  {
model.addCustomer(customer);
  }
  @Override
  public void firePropertyForAllCustomerBooking() {
    model.firePropertyForAllCustomerBooking();
  }
}