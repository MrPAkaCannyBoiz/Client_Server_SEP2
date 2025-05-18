package Server.Command;

import Model.Customer;
import Model.Vehicle;
import Shared.RemoteModel;

public class ReturnBookedVehicleAndPayCommand implements RemoteCommand<Void>
{
  private Vehicle vehicle;
  private Customer customer;

  public ReturnBookedVehicleAndPayCommand(Vehicle vehicle, Customer customer)
  {
    this.vehicle = vehicle;
    this.customer = customer;
  }

  @Override public Void execute(RemoteModel model)
  {
    model.returnBookedVehicleAndPay(vehicle, customer);
    return null;
  }
}
