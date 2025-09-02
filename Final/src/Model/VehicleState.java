package Model;

import java.io.Serializable;

public interface VehicleState extends Serializable
{
    void onReturnVehicle(Vehicle vehicle);
    void onRentVehicle(Vehicle vehicle);
    String getStatus();
}
