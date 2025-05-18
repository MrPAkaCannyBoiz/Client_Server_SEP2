package Client;

import Model.Customer;
import Model.Employee;
import Model.PropertyChangeSubject;
import Model.Vehicle;
import Server.Command.AddCustomerCommand;
import Server.Command.RemoteCommand;
import Shared.RemoteModel;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientSocketHandler implements RemoteModel, PropertyChangeSubject
{
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private PropertyChangeSupport support = new PropertyChangeSupport(this);

  public ClientSocketHandler(String host, int port) throws IOException
  {
    this.socket = new Socket(host, port);
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.out.flush();
    this.in = new ObjectInputStream(socket.getInputStream());
    System.out.println("[Client] Connected to server at " + host + ":" + port);
//    new Thread(() -> {
//      try {
//        while (true) {
//          Object obj = in.readObject();
//          if (obj instanceof PropertyChangeEvent event) {
//            Platform.runLater(() ->
//                support.firePropertyChange(
//                    event.getPropertyName(),
//                    event.getOldValue(),
//                    event.getNewValue()
//                )
//            );
//          }
//        }
//      } catch (Exception e) {
//        System.err.println("[Client] Listener error: " + e.getMessage());
//      }
//    }).start();
  }

  private synchronized <T> T sendCommand(RemoteCommand<T> command) {
    try {
      System.out.println("[Client] Sending test command to server...");
      out.writeObject(command);
      out.flush();
      System.out.println("[Client] Test command sent.");
      Object response = in.readObject();
      return (T) response;
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException("Error sending command to server", e);
    }
  }

  // ======= RemoteModel method implementations using Commands =======

  @Override
  public void addVehicle(Vehicle vehicle) {
    sendCommand(new Server.Command.AddVehicleCommand(vehicle));
  }

  @Override
  public void removeVehicle(Vehicle vehicle) {
    sendCommand(new Server.Command.RemoveVehicleCommand(vehicle));
  }

  @Override
  public List<Vehicle> getVehicles() {
    return sendCommand(new Server.Command.GetVehiclesCommand());
  }

  @Override
  public List<Customer> getCustomers() {
    return sendCommand(new Server.Command.GetCustomerCommand());
  }

  @Override public void addEmployee(Employee employee)
  {
 sendCommand(new Server.Command.AddEmployeeCommand(employee));
  }

  @Override public void removeEmployee(Employee employee)
  {
sendCommand(new Server.Command.AddEmployeeCommand(employee));
  }

  @Override
  public List<Employee> getEmployees() {
    return sendCommand(new Server.Command.GetEmployeesCommand());
  }

  @Override
  public List<Model.Booking> getAllBookings() {
    return sendCommand(new Server.Command.GetAllBookingsCommand());
  }

  @Override
  public void makeBookingByCustomer(java.time.LocalDate startDate, java.time.LocalDate endDate, Customer customer, Vehicle vehicle) {
    sendCommand(new Server.Command.MakeBookingByCustomerCommand(startDate, endDate, customer, vehicle));
  }

  @Override
  public void returnBookedVehicleAndPay(Vehicle vehicle, Customer customer) {
    sendCommand(new Server.Command.ReturnBookedVehicleAndPayCommand(vehicle, customer));
  }

  @Override
  public void addCustomer(Customer customer) {
    List<Customer> updatedList = (List<Customer>) sendCommand(new AddCustomerCommand(customer));
    Platform.runLater(() -> {
      support.firePropertyChange("CustomerEvent", null, updatedList);
    });
  }

  @Override
  public void removeCustomer(Customer customer) {
    sendCommand(new Server.Command.RemoveCustomerCommand(customer));
  }

  @Override
  public void checkIfCustomerInfoIsUnique(Customer customer) {
    sendCommand(new Server.Command.CheckIfCustomerIsUniqueCommand(customer));
  }

  @Override
  public void addEmployees(Employee employee) {
    sendCommand(new Server.Command.AddEmployeeCommand(employee));
  }

  @Override
  public void removeEmployees(Employee employee) {
    sendCommand(new Server.Command.RemoveEmployeeCommand(employee));
  }

  @Override
  public void cancelBookingWithByPassing(Model.Booking booking) {
    sendCommand(new Server.Command.CancelBookingWithBypassingCommand(booking));
  }

  @Override
  public void cancelBooking(Model.Booking booking, Customer customer) {
    sendCommand(new Server.Command.CancelBookingCommand(booking, customer));
  }

  @Override
  public void returnBookedVehicleAndPayForEmployee(Vehicle vehicle) {
    sendCommand(new Server.Command.ReturnBookedVehicleAndPayForEmployeeCommand(vehicle));
  }


  @Override
  public void makeBookingByEmployee(java.time.LocalDate startDate, java.time.LocalDate endDate, Employee employee, Customer customer, Vehicle vehicle) throws IOException {
    sendCommand(new Server.Command.MakeBookingByEmployeeCommand(startDate, endDate, employee, customer, vehicle));
  }

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  // -- PropertyChangeListener-related methods can be left empty or stubbed if unused --
  @Override public void addPropertyChangeListener(String eventName,
      PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName, listener);
  }

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(String eventName, PropertyChangeListener listener) {}

  @Override public void firePropertyForCurrentCustomer(
      Customer selectedCustomer)
  {
    support.firePropertyChange("CurrentCustomerEvent", null, selectedCustomer);
  }

  @Override public void firePropertyForSelectedVehicle(Vehicle chosenVehicle)
  {
    support.firePropertyChange("SelectedVehicleEvent", null, chosenVehicle);
  }

  @Override public void firePropertyForCustomer()
  {
    support.firePropertyChange("CustomerEvent", null, getCustomers());
  }

  @Override public void firePropertyForCurrentEmployee(
      Employee selectedEmployee)
  {
    support.firePropertyChange("CurrentEmployeeEvent", null, selectedEmployee);
  }

  @Override public void rentTheVehicle(Vehicle vehicle, Customer customer)
  {
    support.firePropertyChange("VehicleEvent", null, getVehicles());
  }

  @Override public void returnTheVehicle(Vehicle vehicle, Customer customer)
  {
    support.firePropertyChange("VehicleEvent", null, getVehicles());
  }

  @Override public void firePropertyForAllCustomerBooking()
  {
    List<Model.Booking> updatedBookings = getAllBookings();
    Platform.runLater(() ->
        support.firePropertyChange("AllCustomerBookingEvent", null, updatedBookings)
    );
  }
}

