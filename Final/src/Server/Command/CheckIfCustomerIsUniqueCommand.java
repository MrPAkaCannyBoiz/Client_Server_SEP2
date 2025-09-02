package Server.Command;

import Model.Customer;
import Shared.RemoteModel;

public class CheckIfCustomerIsUniqueCommand implements RemoteCommand<Void>
{
  private Customer customer;
  public CheckIfCustomerIsUniqueCommand(Customer customer)
  {
    this.customer = customer;
  }
  @Override public Void execute(RemoteModel model)
  {
    model.checkIfCustomerInfoIsUnique(customer);
    return null;
  }
}
