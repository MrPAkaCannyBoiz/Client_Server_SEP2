package Client;

import Model.*;
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
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class ClientSocketHandler implements RemoteModel, PropertyChangeSubject
{
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private PropertyChangeSupport support = new PropertyChangeSupport(this);
  private final BlockingQueue<Object> replyQueue = new ArrayBlockingQueue<>(10);

  public ClientSocketHandler(String host, int port) throws IOException
  {
    this.socket = new Socket(host, port);
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.out.flush();
    this.in = new ObjectInputStream(socket.getInputStream());
    System.out.println("[Client] Connected to server at " + host + ":" + port);
    new Thread(() ->
    {
      try
      {
        while (true)
        {
          Object obj = in.readObject();

          if (obj instanceof PropertyChangeEvent evt)
          {
            // broadcast ⇒ hand directly to GUI listeners
            Platform.runLater(() ->
                    support.firePropertyChange(
                            evt.getPropertyName(),
                            evt.getOldValue(),
                            evt.getNewValue())
            );
          }
          else
          {
            // never put null in the queue:
            replyQueue.put(obj == null ? Void.TYPE : obj);
          }
        }
      } catch (Exception e) {
        System.err.println("[Client] reader stopped: " + e.getMessage());
      }
    }, "socket-reader").start();
  }

  @Override
  public void updateCustomer(Customer oldCustomer, Customer editedCustomer) {
    sendCommand(new Server.Command.UpdateCustomerCommand(oldCustomer, editedCustomer));
  }


  private <T> T sendCommand(RemoteCommand<T> cmd)
  {
    try {
      synchronized (out) {          // writer side must stay serialised
        out.writeObject(cmd);
        out.flush();
      }
      @SuppressWarnings("unchecked")
      T reply = (T) replyQueue.take();
      return reply == Void.TYPE ? null : reply;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("RPC failed", e);
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

  @Override
  public List<Employee> getEmployees() {
    return sendCommand(new Server.Command.GetEmployeesCommand());
  }

  @Override
  public List<Booking> getAllBookings() {
    return sendCommand(new Server.Command.GetAllBookingsCommand());

  }

  @Override
  public void makeBookingByCustomer(LocalDate startDate, LocalDate endDate,
      Customer customer, Vehicle vehicle) {
    sendCommand(new Server.Command.MakeBookingByCustomerCommand
        (startDate, endDate, customer, vehicle));
  }

  @Override
  public void returnBookedVehicleAndPay(Vehicle vehicle, Customer customer) {
    sendCommand(new Server.Command.ReturnBookedVehicleAndPayCommand
        (vehicle, customer));
  }

  @Override
  public void addCustomer(Customer customer)
  {
    sendCommand(new AddCustomerCommand(customer));
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
  public void cancelBookingWithByPassing(Booking booking) {
    sendCommand(new Server.Command.CancelBookingWithBypassingCommand(booking));
  }

  @Override
  public void cancelBooking(Booking booking, Customer customer) {
    sendCommand(new Server.Command.CancelBookingCommand(booking, customer));
  }

  @Override
  public void returnBookedVehicleAndPayForEmployee(Vehicle vehicle) {
    sendCommand(new Server.Command.ReturnBookedVehicleAndPayForEmployeeCommand(vehicle));
  }


  @Override
  public void makeBookingByEmployee(LocalDate startDate, LocalDate endDate, Employee employee, Customer customer, Vehicle vehicle) throws IOException {
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
  public void removePropertyChangeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }

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
    List<Booking> updatedBookings = getAllBookings();
    Platform.runLater(() ->
        support.firePropertyChange("AllCustomerBookingEvent", null, updatedBookings)
    );
  }
}

