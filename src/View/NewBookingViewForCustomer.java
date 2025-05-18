package View;

import Application.ViewFactory;
import Model.Vehicle;
import ViewModel.NewBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NewBookingViewForCustomer
{
  private NewBookingViewModel viewModel;
  private ViewFactory viewFactory;
  @FXML private Button bookButton;
  @FXML private Button cancelButton;
  @FXML private DatePicker startDatePicker;
  @FXML private DatePicker endDatePicker;
  @FXML private Label customerNameLabel;
  @FXML private Label vehicleDetailLabel;

  public NewBookingViewForCustomer(NewBookingViewModel viewModel)
  {
    this.viewModel = viewModel;
  }

  public void initialize()
  {
    startDatePicker.valueProperty().bindBidirectional(viewModel.startDateProperty());
    endDatePicker.valueProperty().bindBidirectional(viewModel.endDateProperty());

    //show user who is booking for
    customerNameLabel.textProperty().bind(
        viewModel.currentCustomerProperty().map(
            //if - else statement on crack
            customer -> customer == null ? "No customer selected"
                : "Booking for " + customer.getName()
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

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void onBookButtonClicked()
  {
    try
    {
      viewModel.makeBookingByCurrentCustomerGivenSelectedVehicle();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setHeaderText("Done booking!");
      alert.setContentText("The selected vehicle is now booked");
      alert.showAndWait();
      Stage stage = (Stage) cancelButton.getScene().getWindow();
      stage.close();
    }
    catch (Exception e)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  public void onCancelButtonClicked()
  {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }


}
