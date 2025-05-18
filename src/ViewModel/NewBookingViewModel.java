package ViewModel;

import Model.Customer;
import Model.Employee;
import Model.Vehicle;
import Shared.RemoteModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class NewBookingViewModel
{
  private RemoteModel model;
  //for both customers and employees
  private ObjectProperty<LocalDate> startDate;
  private ObjectProperty<LocalDate> endDate;
  private ObjectProperty<Vehicle> selectedVehicle;
  private Vehicle chosenVehicle;

  //for customer only
  private ObjectProperty<Customer> currentCustomer;
  private Customer selectedCustomer;

  //for employee only
  private ObjectProperty<Employee> currentEmployee;
  private Employee selectedEmployee;
  private ObservableList<Customer> customers;

  public NewBookingViewModel(RemoteModel model)
  {
    this.model = model;
    this.startDate = new SimpleObjectProperty<>();
    this.endDate = new SimpleObjectProperty<>();
    this.customers = FXCollections.observableArrayList(model.getCustomers());

    //this for just to show the detail who and which vehicle they're booking
    this.currentCustomer = new SimpleObjectProperty<Customer>(selectedCustomer);
    this.selectedVehicle = new SimpleObjectProperty<Vehicle>(chosenVehicle);
    this.currentEmployee = new SimpleObjectProperty<Employee>(selectedEmployee);

    model.addPropertyChangeListener("CustomerEvent",this::updateCustomer);
    model.addPropertyChangeListener("CurrentCustomerEvent",this::updateCurrentCustomer);
    model.addPropertyChangeListener("SelectedVehicleEvent",this::updateSelectedVehicle);
  }

  public void updateCustomer(PropertyChangeEvent event)
  {
    ArrayList<Customer> newCustomers = (ArrayList<Customer>) event.getNewValue();
    Platform.runLater(()-> customers.setAll(newCustomers));
  }

  public void updateCurrentCustomer(PropertyChangeEvent event)
  {
    Platform.runLater(() -> currentCustomer.set((Customer) event.getNewValue()));
  }

  public void updateSelectedVehicle(PropertyChangeEvent event)
  {
    Platform.runLater(() -> selectedVehicle.set((Vehicle) event.getNewValue()));
  }

  // for employee side
  public void makeBookingByEmployeeGivenSelectedVehicle(Customer selectedCustomer)
      throws IOException
  {
    model.makeBookingByEmployee(startDate.get(),endDate.get(),selectedEmployee,selectedCustomer,chosenVehicle);
  }

  // for Customer side
  public void makeBookingByCurrentCustomerGivenSelectedVehicle()
  {
    if (currentCustomer.get() != null && selectedVehicle.get() != null)
    {
      model.makeBookingByCustomer(startDate.get(),endDate.get(),selectedCustomer,chosenVehicle);
    }
  }

  public ObjectProperty<Customer> currentCustomerProperty()
  {
    return currentCustomer;
  }

  public ObjectProperty<Employee> currentEmployeeProperty()
  {
    return currentEmployee;
  }

  public ObservableList<Customer> getCustomers()
  {
    return customers;
  }

  public ObjectProperty<Vehicle> selectedVehicleProperty()
  {
    return selectedVehicle;
  }

  public ObjectProperty<LocalDate> startDateProperty()
  {
    return startDate;
  }

  public ObjectProperty<LocalDate> endDateProperty()
  {
    return endDate;
  }

  public Customer getSelectedCustomer()
  {
    return selectedCustomer;
  }

  public Vehicle getChosenVehicle()
  {
    return chosenVehicle;
  }

  public Employee getSelectedEmployee()
  {
    return selectedEmployee;
  }

  public void setSelectedCustomer(Customer selectedCustomer)
  {
    this.selectedCustomer = selectedCustomer;
    model.firePropertyForCurrentCustomer(selectedCustomer);
  }

  public void setSelectedEmployee(Employee selectedEmployee)
  {
    this.selectedEmployee = selectedEmployee;
  }

  public void setChosenVehicle(Vehicle chosenVehicle)
  {
    this.chosenVehicle = chosenVehicle;
    model.firePropertyForSelectedVehicle(chosenVehicle);
  }
}
