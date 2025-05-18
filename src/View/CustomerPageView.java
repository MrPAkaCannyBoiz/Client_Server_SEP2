package View;

import Application.ViewFactory;
import Application.ViewModelFactory;
import Model.*;

import ViewModel.NewBookingViewModel;
import ViewModel.VehicleBookingViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class CustomerPageView
{
    @FXML public Button resetButton;
    @FXML public Button moreInfoButton;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> colorComboBox;
    @FXML private ComboBox<String> statusComboBox;

    @FXML private Slider priceSlider;
    @FXML private Label priceLabel;
    @FXML private Label customerNameLabel;

    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, String> colModel;
    @FXML private TableColumn<Vehicle, String> colColor;
    @FXML private TableColumn<Vehicle, String> colType;
    @FXML private TableColumn<Vehicle, String> colStatus;
    @FXML private TableColumn<Vehicle, Integer> colPrice;

    @FXML private Button reserveButton;
    @FXML private Button bookButton;
    @FXML private Button filterButton;
    @FXML private Button backButton;
    private VehicleBookingViewModel vehicleBookingViewModel;
    private ViewFactory viewFactory;
    private NewBookingViewModel newBookingVM;

    public CustomerPageView(VehicleBookingViewModel viewModel, NewBookingViewModel newBookingVM)
    {
        this.vehicleBookingViewModel = viewModel;
        this.newBookingVM = newBookingVM;
    }

    public void initialize()
    {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        priceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int rounded = newVal.intValue();
            priceLabel.setText(String.valueOf(rounded));
        });
        vehicleTable.setItems(vehicleBookingViewModel.getVehicles());

        customerNameLabel.textProperty().bind(vehicleBookingViewModel.currentCustomerProperty()
            .map(customer ->
                //if-else statement on crack
                customer == null ?
                    "No customer selected" :
                    "Login as Customer: " + customer.getName()));
    }

    public void setViewFactory(ViewFactory viewFactory)
    {
        this.viewFactory = viewFactory;
    }

    @FXML private void handleBackClick() throws IOException
    {
        viewFactory.getMainPageView();
    }

    @FXML private void onBookButtonClicked()
    {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel()
            .getSelectedItem();
        if (selectedVehicle == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Vehicle is not selected");
            alert.setContentText("Please select vehicle before booking");
            alert.showAndWait();
        }
        else if (selectedVehicle.getCurrentState() instanceof AvailableState)
        {
            vehicleBookingViewModel.setSelectedVehicle(selectedVehicle);
            newBookingVM.setChosenVehicle(selectedVehicle);
            newBookingVM.setSelectedCustomer(vehicleBookingViewModel.getSelectedCustomer());
            viewFactory.getNewBookingViewForCustomer();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Vehicle is not Available");
            alert.setContentText("Please select available vehicle");
            alert.showAndWait();
        }
    }

    public void onMoreInfoClick()
    {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel()
            .getSelectedItem();
        Alert alert;
        if (selectedVehicle == null)
        {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a vehicle first.");
        }
        else
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vehicle details");
            alert.setHeaderText("Detailed information:");
            alert.setContentText(selectedVehicle.toString());
        }
        alert.showAndWait();
    }

    public void onClickFilter()
    {
        String selectedType = typeComboBox.getValue();
        String selectedColor = colorComboBox.getValue();
        String selectedStatus = statusComboBox.getValue();
        double maxPrice = priceSlider.getValue();
        if ((selectedType == null || selectedType.equals("ALL")) && (
            selectedColor == null || selectedColor.equals("ALL")) && (
            selectedStatus == null || selectedStatus.equals("ALL"))
            && maxPrice == 500)
        {
            vehicleTable.setItems(vehicleBookingViewModel.getVehicles());
            return;
        }

        // Apply filter strategy
        SearchStrategy strategy = new FilterStrategy(selectedType,
            selectedColor, selectedStatus, maxPrice);

        ObservableList<Vehicle> filteredVehicles = strategy.search(
            vehicleBookingViewModel.getVehicles());

        vehicleTable.setItems(filteredVehicles);
    }

    public void onCancelBookingButtonClicked() throws IOException
    {
        viewFactory.getCustomerBookingManagerView();
    }

    public void onReturnVehicleButtonClicked()
    {
      try
      {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel()
            .getSelectedItem();
        vehicleBookingViewModel.returnBookedVehicleAndPay(selectedVehicle);
      }
      catch (NullPointerException e)
      {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setHeaderText("You have not select the booking");
          alert.setContentText("Please select the booking to return");
          alert.showAndWait();
      }
      catch (IllegalArgumentException e)
      {
          new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
      }
    }

    public void onResetClick()
    {
        // Clear selections
        typeComboBox.getSelectionModel().clearSelection();
        colorComboBox.getSelectionModel().clearSelection();
        statusComboBox.getSelectionModel().clearSelection();

        typeComboBox.getEditor().clear();
        colorComboBox.getEditor().clear();
        statusComboBox.getEditor().clear();

        // Reset Slider
        priceSlider.setValue(priceSlider.getMax());

        // Reset TableView
        vehicleTable.setItems(vehicleBookingViewModel.getVehicles());
    }

}



