package Server.Command;

import Model.Vehicle;
import Shared.RemoteModel;

public class AddVehicleCommand implements RemoteCommand<Void>
{
  private  Vehicle vehicle;

  public AddVehicleCommand(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  @Override
  public Void execute(RemoteModel model) {
    model.addVehicle(vehicle);
    return null;
  }
}
