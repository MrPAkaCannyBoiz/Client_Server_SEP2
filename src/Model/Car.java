package Model;

import java.io.Serializable;

public class Car extends Vehicle implements Serializable
{
  private static final String vehicleType = "Car";
  private static final String requiredLicenseType = "B";
  private static final long serialVersionUID = 7L;

  public Car(String brand, String model, String color, String engine,
      String plateNo, double pricePerDay, double deposit, double lateFee,
      int noOfTyre, int noSeats)
  {
    super(brand, model, color, engine, plateNo, pricePerDay, deposit, lateFee,
        noOfTyre, noSeats, requiredLicenseType, vehicleType);
  }
public String toString(){
    return super.toString();
}
}
