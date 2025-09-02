package Dao;

import Model.Vehicle;

import java.util.List;

public interface VehicleDAO {
    void addVehicle(Vehicle vehicle);
    Vehicle getVehicleByPlateNo(String plateNo);
    List<Vehicle> getAllVehicles();
    void updateVehicle(Vehicle vehicle);
    void deleteVehicle(String plateNo);
}
