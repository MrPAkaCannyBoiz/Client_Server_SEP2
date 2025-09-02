package ViewModel;


import Model.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Shared.RemoteModel;

//TODO add boundary later if the view working fine

public class AddVehicleViewModel
{
  private RemoteModel model;
  // for textField
  private StringProperty plateNo, engine, vehicleModel, addMoreVehicleBrand;
  private DoubleProperty pricePerDay, deposit, lateFeePerDay;
  private IntegerProperty noOfTyre, noOfSeat, topSpeed, loadCapacity, cargoVol,
      antiGravityLvl;

  // for checkBox
  private BooleanProperty hasSidecar, hasTurbo, hasTrailerAttached, hasSlidingDoor
      , hasCloakingDevice;

  //for ComboBox
  //for binding later
  private StringProperty selectedVehicleType;
  private StringProperty selectedBrand;
  private StringProperty selectedColor;
  // for making List inside ComboBox
  private ObservableList<String> colorList , vehicleTypeList, displayedBrandList
      , displayedEngineList;
  private Map<String, List<String>> vehicleBrandGivenType,
      vehicleEngineGivenType;


  public AddVehicleViewModel(RemoteModel model)
  {
    this.model = model;
    this.plateNo = new SimpleStringProperty("");
    this.engine = new SimpleStringProperty("");
    this.vehicleModel = new SimpleStringProperty("");
    this.addMoreVehicleBrand = new SimpleStringProperty("");
    this.pricePerDay = new SimpleDoubleProperty(0);
    this.deposit = new SimpleDoubleProperty(0);
    this.lateFeePerDay = new SimpleDoubleProperty(0);
    this.noOfTyre = new SimpleIntegerProperty(4);
    this.noOfSeat = new SimpleIntegerProperty(2);
    this.topSpeed = new SimpleIntegerProperty(0);
    this.loadCapacity = new SimpleIntegerProperty(0);
    this.cargoVol = new SimpleIntegerProperty(0);
    this.antiGravityLvl = new SimpleIntegerProperty(1);


    this.selectedBrand = new SimpleStringProperty("");
    this.selectedColor = new SimpleStringProperty("");
    this.selectedVehicleType = new SimpleStringProperty("");


    this.colorList = FXCollections.observableArrayList
        ("Red", "Blue", "Black", "Gray", "White", "Yellow", "Green");

    this.hasSidecar = new SimpleBooleanProperty(false);
    this.hasTurbo = new SimpleBooleanProperty(false);
    this.hasTrailerAttached = new SimpleBooleanProperty(false);
    this.hasSlidingDoor = new SimpleBooleanProperty(false);
    this.hasCloakingDevice = new SimpleBooleanProperty(false);


    //for comboBox of vehicleType and its brand
    this.displayedEngineList = FXCollections.observableArrayList();
    this.displayedBrandList = FXCollections.observableArrayList();
    this.vehicleTypeList = FXCollections.observableArrayList( "Car", "SportsCar", "Motorcycle", "Truck", "Van", "UFO");

    // comboBox of vehicleType and its brand (mutable)
    this.vehicleBrandGivenType = new HashMap<>();
    vehicleBrandGivenType.put("Car",new ArrayList<>(List.of("Toyota", "Honda", "Hyundai", "Kia"
        , "Ford", "Volkswagen", "Mazda", "Nissan"
        , "Chevrolet", "Subaru")));
    vehicleBrandGivenType.put("SportsCar",new ArrayList<>(List.of("Ferrari", "Lamborghini", "Porsche", "McLaren"
        , "Aston Martin", "Bugatti ", "Chevrolet", "Nissan", "BMW", "Audi")));
    vehicleBrandGivenType.put("Motorcycle", new ArrayList<>(List.of("Harley-Davidson", "Honda", "Yamaha", "Ducati"
        , "Kawasaki", "Suzuki", "BMW Motorrad", "KTM", "Triumph", "Aprilia")));
    vehicleBrandGivenType.put("Truck",new ArrayList<>(List.of("Freightliner", "Volvo Trucks", "Kenworth",
            "Peterbilt","Mack Trucks", "Scania", "Mercedes-Benz")));
    vehicleBrandGivenType.put("Van", new ArrayList<>(List.of("Mercedes-Benz", "Ford", "Ram", "Volkswagen",
        "Toyota", "Nissan", "Renault", "Peugeot", "Citroën",
        "Fiat Professional")));
    vehicleBrandGivenType.put("UFO",new ArrayList<>(List.of("Quantum Drift", "Nebula Pulse", "Photon Surge",
        "Gravity Echo", "Vortex Horizon", "Darkwave Industries",
        "Plasma Arc", "Celestial Rift", "Ion Flare", "Zero Point Dynamics")));

    //for comboBox of vehicleType and its engine (immutable)
    this.vehicleEngineGivenType = Map.of(
        "Car", List.of("Gasoline","Diesel", "Electric", "Hybrid"),
        "SportsCar", List.of("Gasoline", "Electric", "Hybrid"),
        "Motorcycle", List.of("Gasoline", "Hybrid"),
        "Truck", List.of("Diesel", "Gasoline"),
        "Van", List.of("Diesel", "Gasoline", "Electric", "Hybrid"),
        "UFO", List.of("Liquid Hydrogen", "Kerosene", "Dark Energy",
            "Quarks")
    );

    // Listener for vehicle type changes
    selectedVehicleType.addListener((observable, oldValue, newValue) -> {
      displayedBrandList.setAll(vehicleBrandGivenType.getOrDefault(newValue, List.of()));
      displayedEngineList.setAll(vehicleEngineGivenType.getOrDefault(newValue, List.of()));
      // Reset to dodge the exception
      selectedBrand.set("");
      engine.set("");
    });
  }

  public void addVehicle()
  {
    String vehicleType = selectedVehicleType.get();
    switch (vehicleType)
    {
      case "Motorcycle":
      {
        Vehicle newMotorcycle = new MotorCycle
            (
                selectedBrand.get(),
                vehicleModel.get(),
                selectedColor.get(),
                engine.get(),
                plateNo.get(),
                pricePerDay.get(),
                deposit.get(),
                lateFeePerDay.get(),
                noOfTyre.get(),
                noOfSeat.get(),
                hasSidecar.get()
            );
        model.addVehicle(newMotorcycle);
        break;
      }
      case "SportsCar":
      {
        Vehicle newSportCar = new SportsCar
            (
                selectedBrand.get(),
                vehicleModel.get(),
                selectedColor.get(),
                engine.get(),
                plateNo.get(),
                pricePerDay.get(),
                deposit.get(),
                lateFeePerDay.get(),
                noOfTyre.get(),
                noOfSeat.get(),
                topSpeed.get(),
                hasTurbo.get()
            );
        model.addVehicle(newSportCar);
        break;
      }
      case "Truck":
      {
        Vehicle newTruck = new Truck
            (
                selectedBrand.get(),
                vehicleModel.get(),
                selectedColor.get(),
                engine.get(),
                plateNo.get(),
                pricePerDay.get(),
                deposit.get(),
                lateFeePerDay.get(),
                noOfTyre.get(),
                noOfSeat.get(),
                loadCapacity.get(),
                hasTrailerAttached.get()
            );
        model.addVehicle(newTruck);
        break;
      }
      case "Van" :
      {
        Vehicle newVan = new Van
            (
                selectedBrand.get(),
                vehicleModel.get(),
                selectedColor.get(),
                engine.get(),
                plateNo.get(),
                pricePerDay.get(),
                deposit.get(),
                lateFeePerDay.get(),
                noOfTyre.get(),
                noOfSeat.get(),
                cargoVol.get(),
                hasSlidingDoor.get()
            );
        model.addVehicle(newVan);
        break;
      }
      case "UFO":
        Vehicle newUfo = new Ufo
            (
                selectedBrand.get(),
                vehicleModel.get(),
                selectedColor.get(),
                engine.get(),
                plateNo.get(),
                pricePerDay.get(),
                deposit.get(),
                lateFeePerDay.get(),
                noOfSeat.get(),
                antiGravityLvl.get(),
                hasCloakingDevice.get()
            );
        model.addVehicle(newUfo);
        break;
      case "Car":
      {
        Vehicle newCar = new Car(
            selectedBrand.get(),
            vehicleModel.get(),
            selectedColor.get(),
            engine.get(),
            plateNo.get(),
            pricePerDay.get(),
            deposit.get(),
            lateFeePerDay.get(),
            noOfTyre.get(),
            noOfSeat.get()
        );
        model.addVehicle(newCar);
        break;
      }
      default:
        throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
    }
  }

  public RemoteModel getModel()
  {
    return model;
  }

  public void AddMoreVehicleBrand(String newBrand)
  {
    String vehicleType = selectedVehicleType.get();
    List<String> brands = vehicleBrandGivenType.get(vehicleType);
    if (brands != null && newBrand !=  null && !newBrand.isBlank())
    {
      brands.add(newBrand);
      displayedBrandList.setAll(brands); //update the list of brands
    }
  }


  public StringProperty plateNoProperty() { return plateNo; }
  public StringProperty engineProperty() { return engine; }
  public StringProperty selectedVehicleTypeProperty() { return selectedVehicleType; }
  public StringProperty selectedBrandProperty() { return selectedBrand; }
  public StringProperty selectedColorProperty() { return selectedColor; }
  public StringProperty vehicleModelProperty() { return vehicleModel; }
  public StringProperty addMoreVehicleBrandProperty() { return addMoreVehicleBrand ;}
  public DoubleProperty pricePerDayProperty() { return pricePerDay; }
  public DoubleProperty depositProperty() { return deposit; }
  public DoubleProperty lateFeePerDayProperty() { return lateFeePerDay; }
  public IntegerProperty noOfTyreProperty() { return noOfTyre; }
  public IntegerProperty noOfSeatProperty() { return noOfSeat; }
  public IntegerProperty topSpeedProperty() { return topSpeed; }
  public IntegerProperty loadCapacityProperty() { return loadCapacity; }
  public IntegerProperty antiGravityLvlProperty() { return antiGravityLvl; }
  public IntegerProperty cargoVolProperty() { return cargoVol; }
  public BooleanProperty hasSidecarProperty() { return hasSidecar; }
  public BooleanProperty hasTurboProperty() { return hasTurbo; }
  public BooleanProperty hasTrailerAttachedProperty() { return hasTrailerAttached; }
  public BooleanProperty hasSlidingDoorProperty() { return hasSlidingDoor; }
  public BooleanProperty hasCloakingDeviceProperty() { return hasCloakingDevice; }
  public ObservableList<String> getColorList() { return colorList; }
  public ObservableList<String> getVehicleTypeList() { return vehicleTypeList; }
  public ObservableList<String> getDisplayedBrandList() { return displayedBrandList; }
  public ObservableList<String> getDisplayedEngineList() { return displayedEngineList; }
}
