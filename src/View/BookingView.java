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
    Booking selectedBooking = bookingTableView.getSelectionModel()
        .getSelectedItem();
    if (selectedBooking == null)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("No booking selected");
      alert.setContentText("Please select a booking to cancel.");
      alert.showAndWait();
    }
    else if (LocalDate.now().isAfter(selectedBooking.getStartDate())
        || LocalDate.now().isEqual(selectedBooking.getStartDate()))
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Cannot return");
      alert.setContentText("Cannot return before start date");
      alert.showAndWait();
    }
    else if (!LocalDate.now().isBefore(selectedBooking.getStartDate()))
    {
      vehicleBookingViewModel.returnVehicleByEmployee(
          selectedBooking.getVehicle());
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
    }
    else if (!selectedBooking.getStartDate().isAfter(LocalDate.now()))
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Cannot cancel");
      alert.setContentText("Cannot cancel after start date");
      alert.showAndWait();
    }
    else if (selectedBooking.getStartDate().isAfter(LocalDate.now()))
    {
      vehicleBookingViewModel.getModel()
          .cancelBookingWithByPassing(selectedBooking);
      new Alert(Alert.AlertType.INFORMATION,"done cancel booking")
          .showAndWait();
    }
  }
}
