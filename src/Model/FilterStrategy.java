package Model;

import javafx.collections.ObservableList;

public class FilterStrategy implements SearchStrategy
{

  private String selectedType;
  private String selectedColour;
  private String selectedStatus;
  private double maxPrice;

  public FilterStrategy(String selectedType, String selectedColour,
      String selectedStatus, double maxPrice)
  {
    this.selectedType = selectedType;
    this.selectedColour = selectedColour;
    this.selectedStatus = selectedStatus;
    this.maxPrice = maxPrice;
  }

  @Override public ObservableList<Vehicle> search(
      ObservableList<Vehicle> vehicles)
  {
    return vehicles.filtered(vehicle ->
        (selectedType == null || selectedType.isEmpty() || vehicle.getVehicleType().equals(selectedType)) &&
            (selectedColour == null || selectedColour.isEmpty() || vehicle.getColor().equals(selectedColour)) &&
            (selectedStatus == null || selectedStatus.isEmpty() || vehicle.getCurrentStatus().equals(selectedStatus)) &&
            vehicle.getPricePerDay() <= maxPrice);
  }
}