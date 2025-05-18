package Command;

import Model.*;
import Shared.RemoteModel;

public class VehicleCommand implements Command
{
  public enum Action { ADD, REMOVE, RENT, RETURN }
  private Action action;
  private Vehicle vehicle;
  private Customer customer;

  public VehicleCommand(Action action, Vehicle vehicle, Customer customer)
  {
    this.action = action;
    this.vehicle = vehicle;
    this.customer = customer;
  }

  @Override public void execute(RemoteModel model) throws Exception
  {
    switch (action)
    {
      case ADD -> model.addVehicle(vehicle);
      case REMOVE -> model.removeVehicle(vehicle);
      case RENT -> model.rentTheVehicle(vehicle, customer);
      case RETURN -> model.returnTheVehicle(vehicle, customer);
    }
  }

  @Override public Object getResult()
  {
    return null;
  }
}
