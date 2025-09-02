package View;

import Application.ViewFactory;
import Model.Booking;
import Model.Vehicle;
import ViewModel.VehicleBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class CustomerBookingManagerView
{
  public Button cancelBookingButton;
  public Button returnVehicleButton;
  @FXML private Label currentCustomerLabel;
  @FXML private TableView<Booking> bookingTableView;
  @FXML private TableColumn<Booking, Integer> bookingIDColumn;
  @FXML private TableColumn<Booking, Integer> customerIdColumn;
  @FXML private TableColumn<Booking, LocalDate> startDateColumn;
  @FXML private TableColumn<Booking, LocalDate> endDateColumn;
  @FXML private TableColumn<Booking, String> vehicleColumn;
  @FXML private TableColumn<Booking, Boolean> isActiveColumn;
  @FXML private TableColumn<Booking, Double> finalPaymentColumn;
  private VehicleBookingViewModel vehicleBookingViewModel;
  private ViewFactory viewFactory;

  public CustomerBookingManagerView(VehicleBookingViewModel viewModel)
  {
    this.vehicleBookingViewModel = viewModel;
  }

  public void initialize()
  {
    bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
    customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    vehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehiclePlateNumber"));
    isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
    finalPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("finalPayment"));
    bookingTableView.setItems(vehicleBookingViewModel.getBookings());

    currentCustomerLabel.textProperty().bind(vehicleBookingViewModel
        .currentCustomerProperty().map(customer ->
            //if-else statement on crack
            customer == null ?
                "No customer selected" :
                "Your ID is: " + customer.getVIAId()));
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }


}
