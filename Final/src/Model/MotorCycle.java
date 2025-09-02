package Model;

import java.io.Serializable;

public class MotorCycle extends Vehicle implements Serializable
{
  private boolean hasSidecar;
  private String type;
  private static final String requiredLicenseType = "A";
  private static final String vehicleType = "Motorcycle";
  private static final long serialVersionUID = 8L;


  public MotorCycle(String brand, String model, String color, String engine, String plateNo,
      double pricePerDay, double deposit, double lateFee, int noOfTyre,
      int noSeats, boolean hasSidecar)
  {
    super(brand, model, color, engine, plateNo, pricePerDay, deposit, lateFee,
        noOfTyre, noSeats, requiredLicenseType, vehicleType);

      this.hasSidecar= hasSidecar;
   }
  public boolean hasSidecar() {
    return hasSidecar;
  }

  public void setHasSidecar(boolean hasSidecar) {
    this.hasSidecar = hasSidecar;
  }
public String toString(){
    return super.toString() + "\nHas side car: " + hasSidecar;
}

}


