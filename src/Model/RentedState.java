package Model;

import java.io.Serializable;

public class RentedState implements VehicleState, Serializable
{

    private static final String status = "Rented";
    private static final long serialVersionUID = 9L;

    @Override
    public void onReturnVehicle(Vehicle vehicle)
    {
        System.out.println("Vehicle returned and is now available.");
        vehicle.setCurrentState(new AvailableState());
    }

    @Override
    public void onRentVehicle(Vehicle vehicle) {
        System.out.println("Vehicle is already rented.");
    }


    @Override public String getStatus()
    {
        return status;
    }
}