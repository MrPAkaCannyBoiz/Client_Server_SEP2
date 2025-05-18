package View;

import Application.ViewFactory;
import ViewModel.AddVehicleViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

public class AddVehicleView
{
  private AddVehicleViewModel addVehicleViewModel;
  private ViewFactory viewFactory;

  @FXML private ComboBox<String> vehicleTypeComboBox;
  @FXML private ComboBox<String> brandComboBox;
  @FXML private ComboBox<String> colorComboBox;
  @FXML private ComboBox<String> engineComboBox;
  @FXML private TextField addMoreBrandTextField;
  @FXML private TextField vehicleModelTextField;
  @FXML private TextField plateNoTextField;
  @FXML private TextField pricePrHourTextField;
  @FXML private TextField depositTextField;
  @FXML private TextField lateFeePerDayTextField;
  @FXML private TextField noOfTyreTextField;
  @FXML private TextField noOfSeatTextField;
  @FXML private TextField topSpeedTextField;
  @FXML private TextField loadCapTextField;
  @FXML private TextField cargoVolTextField;
  @FXML private TextField antiGravityTextField;
  @FXML private CheckBox hasSideCarCheckBox;
  @FXML private CheckBox hasTurboCheckBox;
  @FXML private CheckBox hasSlidingDoorCheckBox;
  @FXML private CheckBox hasTrailerAttachedCheckBox;
  @FXML private CheckBox hasCloakingDeviceCheckBox;
  @FXML private Button addVehicleButton;

  public AddVehicleView(AddVehicleViewModel viewModel)
  {
    this.addVehicleViewModel = viewModel;
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  @FXML
  public void initialize()
  {
    plateNoTextField.textProperty().bindBidirectional(addVehicleViewModel.plateNoProperty());
    vehicleModelTextField.textProperty().bindBidirectional(addVehicleViewModel.vehicleModelProperty());
    addMoreBrandTextField.textProperty().bindBidirectional(addVehicleViewModel.addMoreVehicleBrandProperty());
    pricePrHourTextField.textProperty().bindBidirectional(
        addVehicleViewModel.pricePerDayProperty(), new NumberStringConverter()
    );
    //only allow number on text field (with lambda)
    pricePrHourTextField.setTextFormatter(new TextFormatter<Integer>(change ->
    {
      String text = change.getControlNewText();
      if (text.matches("\\d*"))
      {
        return change;
      }
      return null;
    }));

    depositTextField.textProperty().bindBidirectional(
        addVehicleViewModel.depositProperty(), new NumberStringConverter()
    );
    //only allow number on text field (using method with the rest)
    depositTextField.setTextFormatter(allowOnlyNumber());

    lateFeePerDayTextField.textProperty().bindBidirectional(
        addVehicleViewModel.lateFeePerDayProperty(), new NumberStringConverter()
    );
    lateFeePerDayTextField.setTextFormatter(allowOnlyNumber());

    noOfTyreTextField.textProperty().bindBidirectional(
        addVehicleViewModel.noOfTyreProperty(), new NumberStringConverter()
    );
    noOfTyreTextField.setTextFormatter(allowOnlyNumber());

    noOfSeatTextField.textProperty().bindBidirectional(
        addVehicleViewModel.noOfSeatProperty(), new NumberStringConverter()
    );
    noOfSeatTextField.setTextFormatter(allowOnlyNumber());

    topSpeedTextField.textProperty().bindBidirectional(
        addVehicleViewModel.topSpeedProperty(), new NumberStringConverter()
    );
    topSpeedTextField.setTextFormatter(allowOnlyNumber());

    loadCapTextField.textProperty().bindBidirectional(
        addVehicleViewModel.loadCapacityProperty(), new NumberStringConverter()
    );
    loadCapTextField.setTextFormatter(allowOnlyNumber());

    cargoVolTextField.textProperty().bindBidirectional(
        addVehicleViewModel.cargoVolProperty(), new NumberStringConverter()
    );
    cargoVolTextField.setTextFormatter(allowOnlyNumber());

    antiGravityTextField.textProperty().bindBidirectional(
        addVehicleViewModel.antiGravityLvlProperty(), new NumberStringConverter()
    );
    antiGravityTextField.setTextFormatter(allowOnlyNumber());

    //binding the checkbox that has booleanProperty
    hasSideCarCheckBox.selectedProperty().bindBidirectional(
        addVehicleViewModel.hasSidecarProperty());
    hasTurboCheckBox.selectedProperty().bindBidirectional(
        addVehicleViewModel.hasTurboProperty());
    hasSlidingDoorCheckBox.selectedProperty().bindBidirectional(
        addVehicleViewModel.hasSlidingDoorProperty());
    hasTrailerAttachedCheckBox.selectedProperty().bindBidirectional(
        addVehicleViewModel.hasTrailerAttachedProperty());
    hasCloakingDeviceCheckBox.selectedProperty().bindBidirectional(
        addVehicleViewModel.hasCloakingDeviceProperty());

    //set the ComboBox that brand is depend on vehicle type
    vehicleTypeComboBox.setItems(addVehicleViewModel.getVehicleTypeList());
    brandComboBox.setItems(addVehicleViewModel.getDisplayedBrandList());
    //set the ComboBox that engine is depended on vehicle type
    engineComboBox.setItems(addVehicleViewModel.getDisplayedEngineList());
    //set independent ComboBox
    colorComboBox.setItems(addVehicleViewModel.getColorList());

    //binding ComboBox
    vehicleTypeComboBox.valueProperty().bindBidirectional(addVehicleViewModel.selectedVehicleTypeProperty());
    brandComboBox.valueProperty().bindBidirectional(addVehicleViewModel.selectedBrandProperty());
    colorComboBox.valueProperty().bindBidirectional(addVehicleViewModel.selectedColorProperty());
    engineComboBox.valueProperty().bindBidirectional(addVehicleViewModel.engineProperty());

    // Add listener to selected vehicle type
    addVehicleViewModel.selectedVehicleTypeProperty().addListener((observable, oldValue, newValue) ->
    {
      disableGUIElements();
      if (newValue != null)
      {
        switch (newValue)
        {
          case "SportsCar":
            topSpeedTextField.setDisable(false);
            hasTurboCheckBox.setDisable(false);
            noOfTyreTextField.setText("4");
            noOfTyreTextField.setDisable(false);
            break;
          case "Motorcycle":
            hasSideCarCheckBox.setDisable(false);
            noOfTyreTextField.setText("2");
            noOfTyreTextField.setDisable(false);
            break;
          case "Truck":
            loadCapTextField.setDisable(false);
            noOfTyreTextField.setText("10");
            hasTrailerAttachedCheckBox.setDisable(false);
            noOfTyreTextField.setDisable(false);
            break;
          case "Van":
            cargoVolTextField.setDisable(false);
            noOfTyreTextField.setDisable(false);
            hasSlidingDoorCheckBox.setDisable(false);
            noOfTyreTextField.setText("4");
            break;
          case "UFO":
            antiGravityTextField.setDisable(false);
            antiGravityTextField.setText("1");
            hasCloakingDeviceCheckBox.setDisable(false);
            noOfTyreTextField.setDisable(true);
            noOfTyreTextField.setText("0");
            break;
        }
      }
    });
  }

  private void disableGUIElements()
  {
    topSpeedTextField.setDisable(true);
    loadCapTextField.setDisable(true);
    cargoVolTextField.setDisable(true);
    antiGravityTextField.setDisable(true);
    hasSideCarCheckBox.setDisable(true);
    hasTurboCheckBox.setDisable(true);
    hasSlidingDoorCheckBox.setDisable(true);
    hasTrailerAttachedCheckBox.setDisable(true);
    hasCloakingDeviceCheckBox.setDisable(true);


    topSpeedTextField.clear();
    loadCapTextField.clear();
    cargoVolTextField.clear();
    antiGravityTextField.clear();

    hasSideCarCheckBox.setSelected(false);
    hasTurboCheckBox.setSelected(false);
    hasTrailerAttachedCheckBox.setSelected(false);
    hasCloakingDeviceCheckBox.setSelected(false);
    hasSlidingDoorCheckBox.setSelected(false);
  }

  private TextFormatter<Integer> allowOnlyNumber()
  {
    return new TextFormatter<Integer>(change ->
    {
      String text = change.getControlNewText();
      if (text.matches("\\d*"))
      {
        return change;
      }
      return null;
    });
  }

  public void onAddButtonClicked()
  {
    try
    {
      addVehicleViewModel.addVehicle();
      new Alert(Alert.AlertType.INFORMATION,"Vehicle added")
          .showAndWait();
    }
    catch (IllegalArgumentException e)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Cannot add vehicle");
      alert.setHeaderText("Cannot add vehicle");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  public void onAddMoreModelButtonClicked()
  {
    String newBrandInTextField = addMoreBrandTextField.getText();
    addVehicleViewModel.AddMoreVehicleBrand(newBrandInTextField);
  }

}
