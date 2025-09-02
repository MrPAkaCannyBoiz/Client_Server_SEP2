package View;

import Application.ViewFactory;
import Application.ViewModelFactory;
import Model.*;
import ViewModel.NewBookingViewModel;
import ViewModel.VehicleBookingViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EmployeePageView {

    @FXML public Button resetButton;
    @FXML public Button moreInfoButton;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> colorComboBox;
    @FXML private ComboBox<String> statusComboBox;

    @FXML private Slider priceSlider;
    @FXML private Label priceLabel;

    @FXML private TableView <Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, String> brandColumn;
    @FXML private TableColumn<Vehicle, String> colModel;
    @FXML private TableColumn<Vehicle, String> colColor;
    @FXML private TableColumn<Vehicle, String> colType;
    @FXML private TableColumn<Vehicle, String> colStatus;
    @FXML private TableColumn<Vehicle, Integer> colPrice;

    @FXML private Button bookButton;
    @FXML private Button filterButton;
    @FXML private Button backButton;
    private VehicleBookingViewModel vehicleBookingViewModel;
    private ViewFactory viewFactory;
    private NewBookingViewModel newBookingVM;

    public EmployeePageView(VehicleBookingViewModel vehicleBookingViewModel, NewBookingViewModel newBookingVM)
    {
        this.vehicleBookingViewModel=vehicleBookingViewModel;
        this.newBookingVM = newBookingVM;
    }

    @FXML
    public void initialize() {

        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
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
    }

    public void setViewFactory(ViewFactory viewFactory)
    {
        this.viewFactory = viewFactory;
    }


@FXML void onClickFilter()
{
    String selectedType = typeComboBox.getValue();
    String selectedColor = colorComboBox.getValue();
    String selectedStatus = statusComboBox.getValue();
    double maxPrice = priceSlider.getValue();
    if ((selectedType == null || selectedType.equals("ALL")) &&
        (selectedColor == null || selectedColor.equals("ALL")) &&
        (selectedStatus == null || selectedStatus.equals("ALL")) &&
        maxPrice == 500) {
        vehicleTable.setItems(vehicleBookingViewModel.getVehicles());
        return;
    }

    // Apply filter strategy
    SearchStrategy strategy = new FilterStrategy(
        selectedType,
        selectedColor,
        selectedStatus,
        maxPrice
    );

    ObservableList<Vehicle> filteredVehicles =
        strategy.search(vehicleBookingViewModel.getVehicles());

    vehicleTable.setItems(filteredVehicles);
}

@FXML
private void onMoreInfoClick(){
    Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
    if(selectedVehicle == null){
        Alert alert =  new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No selection");
        alert.setHeaderText(null);
        alert.setContentText("Please select a vehicle first.");
        alert.showAndWait();
    }else {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehicle details");
        alert.setHeaderText("Detailed information:");
        alert.setContentText(selectedVehicle.toString());
        alert.showAndWait();
    }
}
    @FXML
    private void onResetClick() {

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


    @FXML
    public void onBackToMainPageButtonClicked() throws IOException
    {
        viewFactory.getMainPageView();
    }

    @FXML
    public void onAddBookButtonClicked() throws IOException
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
            newBookingVM.setSelectedEmployee(vehicleBookingViewModel.getSelectedEmployee());
            viewFactory.getNewBookingViewForEmployee();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Vehicle is not Available");
            alert.setContentText("Please select available vehicle");
            alert.showAndWait();
        }
    }

    @FXML
    public void onCancelBookingButtonClicked() throws IOException
    {
        viewFactory.getBookingView();
    }

    @FXML
    public void onReturnVehicleButtonClicked()
    {
      try
      {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel()
            .getSelectedItem();
          if (selectedVehicle == null)
          {
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setHeaderText("Can't Return this vehicle");
              alert.setContentText("please select vehicle first");
              alert.showAndWait();
          }
        else
        {
            vehicleBookingViewModel.returnVehicleByEmployee(selectedVehicle);
        }

      }
      catch (IllegalArgumentException e)
      {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Can't Return this vehicle");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }
    }

    public void onAddNewVehicleButtonClicked()
    {
        viewFactory.getAddVehicleView();
    }

    public void onRemoveVehicleButtonClicked()
    {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel()
            .getSelectedItem();
        if (selectedVehicle == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Vehicle is not selected");
            alert.setContentText("Please select vehicle to remove");
            alert.showAndWait();
        }
        else if (selectedVehicle.getCurrentState() instanceof AvailableState)
        {
            vehicleBookingViewModel.removeVehicle(selectedVehicle);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vehicle plate no " +
                selectedVehicle.getPlateNo() + " is removed");
            alert.showAndWait();
        }
        else if (selectedVehicle.getCurrentState() instanceof RentedState)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cannot remove rented vehicle");
            alert.showAndWait();
        }
    }


    public void onClickCustomerListButton() throws IOException
    {
        viewFactory.getEditingCustomerView();
    }
}
