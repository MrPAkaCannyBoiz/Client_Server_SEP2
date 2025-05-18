package Model;

import java.io.Serializable;

public class Van extends Vehicle implements Serializable
{
  private int cargoVolume;
  private boolean hasSlidingDoor;
  private static final String requiredLicenseType = "C";
  private static final String vehicleType = "Van";
  private static final long serialVersionUID = 13L;

  public Van(String brand, String model, String color, String engine, String plateNo,
      double pricePerDay, double deposit, double lateFee, int noOfTyre,
      int noSeats, int cargoVolume, boolean hasSlidingDoor)
  {
    super(brand, model, color, engine, plateNo, pricePerDay, deposit, lateFee, noOfTyre,
        noSeats, requiredLicenseType, vehicleType);
    this.cargoVolume=cargoVolume;
    this.hasSlidingDoor = hasSlidingDoor;
  }


  public int getCargoVolume()
  {
    return cargoVolume;
  }

  public void setCargoVolume(int cargoVolume)
  {
    this.cargoVolume = cargoVolume;
  }

  public boolean isHasSlidingDoor()
  {
    return hasSlidingDoor;
  }

  public void setHasSlidingDoor(boolean hasSlidingDoor)
  {
    this.hasSlidingDoor = hasSlidingDoor;
  }
public String toString(){
    return super.toString() + "\nCargo volume: " + cargoVolume + " KG"+
        "\nHas sliding door: " + hasSlidingDoor;
}
}
