package Model;

import java.io.Serializable;

public class AvailableState implements VehicleState, Serializable
{

private static final String status = "Available";
    private static final long serialVersionUID = 6L;

    @Override
    public void onReturnVehicle(Vehicle vehicle) {
        System.out.println("Vehicle is already available.");
    }

    @Override
    public void onRentVehicle(Vehicle vehicle) {
        System.out.println("Vehicle has been rented.");
        vehicle.setCurrentState(new RentedState());
    }

    @Override public String getStatus()
    {
        return status;
    }
}
