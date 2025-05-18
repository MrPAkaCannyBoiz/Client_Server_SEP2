package ViewModel;


import Model.Customer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Shared.RemoteModel;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class ChooseCustomerViewModel
{
  private RemoteModel model;
  private ObservableList<Customer> customers;
  private Customer selectedCustomer; // Track selection client-side

  public ChooseCustomerViewModel(RemoteModel model)
  {
    this.model = model;
    this.customers = FXCollections.observableArrayList(model.getCustomers());

    model.addPropertyChangeListener("CustomerEvent",this::updateCustomer);
  }

  public void updateCustomer(PropertyChangeEvent event)
  {
    ArrayList<Customer> newCustomers = (ArrayList<Customer>) event.getNewValue();
    Platform.runLater(()-> customers.setAll(newCustomers));
  }


  public ObservableList<Customer> getCustomers()
  {
    return customers;
  }

  public Customer getSelectedCustomer()
  {
    return selectedCustomer;
  }

  public void setSelectedCustomer(Customer selectedCustomer)
  {
    this.selectedCustomer = selectedCustomer;
  }
}
