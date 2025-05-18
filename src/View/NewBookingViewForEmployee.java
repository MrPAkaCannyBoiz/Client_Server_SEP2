package View;

import Application.ViewFactory;
import Model.Customer;
import ViewModel.NewBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class NewBookingViewForEmployee
{
  private NewBookingViewModel viewModel;
  @FXML private DatePicker startDatePicker;
  @FXML private DatePicker endDatePicker;
  @FXML private ListView<Customer> customerListView;
  @FXML private Label employeeWhoBooksLabel;
  @FXML private Label vehicleDetailLabel;
  @FXML private Button cancelButton;
  private ViewFactory viewFactory;

  public NewBookingViewForEmployee(NewBookingViewModel viewModel)
  {
    this.viewModel = viewModel;
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void initialize()
  {
    customerListView.setItems(viewModel.getCustomers());
    startDatePicker.valueProperty().bindBidirectional(viewModel.startDateProperty());
    endDatePicker.valueProperty().bindBidirectional(viewModel.endDateProperty());

    //show user which employee is booking
    employeeWhoBooksLabel.textProperty().bind(
        viewModel.currentEmployeeProperty().map(
            //if - else statement on crack
            employee -> employee == null ? "No employee selected"
                : "Employee who books: " + employee
        )
    );

    //show user what vehicle is she/he booking
    vehicleDetailLabel.textProperty().bind(
        viewModel.selectedVehicleProperty().map(
            //if - else statement on crack
            vehicle -> vehicle == null ? "No vehicle selected"
                : "You book " + vehicle.getVehicleType() + " Brand: "
                + vehicle.getBrand() + "Plate No.: " + vehicle.getPlateNo()
        )
    );
  }

  public void onBookButtonClicked()
  {
    try
    {
      Customer selectedCustomer = customerListView.getSelectionModel()
          .getSelectedItem();
      viewModel.makeBookingByEmployeeGivenSelectedVehicle(selectedCustomer);
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setHeaderText("Done booking!");
      alert.setContentText("The selected vehicle is now booked");
      alert.showAndWait();
      Stage stage = (Stage) cancelButton.getScene().getWindow();
      stage.close();
    }
    catch (IllegalArgumentException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Something's wrong");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
    catch (NullPointerException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Something's wrong");
      alert.setContentText("Please select the customer / Someone delete employee that you currently use");
      alert.showAndWait();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void onCancelButtonClicked()
  {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

}
