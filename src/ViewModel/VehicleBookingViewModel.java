package ViewModel;


import Model.*;
import Shared.RemoteModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class VehicleBookingViewModel
{
  private RemoteModel model;
  private ObservableList<Vehicle> vehicles;
  private ObservableList<Booking> bookings;
  private ObservableList<Customer> customers;
  private ObjectProperty<Customer> currentCustomer;
  private ObservableList<Booking> bookingsOnCurrentCustomer;
  private Customer selectedCustomer; // Tracked client-side
  private Vehicle selectedVehicle;// Tracked client-side
  private Employee selectedEmployee;// Tracked client-side

  public VehicleBookingViewModel(RemoteModel model)
  {
    this.model = model;
    DriverLicense emptyLicense =
        new DriverLicense("EMPTY123" ,false,false,false,false,false);
    this.selectedCustomer =
        new Customer(0, "0000000000","111111111","failed",
            "321321","dupa1112",emptyLicense,"idk","1000-12-11");
    this.selectedEmployee = new Employee("Failed","sadness");
    //  testing filter
    this.vehicles = FXCollections.observableArrayList(model.getVehicles());
    this.bookings = FXCollections.observableArrayList(model.getAllBookings());
    this.customers = FXCollections.observableArrayList(model.getCustomers());
    this.currentCustomer = new SimpleObjectProperty<Customer>(selectedCustomer);
    this.bookingsOnCurrentCustomer = FXCollections.observableArrayList(selectedCustomer.getBookingList());

    model.addPropertyChangeListener("VehicleEvent",this::updateVehicle);
    model.addPropertyChangeListener("BookingEvent",this::updateCurrentCustomerBooking);
    model.addPropertyChangeListener("CustomerEvent",this::updateCustomer);
    model.addPropertyChangeListener("AllCustomerBookingEvent",this::updateBooking);
    model.addPropertyChangeListener("CurrentCustomerEvent", this::updateCurrentCustomer);
  }


  public ObservableList<Vehicle> getVehicles()
  {
    return vehicles;
  }

  public ObservableList<Booking> getBookings()
  {
    return bookings;
  }

  public ObservableList<Customer> getCustomers()
  {
    return customers;
  }

  public ObservableList<Booking> getBookingsOnCurrentCustomer()
  {
    return bookingsOnCurrentCustomer;
  }

  public ObjectProperty<Customer> currentCustomerProperty()
  {
    return currentCustomer;
  }

  public RemoteModel getModel()
  {
    return model;
  }

  public void removeVehicle(Vehicle vehicleToRemove)
  {
    model.removeVehicle(vehicleToRemove);
  }

  public void returnVehicleByEmployee(Vehicle vehicleToReturn)
  {
    model.returnBookedVehicleAndPayForEmployee(vehicleToReturn);
  }

  public void returnBookedVehicleAndPay(Vehicle vehicleToReturn)
  {
    model.returnBookedVehicleAndPay(vehicleToReturn,selectedCustomer);
  }

  public void cancelBookingForCurrentCustomer(Booking bookingToCancel)
  {
    model.cancelBooking(bookingToCancel,selectedCustomer);
  }

  public void cancelBookingForEmployeeUses(Booking booking, Customer customer)
  {

  }

  public void firePropertyForCustomer()
  {
    model.firePropertyForCustomer();
  }


  public void updateVehicle(PropertyChangeEvent event)
  {
    ArrayList<Vehicle> newVehicles = (ArrayList<Vehicle>) event.getNewValue();
    Platform.runLater(() -> vehicles.setAll(newVehicles));
  }

  public void updateBooking(PropertyChangeEvent event)
  {
    ArrayList<Booking> newBookings = (ArrayList<Booking>) event.getNewValue();
    Platform.runLater(() -> bookings.setAll(newBookings));
  }

  public void updateCurrentCustomerBooking(PropertyChangeEvent event)
  {
    Platform.runLater(() ->
    {
      bookingsOnCurrentCustomer.setAll(currentCustomer.get().getBookingList());
    });
  }

  public void updateCustomer(PropertyChangeEvent event)
  {
    ArrayList<Customer> newCustomers = (ArrayList<Customer>) event.getNewValue();
    Platform.runLater(()-> customers.setAll(newCustomers));
  }

  public void updateCurrentCustomer(PropertyChangeEvent event)
  {
    Customer newCustomer = (Customer) event.getNewValue();
    Platform.runLater(() ->
    {
      currentCustomer.set(newCustomer);
      // Refresh bookings when current customer changes
      if (newCustomer != null)
      {
        bookingsOnCurrentCustomer.setAll(newCustomer.getBookingList());
      }
      else
      {
        bookingsOnCurrentCustomer.clear();
      }
    });
  }

  public void setSelectedCustomer(Customer selectedCustomer)
  {
    this.selectedCustomer = selectedCustomer;
    model.firePropertyForCurrentCustomer(selectedCustomer);
  }

  public Customer getSelectedCustomer()
  {
    return selectedCustomer;
  }

  public Vehicle getSelectedVehicle()
  {
    return selectedVehicle;
  }

  public void setSelectedVehicle(Vehicle selectedVehicle)
  {
    this.selectedVehicle = selectedVehicle;
    model.firePropertyForSelectedVehicle(selectedVehicle);
  }

  public Employee getSelectedEmployee()
  {
    return selectedEmployee;
  }

  public void setSelectedEmployee(Employee selectedEmployee)
  {
    this.selectedEmployee = selectedEmployee;
    model.firePropertyForCurrentEmployee(selectedEmployee);
  }
}