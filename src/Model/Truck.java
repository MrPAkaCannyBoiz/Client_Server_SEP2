package Model;

import java.io.Serializable;

public class Truck extends Vehicle implements Serializable
{
  private int loadCapacity;
  private boolean trailerAttached;
  private static final String requiredLicenseType = "D";
  private static final String vehicleType = "Truck";
  private static final long serialVersionUID = 11L;

  public Truck(String brand, String model, String color, String engine, String plateNo,
      double pricePerDay, double deposit, double lateFee, int noOfTyre,
      int noSeats, int loadCapacity, boolean trailerAttached)
  {
    super(brand, model, color, engine, plateNo, pricePerDay, deposit, lateFee, noOfTyre,
        noSeats, requiredLicenseType, vehicleType);
    this.loadCapacity = loadCapacity;
    this.trailerAttached = trailerAttached;

  }

  public int getLoadCapacity()
  {
    return loadCapacity;
  }

  public void setLoadCapacity(int loadCapacity)
  {
    this.loadCapacity = loadCapacity;
  }

  public boolean trailerAttached()
  {
    return trailerAttached;
  }

  public void setTrailerAttached(boolean trailerAttached)
  {
    this.trailerAttached = trailerAttached;
  }
public String toString(){
    return super.toString() + "\nLoad capacity: " + loadCapacity +" KG"+
        "\nHas trailer attachement: " + trailerAttached;
}
}
