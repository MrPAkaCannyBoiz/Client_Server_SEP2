package Server.Command;

import Model.Employee;
import Shared.RemoteModel;

import java.io.IOException;

public class AddEmployeeCommand implements RemoteCommand<Void>
{
  private Employee employee;

  public AddEmployeeCommand(Employee employee)
  {
    this.employee = employee;
  }

  @Override public Void execute(RemoteModel model) throws IOException
  {
    model.addEmployees(employee);
    return null;
  }
}
