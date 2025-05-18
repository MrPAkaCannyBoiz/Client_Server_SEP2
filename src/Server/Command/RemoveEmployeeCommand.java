package Server.Command;

import Model.Employee;
import Shared.RemoteModel;

import java.io.IOException;

public class RemoveEmployeeCommand implements RemoteCommand<Void>
{
  private Employee employee;
  public RemoveEmployeeCommand(Employee employee) {
    this.employee = employee;
  }
  @Override public Void execute(RemoteModel model) throws IOException
  {
    model.removeEmployee(employee);
    return null;
  }
}
