package Server.Command;

import Model.Customer;
import Shared.RemoteModel;

public class RemoveCustomerCommand implements RemoteCommand<Void>
{
  private  Customer customer;

  public RemoveCustomerCommand(Customer customer) {
    this.customer = customer;
  }

  @Override
  public Void execute(RemoteModel model) {
    model.removeCustomer(customer);
    return null;
  }
}
