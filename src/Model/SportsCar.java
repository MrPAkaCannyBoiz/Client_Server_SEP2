package Model;

import java.io.Serializable;

public class SportsCar extends Vehicle implements Serializable
{
  private int topSpeed;
  private boolean hasTurbo;
  private static final String requiredLicenseType = "B";
  private static final String vehicleType = "SportCar";
  private static final long serialVersionUID = 10L;

  public SportsCar(String brand ,String model, String color, String engine, String plateNo,
      double pricePerDay, double deposit, double lateFee, int noOfTyre,
      int noSeats, int topSpeed, boolean hasTurbo)
  {
    super(brand, model, color, engine, plateNo, pricePerDay, deposit, lateFee,
        noOfTyre, noSeats, requiredLicenseType, vehicleType);
    this.topSpeed = topSpeed;
    this.hasTurbo = hasTurbo;
  }

  public boolean getHasTurbo()
  {
    return hasTurbo;
  }
  
  public void setHasTurbo(boolean hasTurbo)
  {
    this.hasTurbo = hasTurbo;
  }

  public int getTopSpeed()
  {
    return topSpeed;
  }

  public void setTopSpeed(int topSpeed)
  {
    this.topSpeed = topSpeed;
  }

  public String toString(){
    return super.toString() + "\nTop speed: "+topSpeed+" km/h"+
        "\nHas turbo: "+hasTurbo;
  }
}
