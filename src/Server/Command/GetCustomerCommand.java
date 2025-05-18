package Server.Command;

import Model.Customer;
import Shared.RemoteModel;

import java.util.List;

public class GetCustomerCommand implements RemoteCommand<List<Customer>>
{
  @Override public List<Customer> execute(RemoteModel model)
  {
    return model.getCustomers();
  }
}
