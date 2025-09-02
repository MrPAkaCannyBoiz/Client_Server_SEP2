package Server.Command;

import Model.Employee;
import Shared.RemoteModel;

import java.io.IOException;
import java.util.List;

public class GetEmployeeCommand implements RemoteCommand<List<Employee>>
{
  @Override public List<Employee> execute(RemoteModel model) throws IOException
  {
    return model.getEmployees();
  }
}
