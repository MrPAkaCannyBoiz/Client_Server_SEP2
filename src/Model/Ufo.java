package Model;

import java.io.Serializable;

public class Ufo extends Vehicle implements Serializable
{
  private int antiGravityLevel;
  private boolean isCloakingDevice;
  private static final int noOfUFOTyre = 0;
  private static final String requiredLicenseType = "X";
  private static final String vehicleType = "UFO";
  private static final long serialVersionUID = 12L;

  public Ufo(String brand, String model, String color, String engine,
      String plateNo, double pricePerDay, double deposit, double lateFee,
      int noSeats, int antiGravityLevel,Boolean isCloakingDevice)

  {
    super(brand, model, color, engine, plateNo, pricePerDay, deposit, lateFee, noOfUFOTyre,
        noSeats, requiredLicenseType, vehicleType);
    if (antiGravityLevel <= 0 || antiGravityLevel > 9)
    {
      throw new IllegalArgumentException("(From UFO class) anti g lvl only from 1 to 9");
    }
    this.antiGravityLevel = antiGravityLevel;
    this.isCloakingDevice = isCloakingDevice;
  }

  public int getAntiGravityLevel()
  {
    return antiGravityLevel;
  }

  public void setAntiGravityLevel(int antiGravityLevel)
  {
    this.antiGravityLevel = antiGravityLevel;
  }

  public boolean isCloakingDevice()
  {
    return isCloakingDevice;
  }

  public void setCloakingDevice(boolean cloakingDevice)
  {
    isCloakingDevice = cloakingDevice;
  }
  public String toString(){
    return super.toString() + "Anti gravity level: " + antiGravityLevel + " N"+
        "\nHas clocking device: "+ isCloakingDevice;
  }
}
