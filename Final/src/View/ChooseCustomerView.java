package View;

import Application.ViewFactory;
import Application.ViewModelFactory;
import Model.Customer;
import ViewModel.ChooseCustomerViewModel;
import ViewModel.VehicleBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ChooseCustomerView
{
  private ChooseCustomerViewModel chooseCustomerViewModel;
  private ViewFactory viewFactory;
  private VehicleBookingViewModel customerPageVM;


  @FXML private ListView<Customer> customerListView;

  public ChooseCustomerView(ChooseCustomerViewModel viewModel, VehicleBookingViewModel customerPageVM)
  {
    this.chooseCustomerViewModel = viewModel;
    this.customerPageVM = customerPageVM;
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void initialize()
  {
    customerListView.setItems(chooseCustomerViewModel.getCustomers());
  }

  public void onBackButtonClicked() throws IOException
  {
    viewFactory.getMainPageView();
  }

  public void onSignInButtonClicked()
  {
    Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
    if (selectedCustomer == null)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Please select customer");
      alert.setContentText("Please select customer before sign in");
      alert.showAndWait();
    }
    else
    {
      chooseCustomerViewModel.setSelectedCustomer(selectedCustomer);
      customerPageVM.setSelectedCustomer(selectedCustomer);
      viewFactory.getCustomerPageView();
    }
  }

  public void onAddNewCustomerButtonClicked()
  {
    viewFactory.getNewCustomerViewAsNewPage();
  }

}
