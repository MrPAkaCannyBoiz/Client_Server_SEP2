package ViewModel;


import Model.Customer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Shared.RemoteModel;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

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

  public void updateCustomer(PropertyChangeEvent evt) {
    @SuppressWarnings("unchecked")
    List<Customer> received = (List<Customer>) evt.getNewValue();

    if (received != null) {
      // always hand ListView a different object
      Platform.runLater(() ->
              customers.setAll(new ArrayList<>(received)));
    }
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
