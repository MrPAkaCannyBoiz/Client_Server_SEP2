package View;

import Application.ViewFactory;
import Model.Customer;
import Model.DriverLicense;
import ViewModel.NewCustomerViewModel;
import ViewModel.VehicleBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditingCustomerView {
  public ListView<Customer> listTable;
  public Button editSelectedCustomerButton;
  public TextField nameTextField;
  public TextField phoneNoTextField;
  public TextField emailTextField;
  public TextField cprTextField;
  public TextField passNoTextField;
  public TextField licenceNoTextField;
  public CheckBox categoryACheckBox;
  public CheckBox categoryBCheckBox;
  public CheckBox categoryCCheckBox;
  public CheckBox categoryDCheckBox;
  public CheckBox categoryXCheckBox;
  public DatePicker dobDatePicker;
  public ComboBox<String> nationalityComboBox;
  public Button setCustomerButton;
  public TextField driverLicenseTextField;
  private ViewFactory viewFactory;
  private VehicleBookingViewModel vehicleBookingViewModel;
  private NewCustomerViewModel newCustomerViewModel;

  public EditingCustomerView(VehicleBookingViewModel vehicleBookingViewModel, NewCustomerViewModel newCustomerViewModel){
    this.vehicleBookingViewModel=vehicleBookingViewModel;
    this.newCustomerViewModel=newCustomerViewModel;
  }
public void initialize(){
    listTable.setItems(vehicleBookingViewModel.getCustomers());
  nationalityComboBox.setItems(newCustomerViewModel.getNationalityList());
}


  @FXML
  public void onEditSelectedCustomerButtonClicked() {
    Customer selected = listTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      nameTextField.setText(selected.getName());
      phoneNoTextField.setText(selected.getPhoneNo());
      emailTextField.setText(selected.getEmail());
      cprTextField.setText(selected.getCpr());
      passNoTextField.setText(selected.getPassNo());
      licenceNoTextField.setText(selected.getDriverLicense().getLicenseNumber());
      dobDatePicker.setValue(selected.getDob());
      nationalityComboBox.setValue(selected.getNationality());

      categoryACheckBox.setSelected(selected.getDriverLicense().isCategoryA());
      categoryBCheckBox.setSelected(selected.getDriverLicense().isCategoryB());
      categoryCCheckBox.setSelected(selected.getDriverLicense().isCategoryC());
      categoryDCheckBox.setSelected(selected.getDriverLicense().isCategoryD());
      categoryXCheckBox.setSelected(selected.getDriverLicense().isCategoryX());
    } else
    {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to edit.");
      alert.showAndWait();
    }
  }
  @FXML
  public void onSetCustomerButtonClicked()
  {
    Customer selected = listTable.getSelectionModel().getSelectedItem();
      try
      {
        // Step 1: Collect new values from UI (DO NOT modify the customer yet)
        String newName = nameTextField.getText();
        String newPhone = phoneNoTextField.getText();
        String newEmail = emailTextField.getText();
        String newCpr = cprTextField.getText();
        String newPassNo = passNoTextField.getText();
        String newDob = dobDatePicker.getValue().toString();
        String newNationality = nationalityComboBox.getValue();
        String newLicenseNo = licenceNoTextField.getText();
        boolean catA = categoryACheckBox.isSelected();
        boolean catB = categoryBCheckBox.isSelected();
        boolean catC = categoryCCheckBox.isSelected();
        boolean catD = categoryDCheckBox.isSelected();
        boolean catX = categoryXCheckBox.isSelected();
        

        // Create a temporary driver license for validation
        DriverLicense tempLicense = new DriverLicense(
            newLicenseNo, catA, catB, catC, catD, catX
        );

        // Create a temporary customer for uniqueness checks
        Customer tempCustomer = new Customer
            (newCpr,newPassNo,newName,newPhone,newEmail,tempLicense,newNationality,newDob);

        // Validate uniqueness using the temporary customer
        newCustomerViewModel.checkIfCustomerInfoIsUnique(tempCustomer);

        // Step 3: Only update the actual customer if validation succeeds
        selected.setName(newName);
        selected.setPhoneNo(newPhone);
        selected.setEmail(newEmail);
        selected.setCprAndPassNo(newCpr, newPassNo);
        selected.setDob(newDob);
        selected.setNationality(newNationality);
        selected.setDriverLicense(tempLicense); // Use validated license

        // Refresh UI and notify listeners
        listTable.refresh();
        vehicleBookingViewModel.firePropertyForCustomer();

        Alert success = new Alert(Alert.AlertType.INFORMATION, "Customer information updated.");
        success.showAndWait();
        Stage stage = (Stage) setCustomerButton.getScene().getWindow();
        stage.close();
      }
      catch (IllegalArgumentException e)
      {
        Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
        error.showAndWait();
        listTable.getSelectionModel().clearSelection();
      }
      catch (NullPointerException e)
      {
        new Alert(Alert.AlertType.ERROR,"Please select customer")
            .showAndWait();
        listTable.getSelectionModel().clearSelection();
      }
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory=viewFactory;
  }
}
