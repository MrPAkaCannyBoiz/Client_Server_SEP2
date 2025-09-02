package Server.Command;

import Model.Vehicle;
import Shared.RemoteModel;

public class RemoveVehicleCommand implements RemoteCommand<Void>
{
  private  Vehicle vehicle;

  public RemoveVehicleCommand(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  @Override public Void execute(RemoteModel model)
  {
    model.removeVehicle(vehicle);
    System.out.println("Vehicle removed");
    return null;
  }
}
