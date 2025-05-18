package Server.Command;

import Model.Vehicle;
import Shared.RemoteModel;

public class ReturnBookedVehicleAndPayForEmployeeCommand implements RemoteCommand<Void>
{
  private Vehicle vehicle;

  public ReturnBookedVehicleAndPayForEmployeeCommand(Vehicle vehicle) {
    this.vehicle = vehicle;
  }
  @Override public Void execute(RemoteModel model)
  {
    model.returnBookedVehicleAndPayForEmployee(vehicle);
    return null;
  }
}
