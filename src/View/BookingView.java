package View;

import Application.ViewFactory;
import Model.Booking;
import Model.Customer;
import Model.Vehicle;
import ViewModel.VehicleBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class BookingView
{
  public Button cancelBookingButton;
  public Button returnVehicleButton;
  @FXML private TableView<Booking> bookingTableView;
  @FXML private TableColumn<Booking, Integer> colBookingId;
  @FXML private TableColumn<Booking, Integer> colCustomerId;
  @FXML private TableColumn<Booking, LocalDate> colStartDate;
  @FXML private TableColumn<Booking, LocalDate> colEndDate;
  @FXML private TableColumn<Booking, String> colVehicle;
  @FXML private TableColumn<Booking, Boolean> colIsActive;
  @FXML private TableColumn<Booking, Double> colFinalPrice;
  private VehicleBookingViewModel vehicleBookingViewModel;
  private ViewFactory viewFactory;

  public BookingView(VehicleBookingViewModel vehicleBookingViewModel)
  {
    this.vehicleBookingViewModel = vehicleBookingViewModel;
  }

  public void initialize()
  {

    colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
    colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    colVehicle.setCellValueFactory(
        new PropertyValueFactory<>("vehiclePlateNumber"));
    colIsActive.setCellValueFactory(new PropertyValueFactory<>("isActive"));
    colFinalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPayment"));
    bookingTableView.setItems(vehicleBookingViewModel.getBookings());
    System.out.println(
        "DEBUG: Bookings in View = " + bookingTableView.getItems().size());
    vehicleBookingViewModel.getBookings()
        .forEach(b -> System.out.println("Booking Debug: " + b));

  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void OnClickBackButtonBooking()
  {
    viewFactory.getEmployeePageView();
  }

  public void onReturnVehicleButtonClicked()
  {
    try
    {
      Booking selectedBooking = bookingTableView.getSelectionModel()
          .getSelectedItem();
      vehicleBookingViewModel.returnVehicleByEmployee(
          selectedBooking.getVehicle());
    }
    catch (IllegalArgumentException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Can't Return this vehicle");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
    catch (NullPointerException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("You have not select the booking");
      alert.setContentText("Please select the booking to cancel");
      alert.showAndWait();
    }
  }

  public void onCancelBookingButtonClicked()
  {
    Booking selectedBooking = bookingTableView.getSelectionModel()
        .getSelectedItem();

    if (selectedBooking == null)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("No booking selected");
      alert.setContentText("Please select a booking to cancel.");
      alert.showAndWait();
      return;
    }
    try
    {
      vehicleBookingViewModel.getModel()
          .cancelBookingWithByPassing(selectedBooking);
    }
    catch (IllegalArgumentException e)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText("Error cancelling booking");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
    catch (NullPointerException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Something's wrong");
      alert.setContentText("Someone delete employee that you currently use or No booking selected");
      alert.showAndWait();
    }
  }
}
