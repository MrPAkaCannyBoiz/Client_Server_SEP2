package Server.Command;

import Model.Customer;
import Shared.RemoteModel;

import java.util.List;

public class AddCustomerCommand implements RemoteCommand<List<Customer>>
{
  private  Customer customer;

  public AddCustomerCommand(Customer customer) {
    this.customer = customer;
  }

  @Override
  public List<Customer> execute(RemoteModel model) {
    model.addCustomer(customer);
    return model.getCustomers();
  }
}
