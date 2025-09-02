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
  public void onSetCustomerButtonClicked() {

    Customer selected = listTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING,"Please select a customer").showAndWait();
      return;
    }

    try {
      DriverLicense dl = new DriverLicense(
              licenceNoTextField.getText(),
              categoryACheckBox.isSelected(),
              categoryBCheckBox.isSelected(),
              categoryCCheckBox.isSelected(),
              categoryDCheckBox.isSelected(),
              categoryXCheckBox.isSelected());

      Customer edited = new Customer(
              selected.getVIAId(),
              cprTextField.getText(),
              passNoTextField.getText(),
              nameTextField.getText(),
              phoneNoTextField.getText(),
              emailTextField.getText(),
              dl,
              nationalityComboBox.getValue(),
              dobDatePicker.getValue().toString());




      /* ---------- ONE round-trip does validation + update ------------ */
      vehicleBookingViewModel
              .getModel()
              .updateCustomer(selected, edited);


      new Alert(Alert.AlertType.INFORMATION,"Customer updated").showAndWait();
      ((Stage)setCustomerButton.getScene().getWindow()).close();

    } catch (IllegalArgumentException ex) {
      new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
    }
  }


  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory=viewFactory;
  }
}
