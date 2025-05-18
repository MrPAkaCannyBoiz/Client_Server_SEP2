package Server.Command;

import Model.Vehicle;
import Shared.RemoteModel;

import java.util.List;

public class GetVehiclesCommand implements RemoteCommand<List<Vehicle>>
{
  @Override public List<Vehicle> execute(RemoteModel model)
  {
    return model.getVehicles();
  }
}
